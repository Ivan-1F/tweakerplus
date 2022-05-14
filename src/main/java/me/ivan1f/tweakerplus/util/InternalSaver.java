package me.ivan1f.tweakerplus.util;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade.RecipeStorage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class InternalSaver {
    private static final Map<String, JsonSavable> INTERNAL_DATA_SAVERS = new ImmutableMap.Builder<String, JsonSavable>()
            .put("autoTrade", RecipeStorage.getInstance())
            .build();

    private static File getSaveFile() {
        return new File(new File(FileUtils.getMinecraftDirectory(), TweakerPlusMod.MOD_ID), "internal.json");
    }

    public static void load() {
        JsonObject root = null;
        File file = getSaveFile();
        if (FileUtil.ensureFileReadable(file)) {
            JsonElement element = JsonUtils.parseJsonFile(file);
            if (element != null && element.isJsonObject()) {
                root = element.getAsJsonObject();
            }
        }
        if (root != null) {
            loadInternal(root);
        }
    }

    public static void save() {
        JsonObject root = new JsonObject();
        saveInternal(root);
        FileUtil.writeJsonToFileCreate(root, getSaveFile());
    }

    private static void loadInternal(JsonObject jsonObject) {
        INTERNAL_DATA_SAVERS.forEach((name, jsonSaveAble) -> {
            JsonObject object = JsonUtils.getNestedObject(jsonObject, name, false);
            if (object != null) {
                jsonSaveAble.loadFromJsonSafe(object);
            }
        });
    }

    private static void saveInternal(JsonObject jsonObject) {
        INTERNAL_DATA_SAVERS.forEach((name, jsonSaveAble) -> jsonObject.add(name, jsonSaveAble.dumpToJson()));
    }
}
