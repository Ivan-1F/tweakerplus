package me.ivan1f.tweakerplus.mixins.tweaks.xmapNoDeathWaypointForCreative;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.ModIds;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnresolvedMixinReference")
@Restriction(require = @Condition(ModIds.xaero_minimap))
@Pseudo
@Mixin(targets = "xaero.common.core.XaeroMinimapCore")
public class WaypointsManagerMixin {
    @Inject(method = "beforeRespawn", at = @At("HEAD"), cancellable = true)
    private static void stopCreatingDeathPoint(PlayerEntity player, CallbackInfo ci) {
        if (player.isCreative() && TweakerPlusConfigs.XMAP_NO_DEATH_WAYPOINT_FOR_CREATIVE.getBooleanValue()) {
            ci.cancel();
        }
    }
}
