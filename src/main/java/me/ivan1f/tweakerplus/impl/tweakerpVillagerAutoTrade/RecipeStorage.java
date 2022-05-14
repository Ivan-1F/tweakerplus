package me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RecipeStorage {
    private static final int CAPACITY = 10;
    private static final RecipeStorage INSTANCE = new RecipeStorage();

    public static RecipeStorage getInstance() {
        return INSTANCE;
    }

    private final TradeRecipe[] storage = new TradeRecipe[CAPACITY];
    private int selectedIndex = 0;

    public RecipeStorage() {
        Arrays.fill(this.storage, TradeRecipe.EMPTY);
    }

    public TradeRecipe get(int index) {
        return this.storage[index];
    }

    public List<TradeRecipe> get() {
        return Lists.newArrayList(this.storage);
    }

    public void next() {
        this.selectedIndex = Math.min(this.selectedIndex + 1, CAPACITY - 1);
    }

    public void previous() {
        this.selectedIndex = Math.max(this.selectedIndex - 1, 0);
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public void setSelectedSlot(TradeRecipe recipe) {
        this.set(this.getSelectedIndex(), recipe);
    }

    public void set(int index, TradeRecipe recipe) {
        this.storage[index] = recipe;
    }

    public static class TradeRecipe {
        public static final TradeRecipe EMPTY = new TradeRecipe(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
        public final ItemStack firstBuyItem;
        public final ItemStack secondBuyItem;
        public final ItemStack sellItem;

        public TradeRecipe(ItemStack firstBuyItem, ItemStack secondBuyItem, ItemStack sellItem) {
            this.firstBuyItem = firstBuyItem;
            this.secondBuyItem = secondBuyItem;
            this.sellItem = sellItem;
        }
    }
}
