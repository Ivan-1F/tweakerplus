package me.ivan1f.tweakerplus.config.options;

import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.options.ConfigInteger;

public class TweakerPlusConfigInteger extends ConfigInteger implements TweakerPlusIConfigBase {
    public TweakerPlusConfigInteger(String name, int defaultValue) {
        super(name, defaultValue, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }

    public TweakerPlusConfigInteger(String name, int defaultValue, int minValue, int maxValue) {
        super(name, defaultValue, minValue, maxValue, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        int oldValue = this.getIntegerValue();

        super.setValueFromJsonElement(element);

        if (oldValue != this.getIntegerValue()) {
            this.onValueChanged();
        }
    }
}
