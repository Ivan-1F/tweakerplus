package me.ivan1f.tweakerplus.config.options;

import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.config.options.ConfigOptionList;

public class TweakerPlusConfigOptionList extends ConfigOptionList implements TweakerPlusIConfigBase {
    public TweakerPlusConfigOptionList(String name, IConfigOptionListEntry defaultValue) {
        super(name, defaultValue, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        IConfigOptionListEntry oldValue = this.getOptionListValue();

        super.setValueFromJsonElement(element);

        if (oldValue != this.getOptionListValue()) {
            this.onValueChanged();
        }
    }
}
