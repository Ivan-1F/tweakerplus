package me.ivan1f.tweakerplus.mixins.tweaks.tweakerpVillagerAutoTrade;

import fi.dy.masa.malilib.util.KeyCodes;
import me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade.RecipeStorage;
import me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade.VillagerTrader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends Screen {
    @Shadow private int selectedIndex;

    protected MerchantScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "method_19896", at = @At("TAIL"))
    private void onSelectedIndexUpdated(ButtonWidget buttonWidget, CallbackInfo ci) {
        VillagerTrader.selectedIndex = this.selectedIndex;
    }

    @Inject(method = "syncRecipeIndex", at = @At("TAIL"))
    private void onSelectedIndexSynced(CallbackInfo ci) {
        TradeOffer offer = ((MerchantScreen) (Object) this).getContainer().getRecipes().get(this.selectedIndex);
        if (offer != null) {
            RecipeStorage.getInstance().setSelectedSlot(new RecipeStorage.TradeRecipe(
                    offer.getOriginalFirstBuyItem(),
                    offer.getSecondBuyItem(),
                    offer.getSellItem()
            ));
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);
        if (keyCode == KeyCodes.KEY_UP) {
            RecipeStorage.getInstance().previous();
            return true;
        } else if (keyCode == KeyCodes.KEY_DOWN) {
            RecipeStorage.getInstance().next();
            return true;
        }
        return false;
    }
}
