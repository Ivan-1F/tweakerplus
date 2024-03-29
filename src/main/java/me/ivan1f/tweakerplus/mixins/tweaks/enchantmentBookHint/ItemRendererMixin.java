package me.ivan1f.tweakerplus.mixins.tweaks.enchantmentBookHint;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow
    public float zOffset;

    @Inject(
            method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "RETURN")
    )
    private void onRenderItem(TextRenderer fontRenderer, ItemStack stack, int x, int y, String amountText, CallbackInfo ci) {
        if (!TweakerPlusConfigs.ENCHANTED_BOOK_HINT.getBooleanValue()) return;

        Item item = stack.getItem();
        if (!(item instanceof EnchantedBookItem)) return;
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(x / 2.0, y / 2.0, (this.zOffset + 200.0F));
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());

        Set<Map.Entry<Enchantment, Integer>> entries = EnchantmentHelper.getEnchantments(stack).entrySet();
        int currentY = y + 17 - entries.size() * 9 / 2;

        for (Map.Entry<Enchantment, Integer> entry : entries) {
            String string = entry.getKey().getName(entry.getValue()).getString();
            fontRenderer.draw(
                    string,
                    (float) (x + 17 - fontRenderer.getStringWidth(string) / 2),
                    (float) (currentY),
                    16777215,
                    true,
                    matrixStack.peek().getModel(),
                    immediate,
                    false,
                    0,
                    15728880
            );
            currentY += 9;
        }
        immediate.draw();
    }
}
