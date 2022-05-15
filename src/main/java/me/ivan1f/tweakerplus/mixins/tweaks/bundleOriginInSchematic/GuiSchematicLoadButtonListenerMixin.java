package me.ivan1f.tweakerplus.mixins.tweaks.bundleOriginInSchematic;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.litematica.util.FileType;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.widgets.WidgetFileBrowserBase;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.BundleOriginInSchematicHelper;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.ILitematicaSchematic;
import me.ivan1f.tweakerplus.util.ModIds;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;

@Mixin(targets = "fi.dy.masa.litematica.gui.GuiSchematicLoad$ButtonListener")
@Restriction(require = @Condition(ModIds.litematica))
public class GuiSchematicLoadButtonListenerMixin {
    private LitematicaSchematic loadingSchematic;

    // Local capture is not available for @Redirect, so fetch the schematic before redirecting
    @Inject(
            method = "actionPerformedWithButton",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPos()Lnet/minecraft/util/math/Vec3d;",
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void getSchematic(ButtonBase name, int enabled, CallbackInfo ci, WidgetFileBrowserBase.DirectoryEntry entry, File file, LitematicaSchematic schematic, FileType fileType, boolean warnType) {
        this.loadingSchematic = schematic;
    }

    @Redirect(
            method = "actionPerformedWithButton",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPos()Lnet/minecraft/util/math/Vec3d;")
    )
    private Vec3d movePlacementToOrigin(ClientPlayerEntity player) {
        if (this.shouldMoveToOrigin()) {
            return Vec3d.of(((ILitematicaSchematic) this.loadingSchematic).getOrigin());
        }
        return player.getPos();
    }

    private boolean shouldMoveToOrigin() {
        // bundleOriginInSchematic is enabled, moveToOrigin checkbox is checked, schematic fetched and origin is not null
        return BundleOriginInSchematicHelper.shouldMoveToOrigin() && this.loadingSchematic != null &&
                ((ILitematicaSchematic) this.loadingSchematic).hasOrigin();
    }
}
