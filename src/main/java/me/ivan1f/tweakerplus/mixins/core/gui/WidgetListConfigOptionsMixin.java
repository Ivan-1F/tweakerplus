package me.ivan1f.tweakerplus.mixins.core.gui;

import com.mojang.datafixers.util.Pair;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptionsBase;
import me.ivan1f.tweakerplus.gui.TweakerPlusConfigGui;
import me.ivan1f.tweakerplus.gui.TweakerPlusOptionLabel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(WidgetListConfigOptions.class)
public abstract class WidgetListConfigOptionsMixin extends WidgetListConfigOptionsBase<GuiConfigsBase.ConfigOptionWrapper, WidgetConfigOption> {
    @Shadow(remap = false)
    @Final
    protected GuiConfigsBase parent;

    public WidgetListConfigOptionsMixin(int x, int y, int width, int height, int configWidth) {
        super(x, y, width, height, configWidth);
    }

    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetSearchBar;<init>(IIIIILfi/dy/masa/malilib/gui/interfaces/IGuiIcon;Lfi/dy/masa/malilib/gui/LeftRight;)V"
            ),
            index = 2,
            remap = false
    )
    private int tweakerPlusSearchBarWidth(int width) {
        if (this.parent instanceof TweakerPlusConfigGui) {
            // a default value.
            // a more precise width control wil be applied during the initGui of TweakerPlusConfigGui
            width -= 150;
        }
        return width;
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL"),
            remap = false
    )
    private void tweakerPlusRecordSearchBar(CallbackInfo ci) {
        if (this.parent instanceof TweakerPlusConfigGui) {
            ((TweakerPlusConfigGui) this.parent).setSearchBar(this.widgetSearchBar);
        }
    }

    @ModifyVariable(
            method = "getMaxNameLengthWrapped",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;iterator()Ljava/util/Iterator;",
                    remap = false
            ),
            remap = false
    )
    private int updateMaxNameLengthIfUsingTweakerPlusOptionLabelAndShowsOriginalText$tweakerplus(int maxWidth, List<GuiConfigsBase.ConfigOptionWrapper> wrappers) {
        if (this.parent instanceof TweakerPlusConfigGui) {
            for (GuiConfigsBase.ConfigOptionWrapper wrapper : wrappers) {
                if (wrapper.getType() == GuiConfigsBase.ConfigOptionWrapper.Type.CONFIG) {
                    IConfigBase config = Objects.requireNonNull(wrapper.getConfig());
                    maxWidth = Math.max(maxWidth, this.getStringWidth(config.getConfigGuiDisplayName()));
                    if (TweakerPlusOptionLabel.willShowOriginalLines(new String[]{config.getConfigGuiDisplayName()}, new String[]{config.getName()})) {
                        maxWidth = Math.max(maxWidth, (int) (this.getStringWidth(config.getName()) * TweakerPlusOptionLabel.TRANSLATION_SCALE));
                    }
                }
            }
        }
        return maxWidth;
    }

    @Inject(
            method = "reCreateListEntryWidgets",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetListConfigOptionsBase;reCreateListEntryWidgets()V",
                    remap = false
            ),
            remap = false
    )
    private void adjustConfigAndOptionPanelWidth$tweakerplus(CallbackInfo ci) {
        if (this.parent instanceof TweakerPlusConfigGui) {
            Pair<Integer, Integer> result = ((TweakerPlusConfigGui) this.parent).adjustWidths(this.totalWidth, this.maxLabelWidth);
            this.maxLabelWidth = result.getFirst();
            this.configWidth = result.getSecond();
        }
    }
}
