package me.ivan1f.tweakerplus.util.doc;

import fi.dy.masa.malilib.config.*;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.config.TweakerPlusOption;
import me.ivan1f.tweakerplus.config.options.TweakerPlusIConfigBase;
import me.ivan1f.tweakerplus.mixins.core.doc.ConfigBaseAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static me.ivan1f.tweakerplus.util.doc.MarkdownUtil.inlineCode;
import static me.ivan1f.tweakerplus.util.doc.MarkdownUtil.italic;

public class ConfigFormatter {
    public final TweakerPlusOption option;

    private String tr(String key, Object... args) {
        return StringUtils.translate("tweakerplus.doc_gen." + key, args);
    }

    public ConfigFormatter(TweakerPlusOption option) {
        this.option = option;
    }

    private TweakerPlusIConfigBase getConfig() {
        return this.option.getConfig();
    }

    public String getId() {
        return this.getConfig().getName();
    }

    public String getName() {
        if (this.getNameSimple().equals(this.getId())) {
            return this.getId();
        } else {
            return String.format("%s (%s)", getNameSimple(), this.getId());
        }
    }

    public String getNameSimple() {
        String translated = this.getConfig().getConfigGuiDisplayName();
        if (translated.equals(this.getId())) {
            return this.getId();
        }
        return String.format("%s", translated);
    }

    public String getLink(String lang) {
        String base = new ConfigDocumentGenerator().getFileName(lang);
        if (this.getNameSimple().equals(this.getId())) {
            return base + "#" + this.getId();
        } else {
            return base + String.format("#%s-%s", this.getNameSimple(), this.getId());
        }
    }

    public String getComment() {
        return this.getConfig() instanceof ConfigBaseAccessor ? StringUtils.translate(((ConfigBaseAccessor) this.getConfig()).getCommentKey()) : this.getConfig().getComment();
    }

    public String getType() {
        String id = this.getConfig() instanceof IHotkeyTogglable ? "hotkey_togglable" : this.getConfig().getType().name().toLowerCase();
        return this.tr("type." + id);
    }

    @Nullable
    public String getDefaultValue() {
        String hotkey = "";
        if (this.getConfig() instanceof IHotkey) {
            hotkey = ((IHotkey) this.getConfig()).getKeybind().getDefaultStringValue();
            if (hotkey.isEmpty()) {
                hotkey = italic(this.tr("value.no_hotkey"));
            } else {
                hotkey = inlineCode(hotkey);
            }
        }

        if (this.getConfig() instanceof IHotkeyTogglable) {
            return hotkey + ", " + inlineCode(String.valueOf(((IHotkeyTogglable) this.getConfig()).getDefaultBooleanValue()));
        } else if (this.getConfig() instanceof IHotkey) {
            return hotkey;
        } else if (this.getConfig() instanceof IStringRepresentable) {
            return inlineCode(((IStringRepresentable) this.getConfig()).getDefaultStringValue());
        } else if (this.getConfig() instanceof IConfigStringList) {
            return inlineCode(((IConfigStringList) this.getConfig()).getDefaultStrings().toString());
        } else if (this.getConfig() instanceof IConfigOptionList) {
            return inlineCode(((IConfigOptionList) this.getConfig()).getDefaultOptionListValue().getDisplayName());
        }
        TweakerPlusMod.LOGGER.warn("Unknown type found in getDefaultValue: {}", this.getConfig().getClass());
        return italic("unknown type: " + this.getConfig().getClass().getName());
    }

    public Optional<Number> getMinValue() {
        if (this.getConfig() instanceof IConfigInteger) {
            return Optional.of(((IConfigInteger) this.getConfig()).getMinIntegerValue());
        } else if (this.getConfig() instanceof IConfigDouble) {
            return Optional.of(((IConfigDouble) this.getConfig()).getMinDoubleValue());
        }
        return Optional.empty();
    }

    public Optional<Number> getMaxValue() {
        if (this.getConfig() instanceof IConfigInteger) {
            return Optional.of(((IConfigInteger) this.getConfig()).getMaxIntegerValue());
        } else if (this.getConfig() instanceof IConfigDouble) {
            return Optional.of(((IConfigDouble) this.getConfig()).getMaxDoubleValue());
        }
        return Optional.empty();
    }

    public String getCategory() {
        return this.option.getCategory().getDisplayName();
    }
}
