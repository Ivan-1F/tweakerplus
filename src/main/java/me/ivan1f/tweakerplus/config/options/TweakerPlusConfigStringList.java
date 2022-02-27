package me.ivan1f.tweakerplus.config.options;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.options.ConfigStringList;

import java.util.List;

public class TweakerPlusConfigStringList extends ConfigStringList implements TweakerPlusIConfigBase {
    public TweakerPlusConfigStringList(String name, ImmutableList<String> defaultValue) {
        super(name, defaultValue, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        List<String> oldValue = Lists.newArrayList(this.getStrings());

        super.setValueFromJsonElement(element);

        if (!oldValue.equals(this.getStrings())) {
            this.onValueChanged();
        }
    }
}
