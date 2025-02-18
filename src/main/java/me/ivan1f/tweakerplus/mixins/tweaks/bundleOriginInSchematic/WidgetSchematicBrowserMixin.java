package me.ivan1f.tweakerplus.mixins.tweaks.bundleOriginInSchematic;

import fi.dy.masa.litematica.gui.widgets.WidgetSchematicBrowser;
import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.malilib.gui.interfaces.IDirectoryCache;
import fi.dy.masa.malilib.gui.interfaces.IFileBrowserIconProvider;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetFileBrowserBase;
import fi.dy.masa.malilib.util.StringUtils;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.ILitematicaSchematic;
import me.ivan1f.tweakerplus.util.ModIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;

//#if MC < 11500
//$$ import net.minecraft.util.math.Vec3i;
//#endif

//#if MC >= 11600
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

@Mixin(WidgetSchematicBrowser.class)
@Restriction(require = @Condition(ModIds.litematica))
public abstract class WidgetSchematicBrowserMixin extends WidgetFileBrowserBase {
    public WidgetSchematicBrowserMixin(int x, int y, int width, int height, IDirectoryCache cache, String browserContext, File defaultDirectory, ISelectionListener<DirectoryEntry> selectionListener, IFileBrowserIconProvider iconProvider) {
        super(x, y, width, height, cache, browserContext, defaultDirectory, selectionListener, iconProvider);
    }

    @Inject(
            method = "drawSelectedSchematicInfo",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false
    )
    private void appendOriginInfo(
            DirectoryEntry entry,
            //#if MC >= 11600
            //$$ MatrixStack matrixStack,
            //#endif
            CallbackInfo ci, int x, int y
    ) {
        int textColor = -1061109568;
        if (!TweakerPlusConfigs.BUNDLE_ORIGIN_IN_SCHEMATIC.getBooleanValue()) return;
        LitematicaSchematic schematic = LitematicaSchematic.createFromFile(entry.getDirectory(), entry.getName());
        if (schematic != null && ((ILitematicaSchematic) schematic).hasOrigin()) {
            String msg = StringUtils.translate(
                    "tweakerplus.gui.label.schematic_info.origin",
                    //#if MC >= 11500
                    ((ILitematicaSchematic) schematic).getOrigin().toShortString()
                    //#else
                    //$$ toShortString(((ILitematicaSchematic) schematic).getOrigin())
                    //#endif
            );
            this.drawString(
                    //#if MC >= 11600
                    //$$ matrixStack,
                    //#endif
                    msg, x, y, textColor
            );
        }
    }

    //#if MC < 11500
    //$$ private String toShortString(Vec3i pos) {
    //$$     return "" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    //$$ }
    //#endif
}
