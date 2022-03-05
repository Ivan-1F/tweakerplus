package me.ivan1f.tweakerplus.config.options;

import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.options.ConfigDouble;

public class TweakerPlusConfigDouble extends ConfigDouble implements TweakerPlusIConfigBase {
    public TweakerPlusConfigDouble(String name, double defaultValue) {
        super(name, defaultValue, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }

    public TweakerPlusConfigDouble(String name, double defaultValue, double minValue, double maxValue) {
        super(name, defaultValue, minValue, maxValue, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        double oldValue = this.getDoubleValue();

        super.setValueFromJsonElement(element);

        if (oldValue != this.getDoubleValue()) {
            this.onValueChanged();
        }
    }
}
