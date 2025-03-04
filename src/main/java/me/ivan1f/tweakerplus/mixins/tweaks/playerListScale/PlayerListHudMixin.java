package me.ivan1f.tweakerplus.mixins.tweaks.playerListScale;

import me.fallenbreath.tweakermore.util.render.RenderUtils;
import me.fallenbreath.tweakermore.util.render.context.RenderContext;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 12000
//$$ import net.minecraft.client.gui.DrawContext;
//#elseif MC >= 11600
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    @Nullable
    private RenderUtils.Scaler scaler = null;

    @Inject(method = "render", at = @At("HEAD"))
    private void tweakerPlus_playerListScale_push(
            //#if MC >= 12000
            //$$ DrawContext matrixStackOrDrawContext,
            //#elseif MC >= 11600
            //$$ MatrixStack matrixStackOrDrawContext,
            //#endif
            int width, Scoreboard scoreboard, ScoreboardObjective playerListScoreboardObjective, CallbackInfo ci
    ) {
        this.scaler = null;
        if (TweakerPlusConfigs.PLAYER_LIST_SCALE.isModified()) {
            this.scaler = RenderUtils.createScaler(width / 2, 0, TweakerPlusConfigs.PLAYER_LIST_SCALE.getDoubleValue());
            this.scaler.apply(RenderContext.of(
                    //#if MC >= 11600
                    //$$ matrixStackOrDrawContext
                    //#endif
            ));
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void tweakerPlus_playerListScale_pop(
            //#if MC >= 12000
            //$$ DrawContext matrixStackOrDrawContext,
            //#elseif MC >= 11600
            //$$ MatrixStack matrixStackOrDrawContext,
            //#endif
            int width, Scoreboard scoreboard, ScoreboardObjective playerListScoreboardObjective, CallbackInfo ci
    ) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
