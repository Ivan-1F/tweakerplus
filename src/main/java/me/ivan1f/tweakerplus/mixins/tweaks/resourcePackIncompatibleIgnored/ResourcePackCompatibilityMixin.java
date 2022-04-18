package me.ivan1f.tweakerplus.mixins.tweaks.resourcePackIncompatibleIgnored;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.resource.ResourcePackCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ResourcePackCompatibility.class)
public class ResourcePackCompatibilityMixin {
    @Inject(method = "isCompatible", at = @At("HEAD"), cancellable = true)
    private void alwaysCompatible(CallbackInfoReturnable<Boolean> cir) {
        if (TweakerPlusConfigs.RESOURCE_PACK_INCOMPATIBLE_IGNORED.getBooleanValue()) {
            cir.setReturnValue(true);
        }
    }
}
