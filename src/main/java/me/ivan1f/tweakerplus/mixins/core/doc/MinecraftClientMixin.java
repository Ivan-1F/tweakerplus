package me.ivan1f.tweakerplus.mixins.core.doc;

import me.ivan1f.tweakerplus.util.doc.DocumentGeneration;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(
            method = "method_29338",
            at = @At("TAIL"),
            remap = false
    )
    private void onClientInitFinished(CallbackInfo ci) {
        DocumentGeneration.onClientInitFinished();
    }
}
