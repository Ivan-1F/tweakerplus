package me.ivan1f.tweakerplus.mixins.tweaks.bundleOriginInSchematic;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.litematica.util.FileType;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.widgets.WidgetFileBrowserBase;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.BundleOriginInSchematicHelper;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.ILitematicaSchematic;
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
public class GuiSchematicLoadButtonListenerMixin {
    private LitematicaSchematic loadingSchematic;

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
    private Vec3d setOrigin(ClientPlayerEntity player) {
        if (this.shouldMoveToOrigin()) {
            return new Vec3d(((ILitematicaSchematic) this.loadingSchematic).getOrigin());
        }
        return player.getPos();
    }

    private boolean shouldMoveToOrigin() {
        return TweakerPlusConfigs.BUNDLE_ORIGIN_IN_SCHEMATIC.getBooleanValue() && BundleOriginInSchematicHelper.moveToOrigin &&
                this.loadingSchematic != null && ((ILitematicaSchematic) this.loadingSchematic).hasOrigin();
    }
}
