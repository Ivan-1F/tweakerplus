package me.ivan1f.tweakerplus.mixins.tweaks.subtitlesScale;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.SubtitlesHud;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SubtitlesHud.class)
public class SubtitlesHudMixin {
    @Shadow @Final private MinecraftClient client;
    @Nullable
    private RenderUtil.Scaler scaler = null;

    @Inject(method = "render", at = @At("HEAD"))
    private void tweakerPlus_subtitlesScale_push(CallbackInfo ci) {
        this.scaler = null;
        if (TweakerPlusConfigs.SUBTITLES_SCALE.isModified()) {
            this.scaler = RenderUtil.createScaler(this.client.window.getScaledWidth(), this.client.window.getScaledHeight() - 30, TweakerPlusConfigs.SUBTITLES_SCALE.getDoubleValue());
            this.scaler.apply();
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void tweakerPlus_subtitlesScale_pop(CallbackInfo ci) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
