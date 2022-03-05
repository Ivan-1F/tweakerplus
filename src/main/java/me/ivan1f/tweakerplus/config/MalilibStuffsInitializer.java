package me.ivan1f.tweakerplus.config;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import me.ivan1f.tweakerplus.TweakerPlusMod;

public class MalilibStuffsInitializer {
    public static void init() {
        InitializationHandler.getInstance().registerInitializationHandler(() -> {
            ConfigManager.getInstance().registerConfigHandler(TweakerPlusMod.MOD_ID, new TweakerPlusConfigStorage());

            InputEventHandler.getKeybindManager().registerKeybindProvider(new KeybindProvider());

            TweakerPlusConfigs.initCallbacks();
        });
    }
}
