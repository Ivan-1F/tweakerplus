package me.ivan1f.tweakerplus.mixins.tweaks.bundleOriginInSchematic;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.litematica.selection.AreaSelection;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.impl.bundleOriginInSchematic.ILitematicaSchematic;
import me.ivan1f.tweakerplus.util.ModIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "fi.dy.masa.litematica.gui.GuiSchematicSave$ButtonListener")
@Restriction(require = @Condition(ModIds.litematica))
public class GuiSchematicSaveButtonListenerMixin {
    @Redirect(
            method = "actionPerformedWithButton",
            at = @At(value = "INVOKE", target = "Lfi/dy/masa/litematica/schematic/LitematicaSchematic;createEmptySchematic(Lfi/dy/masa/litematica/selection/AreaSelection;Ljava/lang/String;)Lfi/dy/masa/litematica/schematic/LitematicaSchematic;"),
            remap = false
    )
    private LitematicaSchematic appendOriginOnSave(AreaSelection area, String author) {
        LitematicaSchematic vanilla = LitematicaSchematic.createEmptySchematic(area, author);
        if (TweakerPlusConfigs.BUNDLE_ORIGIN_IN_SCHEMATIC.getBooleanValue()) {
            ((ILitematicaSchematic) vanilla).setOrigin(area.getEffectiveOrigin());
        }
        return vanilla;
    }
}
