package me.ivan1f.tweakerplus.gui;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetLabel;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.config.Config;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.config.TweakerPlusOption;
import me.ivan1f.tweakerplus.util.FabricUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class TweakerPlusConfigGui extends GuiConfigsBase {
    @Nullable
    private static TweakerPlusConfigGui currentInstance = null;
    private static Config.Category category = Config.Category.MC_TWEAKS;
    @Nullable
    private Config.Type filteredType = null;
    @Nullable
    private SelectorDropDownList<Config.Type> typeFilterDropDownList = null;

    public TweakerPlusConfigGui() {
        super(10, 50, TweakerPlusMod.MOD_ID, null, "tweakerplus.gui.title", TweakerPlusMod.VERSION);
        currentInstance = this;
    }

    @Override
    public void removed() {
        super.removed();
        currentInstance = null;
    }

    public static Optional<TweakerPlusConfigGui> getCurrentInstance() {
        return Optional.ofNullable(currentInstance);
    }

    public static boolean onOpenGuiHotkey(KeyAction keyAction, IKeybind iKeybind) {
        GuiBase.openGui(new TweakerPlusConfigGui());
        return true;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (Config.Category category : Config.Category.values()) {
            x += this.createNavigationButton(x, y, category);
        }

        Set<Config.Type> possibleTypes = TweakerPlusConfigs.getOptions(TweakerPlusConfigGui.category).stream().map(TweakerPlusOption::getType).collect(Collectors.toSet());
        List<Config.Type> items = Arrays.stream(Config.Type.values()).filter(possibleTypes::contains).collect(Collectors.toList());
        items.add(0, null);
        SelectorDropDownList<Config.Type> dd = new SelectorDropDownList<>(this.width - 91, this.getListY() + 3, 80, 16, 200, items.size(), items);
        dd.setEntryChangeListener(type -> {
            if (type != this.filteredType) {
                this.filteredType = type;
                this.reDraw();
            }
        });
        this.addWidget(dd);
        this.typeFilterDropDownList = dd;
        dd.setSelectedEntry(this.filteredType);

        String labelTextKey = "tweakerplus.gui.config_type.label_text";
        int labelWidth = this.getStringWidth(StringUtils.translate(labelTextKey));
        WidgetLabel label = new WidgetLabel(dd.getX() - labelWidth - 5, dd.getY() + 1, labelWidth, dd.getHeight(), 0xFFE0E0E0, labelTextKey);
        this.addWidget(label);
    }

    private int createNavigationButton(int x, int y, Config.Category category) {
        ButtonGeneric button = new ButtonGeneric(x, y, -1, 20, category.getDisplayName());
        button.setEnabled(TweakerPlusConfigGui.category != category);
        this.addButton(button, (b, mouseButton) -> {
            TweakerPlusConfigGui.category = category;
            this.reDraw();
        });
        return button.getWidth() + 2;
    }

    public void reDraw() {
        this.reCreateListWidget(); // apply the new config width
        Objects.requireNonNull(this.getListWidget()).resetScrollbarPosition();
        this.initGui();
    }

    public void renderDropDownList(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.typeFilterDropDownList != null) {
            this.typeFilterDropDownList.render(mouseX, mouseY, this.typeFilterDropDownList.isMouseOver(mouseX, mouseY), matrixStack);
        }
    }

    public Pair<Integer, Integer> adjustWidths(int guiWidth, int maxTextWidth) {
        int labelWidth;
        int panelWidth = 190;
        guiWidth -= 75;

        // tweak label width first, to make sure the panel is not too close or too far from the label
        labelWidth = MathHelper.clamp(guiWidth - panelWidth, maxTextWidth - 5, maxTextWidth + 100);
        // decrease the panel width if space is not enough
        panelWidth = MathHelper.clamp(guiWidth - labelWidth, 100, panelWidth);
        // decrease the label width for a bit if space is still way not enough (the label text might overlap with the panel now)
        labelWidth = MathHelper.clamp(guiWidth - panelWidth + 25, labelWidth - Math.max((int) (maxTextWidth * 0.4), 30), labelWidth);

        // just in case
        labelWidth = Math.max(labelWidth, 0);
        panelWidth = Math.max(panelWidth, 0);

        return Pair.of(labelWidth, panelWidth);
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<IConfigBase> configs = Lists.newArrayList();
        for (TweakerPlusOption tweakerPlusOption : TweakerPlusConfigs.getOptions(TweakerPlusConfigGui.category)) {
            // drop down list filtering logic
            if (this.filteredType != null && tweakerPlusOption.getType() != this.filteredType) {
                continue;
            }
            // hide disable options if config hideDisabledOptions is enabled
            if (TweakerPlusConfigs.HIDE_DISABLE_OPTIONS.getBooleanValue() && !tweakerPlusOption.isEnabled()) {
                continue;
            }
            // hide options that don't work with current Minecraft versions, unless debug mode on
            if (!tweakerPlusOption.worksForCurrentMCVersion() && !TweakerPlusConfigs.TWEAKERPLUS_DEBUG_MODE.getBooleanValue()) {
                continue;
            }
            // hide debug options unless debug mode on
            if (tweakerPlusOption.isDebug() && !TweakerPlusConfigs.TWEAKERPLUS_DEBUG_MODE.getBooleanValue()) {
                continue;
            }
            // hide dev only options unless debug mode on and is dev env
            if (tweakerPlusOption.isDevOnly() && !(TweakerPlusConfigs.TWEAKERPLUS_DEBUG_MODE.getBooleanValue() && FabricUtil.isDevelopmentEnvironment())) {
                continue;
            }
            configs.add(tweakerPlusOption.getConfig());
        }
        configs.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return ConfigOptionWrapper.createFor(configs);
    }
}
