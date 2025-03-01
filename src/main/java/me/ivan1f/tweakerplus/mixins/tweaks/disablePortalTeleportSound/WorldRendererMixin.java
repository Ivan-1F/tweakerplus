package me.ivan1f.tweakerplus.mixins.tweaks.disablePortalTeleportSound;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC < 11900
import net.minecraft.entity.player.PlayerEntity;
//#endif

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(
            //#if MC >= 11600
            //$$ method = "processWorldEvent",
            //#else
            method = "playLevelEvent",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;getSoundManager()Lnet/minecraft/client/sound/SoundManager;"
            ),
            cancellable = true
    )
    private void stopPlayingPortalTeleportSound(
            //#if MC >= 11900
            //$$ int eventId,
            //#else
            PlayerEntity source, int type,
            //#endif
            BlockPos pos, int data, CallbackInfo ci
    ) {
        if (TweakerPlusConfigs.DISABLE_PORTAL_TELEPORT_SOUND.getBooleanValue()) {
            ci.cancel();
        }
    }
}
