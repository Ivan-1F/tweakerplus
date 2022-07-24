package me.ivan1f.tweakerplus.impl.reloadGameOptions;

import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.InfoUtils;
import net.minecraft.client.MinecraftClient;

public class GameOptionsReloader {
    public static boolean reloadGameOptions(KeyAction keyAction, IKeybind iKeybind) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return false;
        client.options.load();
        InfoUtils.printActionbarMessage("tweakerplus.config.reloadGameOptions.options_reloaded");
        return true;
    }
}
