package me.ivan1f.tweakerplus.mixins.tweaks.disablePortalTeleportSound;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(
            //#if MC >= 11600
            //$$ method = "processWorldEvent",
            //#elseif
            method = "playLevelEvent",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;getSoundManager()Lnet/minecraft/client/sound/SoundManager;"
            ),
            cancellable = true
    )
    private void stopPlayingPortalTeleportSound(PlayerEntity source, int type, BlockPos pos, int data, CallbackInfo ci) {
        if (TweakerPlusConfigs.DISABLE_PORTAL_TELEPORT_SOUND.getBooleanValue()) {
            ci.cancel();
        }
    }
}
