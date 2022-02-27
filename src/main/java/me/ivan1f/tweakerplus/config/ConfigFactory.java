package me.ivan1f.tweakerplus.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import me.ivan1f.tweakerplus.config.options.*;

public abstract class ConfigFactory {
    public static TweakerPlusConfigHotkey newConfigHotKey(String name, String defaultStorageString) {
        return new TweakerPlusConfigHotkey(name, defaultStorageString);
    }

    public static TweakerPlusConfigHotkey newConfigHotKey(String name, String defaultStorageString, KeybindSettings settings) {
        return new TweakerPlusConfigHotkey(name, defaultStorageString, settings);
    }

    public static TweakerPlusConfigBooleanHotkeyed newConfigBooleanHotkeyed(String name) {
        return newConfigBooleanHotkeyed(name, false, "");
    }

    public static TweakerPlusConfigBooleanHotkeyed newConfigBooleanHotkeyed(String name, boolean defaultValue, String defaultStorageString) {
        return new TweakerPlusConfigBooleanHotkeyed(name, defaultValue, defaultStorageString);
    }

    public static TweakerPlusConfigBoolean newConfigBoolean(String name, boolean defaultValue) {
        return new TweakerPlusConfigBoolean(name, defaultValue);
    }

    public static TweakerPlusConfigInteger newConfigInteger(String name, int defaultValue) {
        return new TweakerPlusConfigInteger(name, defaultValue);
    }

    public static TweakerPlusConfigInteger newConfigInteger(String name, int defaultValue, int minValue, int maxValue) {
        return new TweakerPlusConfigInteger(name, defaultValue, minValue, maxValue);
    }

    public static TweakerPlusConfigDouble newConfigDouble(String name, double defaultValue) {
        return new TweakerPlusConfigDouble(name, defaultValue);
    }

    public static TweakerPlusConfigDouble newConfigDouble(String name, double defaultValue, double minValue, double maxValue) {
        return new TweakerPlusConfigDouble(name, defaultValue, minValue, maxValue);
    }

    public static TweakerPlusConfigString newConfigString(String name, String defaultValue) {
        return new TweakerPlusConfigString(name, defaultValue);
    }

    public static TweakerPlusConfigStringList newConfigStringList(String name, ImmutableList<String> defaultValue) {
        return new TweakerPlusConfigStringList(name, defaultValue);
    }

    public static TweakerPlusConfigOptionList newConfigOptionList(String name, IConfigOptionListEntry defaultValue) {
        return new TweakerPlusConfigOptionList(name, defaultValue);
    }
}
