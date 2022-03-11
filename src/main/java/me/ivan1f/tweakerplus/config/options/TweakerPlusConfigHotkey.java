package me.ivan1f.tweakerplus.config.options;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class TweakerPlusConfigHotkey extends ConfigHotkey implements TweakerPlusIConfigBase {
    public TweakerPlusConfigHotkey(String name, String defaultStorageString) {
        super(name, defaultStorageString, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }

    public TweakerPlusConfigHotkey(String name, String defaultStorageString, KeybindSettings settings) {
        super(name, defaultStorageString, KeybindSettings.DEFAULT, TWEAKERPLUS_NAMESPACE_PREFIX + name + COMMENT_SUFFIX);
    }
}
