package me.ivan1f.tweakerplus.mixins.tweaks.disableWitherSpawnSound;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(
            //#if MC >= 11600
            //$$ method = "processGlobalEvent",
            //#else
            method = "playGlobalEvent",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void stopPlayingWitherSpawnSound(int eventId, BlockPos pos, int i, CallbackInfo ci) {
        if (TweakerPlusConfigs.DISABLE_WITHER_SPAWN_SOUND.getBooleanValue()) {
            ci.cancel();
        }
    }
}
