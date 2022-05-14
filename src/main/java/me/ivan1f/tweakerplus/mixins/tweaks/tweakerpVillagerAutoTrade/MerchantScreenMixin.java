package me.ivan1f.tweakerplus.mixins.tweaks.tweakerpVillagerAutoTrade;

import me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade.VillagerTrader;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin {
    @Shadow private int selectedIndex;

    @Inject(method = "method_19896", at = @At("TAIL"))
    private void onSelectedIndexUpdated(ButtonWidget buttonWidget, CallbackInfo ci) {
        VillagerTrader.selectedIndex = this.selectedIndex;
    }
}
