package me.ivan1f.tweakerplus.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.config.options.*;
import me.ivan1f.tweakerplus.gui.TweakerPlusConfigGui;
import net.minecraft.client.MinecraftClient;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static me.ivan1f.tweakerplus.config.ConfigFactory.newConfigBoolean;
import static me.ivan1f.tweakerplus.config.ConfigFactory.newConfigDouble;
import static me.ivan1f.tweakerplus.util.ModIds.*;

public class TweakerPlusConfigs {
    /**
     * ============================
     * Config Declarations
     * ============================
     */

    ////////////////////
    //    Features    //
    ////////////////////

    @Config(type = Config.Type.GENERIC, category = Config.Category.FEATURES)
    public static final TweakerPlusConfigBooleanHotkeyed LIMIT_WORLD_MODIFICATION = ConfigFactory.newConfigBooleanHotkeyed("limitWorldModification", false, "");

    @Config(type = Config.Type.GENERIC, category = Config.Category.FEATURES)
    public static final TweakerPlusConfigBooleanHotkeyed IMMEDIATELY_RESPAWN = ConfigFactory.newConfigBooleanHotkeyed("immediatelyRespawn", false, "");

    @Config(type = Config.Type.GENERIC, category = Config.Category.FEATURES)
    public static final TweakerPlusConfigBoolean RESOURCE_PACK_INCOMPATIBLE_IGNORED = newConfigBoolean("resourcePackIncompatibleIgnored", false);

    ////////////////////
    //    MC Tweaks   //
    ////////////////////

    // Generic

    @Config(type = Config.Type.GENERIC, category = Config.Category.MC_TWEAKS)
    public static final TweakerPlusConfigDouble PLAYER_LIST_SCALE = newConfigDouble("playerListScale", 1, 0.001, 2);

    @Config(type = Config.Type.DISABLE, category = Config.Category.MC_TWEAKS)
    public static final TweakerPlusConfigBooleanHotkeyed DISABLE_PUMPKIN_OVERLAY = ConfigFactory.newConfigBooleanHotkeyed("disablePumpkinOverlay", false, "");

    @Config(type = Config.Type.DISABLE, category = Config.Category.MC_TWEAKS)
    public static final TweakerPlusConfigBooleanHotkeyed DISABLE_PORTAL_OVERLAY = ConfigFactory.newConfigBooleanHotkeyed("disablePortalOverlay", false, "");

    @Config(type = Config.Type.DISABLE, category = Config.Category.MC_TWEAKS)
    public static final TweakerPlusConfigBooleanHotkeyed DISABLE_WITHER_SPAWN_SOUND = ConfigFactory.newConfigBooleanHotkeyed("disableWitherSpawnSound", false, "");

    // List

    // Tweak

    ////////////////////
    //   Mod Tweaks   //
    ////////////////////

    @Config(type = Config.Type.GENERIC, category = Config.Category.MOD_TWEAKS, restriction = @Restriction(require = @Condition(xaero_minimap)))
    public static final TweakerPlusConfigBoolean XMAP_NO_DEATH_WAYPOINT_FOR_CREATIVE = ConfigFactory.newConfigBoolean("xmapNoDeathWaypointForCreative", false);

    @Config(type = Config.Type.GENERIC, category = Config.Category.MOD_TWEAKS)
    public static final TweakerPlusConfigBoolean LEFT_ALIGN_TITLE_GLOBALLY = ConfigFactory.newConfigBoolean("leftAlignTitleGlobally", false);

    @Config(type = Config.Type.GENERIC, category = Config.Category.MOD_TWEAKS, restriction = @Restriction(require = @Condition(litematica)))
    public static final TweakerPlusConfigBoolean BUNDLE_ORIGIN_IN_SCHEMATIC = ConfigFactory.newConfigBoolean("bundleOriginInSchematic", false);

    //////////////////////////
    //  TweakerPlus Setting //
    //////////////////////////

    @Config(type = Config.Type.GENERIC, category = Config.Category.SETTING)
    public static final TweakerPlusConfigBoolean HIDE_DISABLE_OPTIONS = ConfigFactory.newConfigBoolean("hideDisabledOptions", false);

    @Config(type = Config.Type.HOTKEY, category = Config.Category.SETTING)
    public static final TweakerPlusConfigHotkey OPEN_TWEAKERPLUS_CONFIG_GUI = ConfigFactory.newConfigHotKey("openTweakerPlusConfigGui", "P,C");

