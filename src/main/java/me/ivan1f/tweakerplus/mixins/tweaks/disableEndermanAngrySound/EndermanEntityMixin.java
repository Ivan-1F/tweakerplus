package me.ivan1f.tweakerplus.mixins.tweaks.disableEndermanAngrySound;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.entity.mob.EndermanEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {
    @Inject(method = "playAngrySound", at = @At("HEAD"), cancellable = true)
    private void stopPlayingAngrySound(CallbackInfo ci) {
        if (TweakerPlusConfigs.DISABLE_ENDERMAN_ANGRY_SOUND.getBooleanValue()) {
            ci.cancel();
        }
    }
}
