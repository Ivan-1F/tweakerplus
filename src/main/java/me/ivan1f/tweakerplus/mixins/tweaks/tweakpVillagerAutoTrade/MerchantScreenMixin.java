package me.ivan1f.tweakerplus.mixins.tweaks.tweakpVillagerAutoTrade;

import fi.dy.masa.malilib.util.KeyCodes;
import me.ivan1f.tweakerplus.impl.tweakpVillagerAutoTrade.RecipeStorage;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends Screen {
    protected MerchantScreenMixin(Text title) {
        super(title);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == KeyCodes.KEY_UP) {
            RecipeStorage.getInstance().previous();
            return true;
        } else if (keyCode == KeyCodes.KEY_DOWN) {
            RecipeStorage.getInstance().next();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
