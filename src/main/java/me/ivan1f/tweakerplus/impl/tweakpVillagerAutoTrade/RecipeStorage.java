package me.ivan1f.tweakerplus.impl.tweakpVillagerAutoTrade;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.fallenbreath.tweakermore.util.IdentifierUtils;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.util.JsonSavable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.List;

public class RecipeStorage implements JsonSavable {
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

    private JsonObject dumpItemStack(ItemStack stack) {
        JsonObject jsonObject = new JsonObject();
        if (!stack.isEmpty()) {
            jsonObject.addProperty("item", Registry.ITEM.getId(stack.getItem()).toString());
            jsonObject.addProperty("count", stack.getCount());
        }
        return jsonObject;
    }

    private ItemStack loadItemStack(JsonObject jsonObject) {
        try {
            if (!(jsonObject.has("item") && jsonObject.has("count"))) {
                return ItemStack.EMPTY;
            }
            String id = jsonObject.get("item").getAsString();
            Item item = Registry.ITEM.get(IdentifierUtils.of(id));
            int count = jsonObject.get("count").getAsInt();
            return new ItemStack(item, count);
        } catch (Exception e) {
            TweakerPlusMod.LOGGER.warn("Failed to load item stack: " + e);
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void dumpToJson(JsonObject jsonObject) {
        jsonObject.addProperty("selectedIndex", this.selectedIndex);
        JsonArray recipes = new JsonArray();
        for (TradeRecipe recipe : this.storage) {
            JsonObject recipeObject = new JsonObject();
            recipeObject.add("firstBuyItem", dumpItemStack(recipe.firstBuyItem));
            recipeObject.add("secondBuyItem", dumpItemStack(recipe.secondBuyItem));
            recipeObject.add("sellItem", dumpItemStack(recipe.sellItem));
            recipes.add(recipeObject);
        }
        jsonObject.add("recipes", recipes);
    }

    @Override
    public void loadFromJson(JsonObject jsonObject) {
        try {
            this.selectedIndex = jsonObject.get("selectedIndex").getAsInt();
            JsonArray recipes = jsonObject.get("recipes").getAsJsonArray();
            for (int i = 0; i < recipes.size(); i++) {
                JsonObject object = recipes.get(i).getAsJsonObject();
                TradeRecipe recipe = new TradeRecipe(
                        loadItemStack(object.get("firstBuyItem").getAsJsonObject()),
                        loadItemStack(object.get("secondBuyItem").getAsJsonObject()),
                        loadItemStack(object.get("sellItem").getAsJsonObject())
                );
                this.set(i, recipe);
            }
        } catch (Exception e) {
            TweakerPlusMod.LOGGER.warn("Failed to load AutoTrade RecipeStorage from JSON: " + e);
        }
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