    @Config(type = Config.Type.TWEAK, category = Config.Category.SETTING)
    public static final TweakerPlusConfigBooleanHotkeyed TWEAKERPLUS_DEBUG_MODE = ConfigFactory.newConfigBooleanHotkeyed("tweakerPlusDebugMode");

    @Config(type = Config.Type.GENERIC, category = Config.Category.SETTING, debug = true)
    public static final TweakerPlusConfigInteger TWEAKERPLUS_DEBUG_INT = ConfigFactory.newConfigInteger("tweakerPlusDebugInt", 0, -1000, 1000);

    @Config(type = Config.Type.GENERIC, category = Config.Category.SETTING, debug = true)
    public static final TweakerPlusConfigInteger TWEAKERPLUS_DEBUG_DOUBLE = ConfigFactory.newConfigInteger("tweakerPlusDebugDouble", 0, -1, 1);

    /**
     * ============================
     * Implementation Details
     * ============================
     */

    public static void initCallbacks() {
        // common callbacks
        IValueChangeCallback<ConfigBoolean> redrawConfigGui = newValue -> TweakerPlusConfigGui.getCurrentInstance().ifPresent(TweakerPlusConfigGui::reDraw);

        // hotkeys
        OPEN_TWEAKERPLUS_CONFIG_GUI.getKeybind().setCallback(TweakerPlusConfigGui::onOpenGuiHotkey);

        // value listeners
        HIDE_DISABLE_OPTIONS.setValueChangeCallback(redrawConfigGui);
        IMMEDIATELY_RESPAWN.setValueChangeCallback(newValue -> {
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.setShowsDeathScreen(!newValue.getBooleanValue());
            }
        });

        // debugs
        TWEAKERPLUS_DEBUG_MODE.setValueChangeCallback(redrawConfigGui);
    }

    private static final List<TweakerPlusOption> OPTIONS = Lists.newArrayList();
    private static final Map<Config.Category, List<TweakerPlusOption>> CATEGORY_TO_OPTION = Maps.newLinkedHashMap();
    private static final Map<Config.Type, List<TweakerPlusOption>> TYPE_TO_OPTION = Maps.newLinkedHashMap();
    private static final Map<IConfigBase, TweakerPlusOption> CONFIG_TO_OPTION = Maps.newLinkedHashMap();

    static {
        for (Field field : TweakerPlusConfigs.class.getDeclaredFields()) {
            Config annotation = field.getAnnotation(Config.class);
            if (annotation != null) {
                try {
                    Object config = field.get(null);
                    if (!(config instanceof TweakerPlusIConfigBase)) {
                        TweakerPlusMod.LOGGER.warn("[TweakerPlus] {} is not a subclass of TweakerPlusIConfigBase", config);
                        continue;
                    }
                    TweakerPlusOption tweakerPlusOption = new TweakerPlusOption(annotation, (TweakerPlusIConfigBase) config);
                    OPTIONS.add(tweakerPlusOption);
                    CATEGORY_TO_OPTION.computeIfAbsent(tweakerPlusOption.getCategory(), k -> Lists.newArrayList()).add(tweakerPlusOption);
                    TYPE_TO_OPTION.computeIfAbsent(tweakerPlusOption.getType(), k -> Lists.newArrayList()).add(tweakerPlusOption);
                    CONFIG_TO_OPTION.put(tweakerPlusOption.getConfig(), tweakerPlusOption);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<TweakerPlusOption> getOptions(Config.Category categoryType) {
        if (categoryType == Config.Category.ALL) {
            return OPTIONS;
        }
        return CATEGORY_TO_OPTION.getOrDefault(categoryType, Collections.emptyList());
    }

    public static List<TweakerPlusOption> getOptions(Config.Type optionType) {
        return TYPE_TO_OPTION.getOrDefault(optionType, Collections.emptyList());
    }

    public static Stream<TweakerPlusIConfigBase> getAllConfigOptionStream() {
        return OPTIONS.stream().map(TweakerPlusOption::getConfig);
    }

    public static Optional<TweakerPlusOption> getOptionFromConfig(IConfigBase iConfigBase) {
        return Optional.ofNullable(CONFIG_TO_OPTION.get(iConfigBase));
    }

    public static boolean hasConfig(IConfigBase iConfigBase) {
        return getOptionFromConfig(iConfigBase).isPresent();
    }
}
