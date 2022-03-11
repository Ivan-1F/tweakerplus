package me.ivan1f.tweakerplus.util;

import me.ivan1f.tweakerplus.TweakerPlusMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class FileUtil {
    private static final String CONFIG_FILE_NAME = TweakerPlusMod.MOD_ID + ".json";

    /**
     * Use deprecation API for better old fabric loader version compatibility
     */
    @SuppressWarnings("deprecation")
    public static File getConfigFile() {
        return FabricLoader.getInstance().getConfigDirectory().toPath().resolve(CONFIG_FILE_NAME).toFile();
    }
}
