package me.ivan1f.tweakerplus.config.options;

import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.options.ConfigBoolean;

public class TweakerPlusConfigBoolean extends ConfigBoolean implements TweakerPlusIConfigBase {
    public TweakerPlusConfigBoolean(String name, boolean defaultValue) {
        super(name, defaultValue, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
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
