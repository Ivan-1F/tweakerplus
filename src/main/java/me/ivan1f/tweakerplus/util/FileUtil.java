package me.ivan1f.tweakerplus.util;

import me.ivan1f.tweakerplus.TweakerPlusMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    private static final String CONFIG_FILE_NAME = TweakerPlusMod.MOD_ID + ".json";

    /**
     * Use deprecation API for better old fabric loader version compatibility
     */
    @SuppressWarnings("deprecation")
    public static File getConfigFile() {
        return FabricLoader.getInstance().getConfigDirectory().toPath().resolve(CONFIG_FILE_NAME).toFile();
    }

    public static void writeToFile(Path path, String text) throws IOException {
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(text);
        }
    }
}
