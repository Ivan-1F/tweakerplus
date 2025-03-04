package me.ivan1f.tweakerplus.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.JsonUtils;
import me.fallenbreath.tweakermore.util.FabricUtils;
import me.ivan1f.tweakerplus.util.FileUtil;
import me.ivan1f.tweakerplus.util.InternalSaver;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class TweakerPlusConfigStorage implements IConfigHandler {
    private static JsonObject ROOT_JSON_OBJ = new JsonObject();

    public static void loadFromFile() {
        File configFile = FileUtil.getConfigFile();
        if (FileUtil.ensureFileReadable(configFile)) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();
                loadFromJson(root);
                ROOT_JSON_OBJ = root;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends IConfigBase> List<T> getConfigOptions(Config.Type optionType) {
        return (List<T>) TweakerPlusConfigs.getOptions(optionType).stream().
                filter(o -> !o.isDevOnly() || FabricUtils.isDevelopmentEnvironment()).
                map(TweakerPlusOption::getConfig).
                collect(Collectors.toList());
    }

    public static void loadFromJson(JsonObject jsonObject) {
        ConfigUtils.readConfigBase(jsonObject, "Generic", getConfigOptions(Config.Type.GENERIC));
        ConfigUtils.readConfigBase(jsonObject, "GenericHotkeys", getConfigOptions(Config.Type.HOTKEY));
        ConfigUtils.readConfigBase(jsonObject, "Lists", getConfigOptions(Config.Type.LIST));
        ConfigUtils.readHotkeyToggleOptions(jsonObject, "TweakHotkeys", "TweakToggles", getConfigOptions(Config.Type.TWEAK));
        ConfigUtils.readHotkeyToggleOptions(jsonObject, "DisableHotkeys", "DisableToggles", getConfigOptions(Config.Type.DISABLE));

        InternalSaver.load();
    }

    public static void saveToFile() {
        File configFile = FileUtil.getConfigFile();
        JsonObject root = ROOT_JSON_OBJ;

        ConfigUtils.writeConfigBase(root, "Generic", getConfigOptions(Config.Type.GENERIC));
        ConfigUtils.writeConfigBase(root, "GenericHotkeys", getConfigOptions(Config.Type.HOTKEY));
        ConfigUtils.writeConfigBase(root, "Lists", getConfigOptions(Config.Type.LIST));
        ConfigUtils.writeHotkeyToggleOptions(root, "TweakHotkeys", "TweakToggles", getConfigOptions(Config.Type.TWEAK));
        ConfigUtils.writeHotkeyToggleOptions(root, "DisableHotkeys", "DisableToggles", getConfigOptions(Config.Type.DISABLE));

        JsonUtils.writeJsonToFile(root, configFile);

        InternalSaver.save();
    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveToFile();
    }
}
