package me.ivan1f.tweakerplus.mixins.tweaks.immediatelyRespawn;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(
            method = "onDeathMessage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"
            ),
            cancellable = true
    )
    private void immediatelyRespawn(DeathMessageS2CPacket packet, CallbackInfo ci) {
        if (TweakerPlusConfigs.IMMEDIATELY_RESPAWN.getBooleanValue()) {
            if (this.client.player != null) {
                this.client.player.requestRespawn();
                ci.cancel();
            }
        }
    }
}
