package me.ivan1f.tweakerplus.mixins.tweaks.immediatelyRespawn;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow public ClientPlayerEntity player;

    @Inject(method = "openScreen", at = @At("HEAD"), cancellable = true)
    private void respawnInsteadOfOpeningDeathScreen(Screen screen, CallbackInfo ci) {
        if (screen instanceof DeathScreen && TweakerPlusConfigs.IMMEDIATELY_RESPAWN.getBooleanValue()) {
            ci.cancel();
            this.player.requestRespawn();
        }
    }
}
