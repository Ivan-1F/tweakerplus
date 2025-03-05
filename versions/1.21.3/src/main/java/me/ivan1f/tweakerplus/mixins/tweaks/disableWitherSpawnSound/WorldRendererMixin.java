package me.ivan1f.tweakerplus.mixins.tweaks.disableWitherSpawnSound;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.world.WorldEventHandler;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldEventHandler.class)
public class WorldRendererMixin {
    @Inject(
            method = "processGlobalEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V",
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
