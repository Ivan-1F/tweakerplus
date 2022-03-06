package me.ivan1f.tweakerplus.mixins.tweaks.playerListScale;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.RenderUtil;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    @Nullable
    private RenderUtil.Scaler scaler = null;

    @Inject(method = "render", at = @At("HEAD"))
    private void tweakerPlus_playerListScale_push(MatrixStack matrices, int width, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci) {
        this.scaler = null;
        if (TweakerPlusConfigs.PLAYER_LIST_SCALE.isModified()) {
            this.scaler = RenderUtil.createScaler(width / 2, 0, TweakerPlusConfigs.PLAYER_LIST_SCALE.getDoubleValue());
            this.scaler.apply(matrices);
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void tweakerPlus_playerListScale_pop(MatrixStack matrices, int width, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci) {
        if (this.scaler != null) {
            this.scaler.restore();
        }
    }
}
