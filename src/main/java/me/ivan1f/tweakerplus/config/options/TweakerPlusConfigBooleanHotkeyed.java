package me.ivan1f.tweakerplus.config.options;

import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;

public class TweakerPlusConfigBooleanHotkeyed extends ConfigBooleanHotkeyed implements TweakerPlusIConfigBase {
    public TweakerPlusConfigBooleanHotkeyed(String name, boolean defaultValue, String defaultHotkey) {
        super(name, defaultValue, defaultHotkey, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX, TWEAKERPLUS_NAMESPACE_PREFIX + name + PRETTY_NAME_SUFFIX);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        boolean oldValue = this.getBooleanValue();

        super.setValueFromJsonElement(element);

        if (oldValue != this.getBooleanValue()) {
            this.onValueChanged();
        }
    }
}
