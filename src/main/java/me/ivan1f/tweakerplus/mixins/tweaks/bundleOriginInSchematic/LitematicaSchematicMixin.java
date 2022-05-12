package me.ivan1f.tweakerplus.mixins.tweaks.bundleOriginInSchematic;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.ILitematicaSchematic;
import me.ivan1f.tweakerplus.util.ModIds;
import net.minecraft.nbt.CompoundTag;
import fi.dy.masa.malilib.util.NBTUtils;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LitematicaSchematic.class)
@Restriction(require = @Condition(ModIds.litematica))
public class LitematicaSchematicMixin implements ILitematicaSchematic {
    // handles ILitematicaSchematic, will be used when:
    //  - saving the schematic with bundleOriginInSchematic enabled, will be used at LitematicaSchematicMixin#writeOriginToNBT (GuiSchematicSaveButtonListenerMixin#appendOriginOnSave)
    //  - reading a schematic with origin stored in nbt and bundleOriginInSchematic enabled, will be used at GuiSchematicLoadButtonListenerMixin#setOrigin (LitematicaSchematicMixin#readOriginFromNBT)
    private BlockPos origin;

    // save schematic:
    // 1. save button clicked (GuiSchematicSaveButtonListenerMixin) write origin using ILitematicaSchematic#setOrigin
    // 2. save task generated
    // 3. read origin using ILitematicaSchematic#getOrigin and write it to nbt (LitematicaSchematicMixin#writeOriginToNBT)
    // 4. the injected nbt will be written to the file

    // read schematic:
    // 1. load origin using ILitematicaSchematic#setOrigin from the nbt (LitematicaSchematicMixin#readOriginFromNBT)
    // 2. read origin using ILitematicaSchematic#getOrigin and move the placement to origin (GuiSchematicLoadButtonListenerMixin#movePlacementToOrigin)

    @Inject(method = "writeToNBT", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void writeOriginToNBT(CallbackInfoReturnable<CompoundTag> cir, CompoundTag nbt) {
        // if bundleOriginInSchematic is disabled, the origin will be null since GuiSchematicSaveButtonListenerMixin will not be triggered
        if (!((ILitematicaSchematic) this).hasOrigin()) return;
        BlockPos origin = ((ILitematicaSchematic) this).getOrigin();
        nbt.put("Origin", NBTUtils.createBlockPosTag(origin));
    }

    @Inject(method = "readFromNBT", at = @At("RETURN"))
    private void readOriginFromNBT(CompoundTag nbt, CallbackInfoReturnable<Boolean> cir) {
        // if no origin info is present or bundleOriginInSchematic is disabled, don't read
        if (!nbt.contains("Origin") || !TweakerPlusConfigs.BUNDLE_ORIGIN_IN_SCHEMATIC.getBooleanValue()) return;
        BlockPos origin = NBTUtils.readBlockPos(nbt.getCompound("Origin"));
        ((ILitematicaSchematic) this).setOrigin(origin);
    }

    @Override
    public boolean hasOrigin() {
        return this.origin != null;
    }

    @Override
    public void setOrigin(BlockPos origin) {
        this.origin = origin;
    }

    @Override
    public BlockPos getOrigin() {
        return this.origin;
    }
}
