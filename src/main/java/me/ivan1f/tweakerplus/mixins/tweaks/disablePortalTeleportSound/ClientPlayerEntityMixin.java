package me.ivan1f.tweakerplus.mixins.tweaks.disablePortalTeleportSound;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Redirect(
            method = "updateNausea",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V"
            )
    )
    private void stopPlayingPortalTriggerSound(SoundManager instance, SoundInstance sound) {
        if (!TweakerPlusConfigs.DISABLE_PORTAL_TELEPORT_SOUND.getBooleanValue()) {
            // Vanilla
            instance.play(sound);
        }
    }
}
