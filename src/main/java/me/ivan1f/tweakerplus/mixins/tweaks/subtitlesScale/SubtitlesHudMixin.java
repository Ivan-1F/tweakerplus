package me.ivan1f.tweakerplus.mixins.tweaks.subtitlesScale;

import me.fallenbreath.tweakermore.util.render.RenderUtils;
import me.fallenbreath.tweakermore.util.render.context.RenderContext;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.SubtitlesHud;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC > 11700
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

@Mixin(SubtitlesHud.class)
public class SubtitlesHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;
    @Nullable
    private RenderUtils.Scaler scaler = null;

    @Inject(method = "render", at = @At("HEAD"))
    private void tweakerPlus_subtitlesScale_push(
            //#if MC > 11700
            //$$ MatrixStack matrices,
            //#endif
            CallbackInfo ci
    ) {
        this.scaler = null;
        if (TweakerPlusConfigs.SUBTITLES_SCALE.isModified()) {
            this.scaler = RenderUtils.createScaler(
                    //#if MC >= 11500
                    this.client.getWindow().getScaledWidth(),
                    this.client.getWindow().getScaledHeight() - 30,
                    //#else
                    //$$ this.client.window.getScaledWidth(),
                    //$$ this.client.window.getScaledHeight() - 30,
                    //#endif
                    TweakerPlusConfigs.SUBTITLES_SCALE.getDoubleValue()
            );
            this.scaler.apply(RenderContext.of(
                    //#if MC >= 11700
                    //$$ matrices
                    //#endif
            ));
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void tweakerPlus_subtitlesScale_pop(
            //#if MC > 11700
            //$$ MatrixStack matrices,
            //#endif
            CallbackInfo ci
    ) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
