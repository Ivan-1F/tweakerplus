package me.ivan1f.tweakerplus.mixins.tweaks.xmapNoDeathWaypointForCreative;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.util.ModIds;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointsManager;

@Restriction(require = @Condition(ModIds.xaero_worldmap))
@Mixin(value = WaypointsManager.class, remap = false)
public class WaypointsManagerMixin {
    @Inject(method = "createDeathpoint(Lnet/minecraft/entity/player/PlayerEntity;Lxaero/common/minimap/waypoints/WaypointWorld;Z)V", at = @At("HEAD"), cancellable = true)
    private void stopCreatingDeathPoint(PlayerEntity player, WaypointWorld world, boolean temp, CallbackInfo ci) {
        if (player.isCreative() && TweakerPlusConfigs.XMAP_NO_DEATH_WAYPOINT_FOR_CREATIVE.getBooleanValue()) {
            ci.cancel();
        }
    }
}
