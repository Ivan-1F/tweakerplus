package me.ivan1f.tweakerplus.impl.tweakpVillagerAutoTrade;

import fi.dy.masa.itemscroller.util.AccessorUtils;
import fi.dy.masa.itemscroller.util.InventoryUtils;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.InfoUtils;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.InventoryUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.container.MerchantContainer;
import net.minecraft.container.Slot;
import net.minecraft.container.TradeOutputSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class VillagerTrader {
    private final MerchantScreen screen;
    private final MerchantContainer container;
    private final RecipeStorage storage = RecipeStorage.getInstance();

    public VillagerTrader(MerchantScreen screen) {
        this.screen = screen;
        this.container = screen.getContainer();
    }

    public int getOfferIndex() {
        RecipeStorage.TradeRecipe recipe = this.storage.get(this.storage.getSelectedIndex());
        for (int i = 0; i < this.container.getRecipes().size(); i++) {
            TradeOffer offer = this.container.getRecipes().get(i);
            if (offer.getOriginalFirstBuyItem().getItem() == recipe.firstBuyItem.getItem() &&
                    offer.getSecondBuyItem().getItem() == recipe.secondBuyItem.getItem() &&
                    offer.getSellItem().getItem() == recipe.sellItem.getItem()) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unused")
    public static boolean storeRecipe(KeyAction keyAction, IKeybind iKeybind) {
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;
        if (currentScreen instanceof MerchantScreen && TweakerPlusConfigs.TWEAKP_AUTO_TRADE.getBooleanValue()) {
            Slot slotUnderMouse = AccessorUtils.getSlotUnderMouse((MerchantScreen) currentScreen);
            if (slotUnderMouse instanceof TradeOutputSlot) {
                if (slotUnderMouse.hasStack()) {
                    MerchantScreen merchantScreen = (MerchantScreen) currentScreen;
                    int selectedMerchantRecipe = AccessorUtils.getSelectedMerchantRecipe(merchantScreen);
                    TradeOffer offer = merchantScreen.getContainer().getRecipes().get(selectedMerchantRecipe);
                    if (offer != null) {
                        RecipeStorage.getInstance().setSelectedSlot(new RecipeStorage.TradeRecipe(
                                offer.getOriginalFirstBuyItem(),
                                offer.getSecondBuyItem(),
                                offer.getSellItem()
                        ));
                    }
                } else {
                    RecipeStorage.getInstance().setSelectedSlot(RecipeStorage.TradeRecipe.EMPTY);
                }
            }
        }
        return true;
    }

    public static TradeResult doTradeEverything() {
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;
        if (currentScreen instanceof MerchantScreen) {
            VillagerTrader trader = new VillagerTrader((MerchantScreen) currentScreen);
            return trader.tradeEverything();
        }
        return TradeResult.createFailedResult(TradeFailedReason.NOT_MERCHANT_SCREEN);
    }

    @SuppressWarnings("unused")
    public static boolean doTradeEverything(KeyAction keyAction, IKeybind iKeybind) {
        TradeResult result = doTradeEverything();
        result.printActionBarMessage();
        return true;
    }

    private boolean isResultSatisfied(ItemStack stack) {
        return this.container.getSlot(2).hasStack() &&
                InventoryUtils.areStacksEqual(this.container.getSlot(2).getStack(), stack);
    }

    public TradeResult tradeEverything() {
        if (this.getOfferIndex() == -1) return TradeResult.createFailedResult(TradeFailedReason.OFFER_NOT_FOUND);
        TradeOffer offer = this.container.getRecipes().get(this.getOfferIndex());
        if (offer == null) return TradeResult.createFailedResult(TradeFailedReason.OFFER_NOT_FOUND);
        if (offer.isDisabled()) return TradeResult.createFailedResult(TradeFailedReason.LOCKED);
        ItemStack sellItem = offer.getSellItem();
        int count = 0;
        this.prepareBuySlots();
        for (int failSafe = 1024; failSafe >= 0 && isResultSatisfied(sellItem); --failSafe) {
            this.processOutputSlot();
            this.prepareBuySlots();
            count++;
        }
        if (count == 0) {
            return TradeResult.createFailedResult(TradeFailedReason.LACK_OF_BUY_ITEMS);
        }
        return TradeResult.createSuccessResult(count);
    }

    public void processOutputSlot() {
        if (TweakerPlusConfigs.TWEAKP_AUTO_TRADE_THROW_OUTPUT.getBooleanValue()) {
            InventoryUtils.dropStacksUntilEmpty(this.screen, 2);
        } else {
            InventoryUtils.shiftClickSlot(this.screen, 2);
            if (this.container.getSlot(2).hasStack()) {
                InventoryUtils.dropStacksUntilEmpty(this.screen, 2);
            }
        }
    }

    private void prepareBuySlots() {
        TradeOffer offer = this.container.getRecipes().get(this.getOfferIndex());
        if (offer.isDisabled()) return;
        ItemStack firstBuyItem = offer.getAdjustedFirstBuyItem();
        ItemStack secondBuyItem = offer.getSecondBuyItem();
        clearVillagerTradingSlots();
        pickItemsAndPutToVillagerTradingSlot(firstBuyItem, 0);
        pickItemsAndPutToVillagerTradingSlot(secondBuyItem, 1);
    }

    private void clearVillagerTradingSlots() {
        InventoryUtils.leftClickSlot(this.screen, 0);
        InventoryUtils.tryClearCursor(this.screen, MinecraftClient.getInstance());
        InventoryUtils.leftClickSlot(this.screen, 1);
        InventoryUtils.tryClearCursor(this.screen, MinecraftClient.getInstance());
    }

    public void pickItemsAndPutToVillagerTradingSlot(ItemStack stack, int targetSlot) {
        List<Slot> playerInvSlots = this.container.slots.stream().filter(slot -> slot.inventory instanceof PlayerInventory).collect(Collectors.toList());
        InventoryUtil.pickItemsInCursor(this.screen, playerInvSlots, stack);
        InventoryUtils.leftClickSlot(this.screen, targetSlot);
    }

    public static class TradeResult {
        private final boolean success;
        private final int count;
        @Nullable final private TradeFailedReason reason;

        public TradeResult(boolean success, @Nullable TradeFailedReason reason) {
            this.success = success;
            this.count = -1;
            this.reason = reason;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public int getCount() {
            return this.count;
        }

        public @Nullable TradeFailedReason getReason() {
            return this.reason;
        }

        public TradeResult(boolean success, int count) {
            this.success = success;
            this.count = count;
            this.reason = null;
        }

        public static TradeResult createSuccessResult(int count) {
            return new TradeResult(true, count);
        }

        public static TradeResult createFailedResult(TradeFailedReason reason) {
            return new TradeResult(false, reason);
        }

        public void printActionBarMessage() {
            if (this.isSuccess()) {
                InfoUtils.printActionbarMessage("tweakerplus.config.tweakpAutoTradeEverything.traded_successfully", this.getCount());
            } else {
                assert this.getReason() != null;
                InfoUtils.printActionbarMessage("tweakerplus.config.tweakpAutoTradeEverything.traded_failed", this.getReason().getStringValue());
            }
        }
    }
}
