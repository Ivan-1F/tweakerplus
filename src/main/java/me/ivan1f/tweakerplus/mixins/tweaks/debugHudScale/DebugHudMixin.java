package me.ivan1f.tweakerplus.mixins.tweaks.debugHudScale;

import me.fallenbreath.tweakermore.util.render.RenderUtils;
import me.fallenbreath.tweakermore.util.render.context.RenderContext;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 11600
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Shadow @Final private MinecraftClient client;
    @Nullable
    private RenderUtils.Scaler scaler = null;

    @Inject(method = "renderLeftText", at = @At("HEAD"))
    private void tweakerPlus_debugHudScale_left_push(
            //#if MC >= 11600
            //$$ MatrixStack matrices,
            //#endif
            CallbackInfo ci
    ) {
        this.scaler = null;
        if (TweakerPlusConfigs.DEBUG_HUD_SCALE.isModified()) {
            this.scaler = RenderUtils.createScaler(0, 0, TweakerPlusConfigs.DEBUG_HUD_SCALE.getDoubleValue());
            this.scaler.apply(RenderContext.of(
                    //#if MC >= 11600
                    //$$ matrices
                    //#endif
            ));
        }
    }

    @Inject(method = "renderLeftText", at = @At("RETURN"))
    private void tweakerPlus_debugHudScale_left_pop(
            //#if MC >= 11600
            //$$ MatrixStack matrices,
            //#endif
            CallbackInfo ci
    ) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }

    @Inject(method = "renderRightText", at = @At("HEAD"))
    private void tweakerPlus_debugHudScale_right_push(
            //#if MC >= 11600
            //$$ MatrixStack matrices,
            //#endif
            CallbackInfo ci
    ) {
        this.scaler = null;
        if (TweakerPlusConfigs.DEBUG_HUD_SCALE.isModified()) {
            this.scaler = RenderUtils.createScaler(
                    //#if MC >= 11500
                    this.client.getWindow().getScaledWidth(),
                    //#else
                    //$$ this.client.window.getScaledWidth(),
                    //#endif
                    0,
                    TweakerPlusConfigs.DEBUG_HUD_SCALE.getDoubleValue()
            );
            this.scaler.apply(RenderContext.of(
                    //#if MC >= 11600
                    //$$ matrices
                    //#endif
            ));
        }
    }

    @Inject(method = "renderRightText", at = @At("RETURN"))
    private void tweakerPlus_debugHudScale_right_pop(
            //#if MC >= 11600
            //$$ MatrixStack matrices,
            //#endif
            CallbackInfo ci
    ) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
