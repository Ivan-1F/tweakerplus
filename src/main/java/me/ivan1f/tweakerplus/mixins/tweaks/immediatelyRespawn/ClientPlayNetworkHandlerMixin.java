package me.ivan1f.tweakerplus.mixins.tweaks.immediatelyRespawn;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 11700
//$$ import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
//$$ import org.spongepowered.asm.mixin.Final;
//#else
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
//#endif

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    //#if MC >= 11700
    //$$ @Final
    //#endif
    @Shadow
    private MinecraftClient client;

    @Inject(
            //#if MC >= 11700
            //$$ method = "onDeathMessage",
            //#else
            method = "onCombatEvent",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11700
                    //$$ target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"
                    //#else
                    target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V"
                    //#endif
            ),
            cancellable = true
    )
    private void immediatelyRespawn(
            //#if MC >= 11700
            //$$ DeathMessageS2CPacket packet,
            //#else
            CombatEventS2CPacket packet,
            //#endif
            CallbackInfo ci
    ) {
        if (TweakerPlusConfigs.IMMEDIATELY_RESPAWN.getBooleanValue()) {
            if (this.client.player != null) {
                this.client.player.requestRespawn();
                ci.cancel();
            }
        }
    }
}
