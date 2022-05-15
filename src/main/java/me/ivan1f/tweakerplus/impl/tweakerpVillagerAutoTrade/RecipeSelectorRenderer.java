package me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade;

import fi.dy.masa.itemscroller.util.AccessorUtils;
import fi.dy.masa.itemscroller.util.InventoryUtils;
import fi.dy.masa.malilib.render.InventoryOverlay;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.render.RenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.item.ItemStack;

public class RecipeSelectorRenderer {
    private static final RecipeSelectorRenderer INSTANCE = new RecipeSelectorRenderer();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public static RecipeSelectorRenderer getInstance() {
        return INSTANCE;
    }

    public void onDrawBackgroundPost() {
        if (GuiUtils.getCurrentScreen() instanceof MerchantScreen && TweakerPlusConfigs.TWEAKP_AUTO_TRADE.getBooleanValue()) {
            MerchantScreen screen = (MerchantScreen) GuiUtils.getCurrentScreen();
            RecipeStorage storage = RecipeStorage.getInstance();

            RenderContext renderContext = new RenderContext();
            renderContext.pushMatrix();

            int x = AccessorUtils.getGuiLeft(screen) - 16 - 1 - 16 - 1 - 16 - 10;
            int y = AccessorUtils.getGuiTop(screen) - 1;
            for (int i = 0; i < storage.get().size(); i++) {
                y = renderRecipe(storage.get(i), x, y, i == storage.getSelectedIndex());
            }

            renderContext.popMatrix();
        }
    }

    public void onDrawScreenPost() {
        if (GuiUtils.getCurrentScreen() instanceof MerchantScreen && TweakerPlusConfigs.TWEAKP_AUTO_TRADE.getBooleanValue()) {
            final int mouseX = fi.dy.masa.malilib.util.InputUtils.getMouseX();
            final int mouseY = fi.dy.masa.malilib.util.InputUtils.getMouseY();

            RenderContext renderContext = new RenderContext();
            renderContext.pushMatrix();
            renderContext.translate(0F, 0F, 300F);  // render tooltips at top

            this.renderHoverTooltip(mouseX, mouseY, (MerchantScreen) GuiUtils.getCurrentScreen());

            renderContext.popMatrix();
        }
    }

    private void renderHoverTooltip(int mouseX, int mouseY, MerchantScreen screen) {
        ItemStack stack = null;
        RecipeStorage storage = RecipeStorage.getInstance();

        if (mouseY <= (AccessorUtils.getGuiTop(screen) - 1) || mouseY >= (AccessorUtils.getGuiTop(screen) - 1 + (16 + 1) * 10)) {
            return;
        }

        int index = (mouseY - (AccessorUtils.getGuiTop(screen) - 1)) / (16 + 1);
        RecipeStorage.TradeRecipe recipe = storage.get(index);

        if (mouseX >= AccessorUtils.getGuiLeft(screen) - 16 - 1 - 16 - 1 - 16 - 10 && mouseX <= AccessorUtils.getGuiLeft(screen) - 1 - 16 - 1 - 16 - 10) {
            stack = recipe.firstBuyItem;
        } else if (mouseX >= AccessorUtils.getGuiLeft(screen) - 16 - 1 - 16 - 10 && mouseX <= AccessorUtils.getGuiLeft(screen) - 1 - 16 - 10) {
            stack = recipe.secondBuyItem;
        } else if (mouseX >= AccessorUtils.getGuiLeft(screen) - 16 - 1 && mouseX <= AccessorUtils.getGuiLeft(screen) - 1) {
            stack = recipe.sellItem;
        }

        if (stack == null) {
            return;
        }

        if (!InventoryUtils.isStackEmpty(stack)) {
            InventoryOverlay.renderStackToolTip(mouseX, mouseY, stack, this.client);
        }
    }

    private int renderRecipe(RecipeStorage.TradeRecipe recipe, int x, int y, boolean selected) {
        this.renderStackAt(recipe.firstBuyItem, x, y, false);
        this.renderStackAt(recipe.secondBuyItem, x + 16 + 1, y, false);
        return this.renderStackAt(recipe.sellItem, x + 16 + 1 + 16 + 10, y, selected) + 1;
    }

    /**
     * Reference: fi.dy.masa.itemscroller.event.RenderEventHandler#renderStackAt
     */
    private int renderStackAt(ItemStack stack, int x, int y, boolean border) {
        RenderContext renderContext = new RenderContext();
        renderContext.pushMatrix();

        int w = 16;

        if (border) {
            // Draw a light/white border around the stack
            RenderUtils.drawRect(x - 1, y - 1, w + 1, 1, 0xFFFFFFFF);
            RenderUtils.drawRect(x - 1, y, 1, w + 1, 0xFFFFFFFF);
            RenderUtils.drawRect(x + w, y - 1, 1, w + 1, 0xFFFFFFFF);
            RenderUtils.drawRect(x, y + w, w + 1, 1, 0xFFFFFFFF);

            RenderUtils.drawRect(x, y, w, w, 0x20FFFFFF); // light background for the item

        } else {
            RenderUtils.drawRect(x, y, w, w, 0x20FFFFFF); // light background for the item
        }

        if (!InventoryUtils.isStackEmpty(stack)) {
            DiffuseLighting.enableGuiDepthLighting();

            stack = stack.copy();
            InventoryUtils.setStackSize(stack, 1);
            this.client.getItemRenderer().zOffset += 100;
            this.client.getItemRenderer().renderGuiItem(this.client.player, stack, x, y);
            this.client.getItemRenderer().zOffset -= 100;
        }

        renderContext.popMatrix();

        return y + w;
    }
}
