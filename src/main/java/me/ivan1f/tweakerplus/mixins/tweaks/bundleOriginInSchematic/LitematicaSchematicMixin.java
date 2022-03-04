package me.ivan1f.tweakerplus.mixins.tweaks.bundleOriginInSchematic;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.ILitematicaSchematic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LitematicaSchematic.class)
public class LitematicaSchematicMixin implements ILitematicaSchematic {
    private BlockPos origin;

    @Inject(method = "writeToNBT", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void writeOriginToNBT(CallbackInfoReturnable<CompoundTag> cir, CompoundTag nbt) {
        if (((ILitematicaSchematic) this).getOrigin() == null) return;
        BlockPos origin = ((ILitematicaSchematic) this).getOrigin();
        nbt.put("Origin", this.toListTag(origin.getX(), origin.getY(), origin.getZ()));
    }

    @Inject(method = "readFromNBT", at = @At("RETURN"))
    private void readOriginFromNBT(CompoundTag nbt, CallbackInfoReturnable<Boolean> cir) {
        if (!nbt.contains("Origin")) return;
        ListTag list = nbt.getList("Origin", 3);
        BlockPos origin = new BlockPos(list.getInt(0), list.getInt(1), list.getInt(2));
        ((ILitematicaSchematic) this).setOrigin(origin);
    }

    protected ListTag toListTag(int ... values) {
        ListTag listTag = new ListTag();
        for (int i : values) {
            listTag.add(IntTag.of(i));
        }
        return listTag;
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
