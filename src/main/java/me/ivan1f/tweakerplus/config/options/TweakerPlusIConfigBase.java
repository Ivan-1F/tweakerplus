package me.ivan1f.tweakerplus.config.options;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.config.TweakerPlusOption;

import java.util.function.Function;

public interface TweakerPlusIConfigBase extends IConfigBase {
    String TWEAKERPLUS_NAMESPACE_PREFIX = TweakerPlusMod.MOD_ID + ".config.";
    String COMMENT_SUFFIX = ".comment";
    String PRETTY_NAME_SUFFIX = ".pretty_name";

    @Override
    default String getConfigGuiDisplayName() {
        return StringUtils.translate(TWEAKERPLUS_NAMESPACE_PREFIX + this.getName());
    }

    default TweakerPlusOption getTweakerPlusOption() {
        return TweakerPlusConfigs.getOptionFromConfig(this).orElseThrow(() -> new RuntimeException("TweakerPlusIConfigBase " + this + " not in TweakerPlusConfigs"));
    }

    default Function<String, String> getGuiDisplayLineModifier() {
        TweakerPlusOption tweakerPlusOption = this.getTweakerPlusOption();
        if (!tweakerPlusOption.isEnabled()) {
            return line -> GuiBase.TXT_DARK_RED + line + GuiBase.TXT_RST;
        }
        if (tweakerPlusOption.isDebug()) {
            return line -> GuiBase.TXT_BLUE + line + GuiBase.TXT_RST;
        }
        if (tweakerPlusOption.isDevOnly()) {
            return line -> GuiBase.TXT_LIGHT_PURPLE + line + GuiBase.TXT_RST;
        }
        return line -> line;
    }
}
