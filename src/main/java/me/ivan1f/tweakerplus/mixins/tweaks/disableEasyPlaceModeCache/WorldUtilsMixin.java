package me.ivan1f.tweakerplus.mixins.tweaks.disableEasyPlaceModeCache;

import fi.dy.masa.litematica.util.WorldUtils;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldUtils.class)
public class WorldUtilsMixin {
    @Inject(method = "cacheEasyPlacePosition", at = @At("HEAD"), cancellable = true)
    private static void stopCachingPosition(BlockPos pos, CallbackInfo ci) {
        if (TweakerPlusConfigs.DISABLE_EASY_PLACE_MODE_CACHE.getBooleanValue()) {
            ci.cancel();
        }
    }
}
