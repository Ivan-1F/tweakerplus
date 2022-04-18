package me.ivan1f.tweakerplus.impl.bundleOriginInSchematic;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;

public class BundleOriginInSchematicHelper {
    public static boolean moveToOriginChecked = true;

    public static boolean shouldMoveToOrigin() {
        return TweakerPlusConfigs.BUNDLE_ORIGIN_IN_SCHEMATIC.getBooleanValue() && moveToOriginChecked;
    }
}
