package me.ivan1f.tweakerplus.mixins.tweaks.bundleOriginInSchematic;

import fi.dy.masa.litematica.gui.GuiSchematicLoad;
import fi.dy.masa.litematica.gui.Icons;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.widgets.WidgetCheckBox;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.BundleOriginInSchematicHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiSchematicLoad.class)
public class GuiSchematicLoadMixin extends GuiBase {
    @Inject(method = "initGui", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void addMoveToOriginCheckBox(CallbackInfo ci) {
        if (!TweakerPlusConfigs.BUNDLE_ORIGIN_IN_SCHEMATIC.getBooleanValue()) return;
        String label = StringUtils.translate("tweakerplus.gui.label.schematic_load.checkbox.move_to_origin");
        String hover = StringUtils.translate("tweakerplus.gui.label.schematic_load.hoverinfo.move_to_origin");

        int x = 12 + this.getStringWidth(StringUtils.translate("litematica.gui.label.schematic_load.checkbox.create_placement")) + 20;
        int y = this.height - 40;

        WidgetCheckBox checkbox = new WidgetCheckBox(x, y, Icons.CHECKBOX_UNSELECTED, Icons.CHECKBOX_SELECTED, label, hover);
        checkbox.setListener(widgetCheckBox -> {
            assert widgetCheckBox != null;
            BundleOriginInSchematicHelper.moveToOriginChecked = widgetCheckBox.isChecked();
        });
        checkbox.setChecked(BundleOriginInSchematicHelper.moveToOriginChecked, false);
        this.addWidget(checkbox);
    }
}