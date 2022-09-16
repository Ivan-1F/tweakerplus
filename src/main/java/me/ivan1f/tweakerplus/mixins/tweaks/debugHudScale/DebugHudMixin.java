package me.ivan1f.tweakerplus.mixins.tweaks.debugHudScale;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Shadow @Final private MinecraftClient client;
    @Nullable
    private RenderUtil.Scaler scaler = null;

    @Inject(method = "renderLeftText", at = @At("HEAD"))
    private void tweakerPlus_debugHudScale_left_push(MatrixStack matrices, CallbackInfo ci) {
        this.scaler = null;
        if (TweakerPlusConfigs.DEBUG_HUD_SCALE.isModified()) {
            this.scaler = RenderUtil.createScaler(0, 0, TweakerPlusConfigs.DEBUG_HUD_SCALE.getDoubleValue());
            this.scaler.apply(matrices);
        }
    }

    @Inject(method = "renderLeftText", at = @At("RETURN"))
    private void tweakerPlus_debugHudScale_left_pop(MatrixStack matrices, CallbackInfo ci) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }

    @Inject(method = "renderRightText", at = @At("HEAD"))
    private void tweakerPlus_debugHudScale_right_push(MatrixStack matrices, CallbackInfo ci) {
        this.scaler = null;
        if (TweakerPlusConfigs.DEBUG_HUD_SCALE.isModified()) {
            this.scaler = RenderUtil.createScaler(this.client.getWindow().getScaledWidth(), 0, TweakerPlusConfigs.DEBUG_HUD_SCALE.getDoubleValue());
            this.scaler.apply(matrices);
        }
    }

    @Inject(method = "renderRightText", at = @At("RETURN"))
    private void tweakerPlus_debugHudScale_right_pop(MatrixStack matrices, CallbackInfo ci) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
