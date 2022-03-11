package me.ivan1f.tweakerplus.mixins.tweaks.limitWorldModification;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void stopAttackingBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (TweakerPlusConfigs.LIMIT_WORLD_MODIFICATION.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void stopInteractingBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (TweakerPlusConfigs.LIMIT_WORLD_MODIFICATION.getBooleanValue()) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method = "interactEntity", at = @At("HEAD"), cancellable = true)
    private void stopInteractingEntity(PlayerEntity player, Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (TweakerPlusConfigs.LIMIT_WORLD_MODIFICATION.getBooleanValue()) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    private void stopInteractingItem(PlayerEntity player, World world, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (TweakerPlusConfigs.LIMIT_WORLD_MODIFICATION.getBooleanValue()) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void stopAttackingEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (TweakerPlusConfigs.LIMIT_WORLD_MODIFICATION.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    private void stopBreakingBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (TweakerPlusConfigs.LIMIT_WORLD_MODIFICATION.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "method_2902", at = @At("HEAD"), cancellable = true)
    private void resetBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (TweakerPlusConfigs.LIMIT_WORLD_MODIFICATION.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }
}
