package me.ivan1f.tweakerplus.mixins.tweaks.disablePumpkinOverlay;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private void disablePumpkinOverlay(MatrixStack matrices, Identifier texture, float opacity, CallbackInfo ci) {
        // maybe there's a better way to do this
        if (texture.equals(new Identifier("textures/misc/pumpkinblur.png")) && TweakerPlusConfigs.DISABLE_PUMPKIN_OVERLAY.getBooleanValue()) ci.cancel();
    }
}
