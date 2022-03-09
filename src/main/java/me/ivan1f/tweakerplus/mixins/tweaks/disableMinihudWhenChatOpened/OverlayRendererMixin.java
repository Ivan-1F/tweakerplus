package me.ivan1f.tweakerplus.mixins.tweaks.disableMinihudWhenChatOpened;

import fi.dy.masa.minihud.renderer.OverlayRenderer;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverlayRenderer.class)
public class OverlayRendererMixin {

    @Inject(method = "renderOverlays", at = @At("HEAD"), cancellable = true)
    private static void stopRenderingOverlays(MatrixStack matrixStack, MinecraftClient mc, float partialTicks, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen &&
                TweakerPlusConfigs.DISABLE_MINIHUD_WHEN_CHAT_OPENED.getBooleanValue()) {
            ci.cancel();
        } else {
//            if (MinecraftClient.getInstance().inGameHud.getChatHud()) {
//                System.out.println("focused");
//            }
        }
    }
}
