package me.ivan1f.tweakerplus.mixins.tweaks.enchantmentBookHint;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.ivan1f.tweakerplus.impl.enchantmentBookHint.EnchantmentBookHintRenderer;

@Mixin(DrawContext.class)
public class ItemRendererMixin {
    @Inject(
            method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V",
                    shift = At.Shift.AFTER
            )
    )
    private void enchantmentBookHint_impl(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        DrawContext self = (DrawContext) (Object) this;
        EnchantmentBookHintRenderer.render(self.getMatrices(), self, textRenderer, stack, x, y);
    }
}
