package me.ivan1f.tweakerplus.mixins.tweaks.tweakpVillagerAutoTrade;

import fi.dy.masa.malilib.util.InfoUtils;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.impl.tweakpVillagerAutoTrade.VillagerTrader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.container.MerchantContainer;
import net.minecraft.village.TraderOfferList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantContainer.class)
public class MerchantContainerMixin {
    @Inject(method = "setOffers", at = @At("TAIL"))
    private void setOffers(TraderOfferList traderOfferList, CallbackInfo ci) {
        if (!TweakerPlusConfigs.TWEAKP_AUTO_TRADE_EVERYTHING.getBooleanValue() || !TweakerPlusConfigs.TWEAKP_AUTO_TRADE.getBooleanValue()) return;
        VillagerTrader.TradeResult result = VillagerTrader.doTradeEverything();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.closeContainer();
        }
        if (result.isSuccess()) {
            InfoUtils.printActionbarMessage("tweakerplus.config.tweakpAutoTradeEverything.traded_successfully", result.getCount());
        } else {
            assert result.getReason() != null;
            InfoUtils.printActionbarMessage("tweakerplus.config.tweakpAutoTradeEverything.traded_failed", result.getReason().getStringValue());
        }
    }
}
