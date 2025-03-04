package me.ivan1f.tweakerplus.mixins.tweaks.enchantmentBookHint;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.impl.enchantmentBookHint.EnchantmentBookHintRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 11900
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(
            //#if MC >= 11900
            //$$ method = "renderGuiItemOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            //#else
            method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            //#endif
            at = @At(value = "RETURN")
    )
    private void onRenderItem(
            //#if MC >= 11900
            //$$ MatrixStack matrixStack,
            //#endif
            TextRenderer fontRenderer, ItemStack stack, int x, int y, String amountText, CallbackInfo ci
    ) {
        if (!TweakerPlusConfigs.ENCHANTED_BOOK_HINT.getBooleanValue()) return;

        ItemRenderer self = (ItemRenderer) (Object) this;

        EnchantmentBookHintRenderer.render(
                //#if MC >= 11900
                //$$ matrixStack,
                //#endif
                self, fontRenderer, stack, x, y
        );
    }
}
