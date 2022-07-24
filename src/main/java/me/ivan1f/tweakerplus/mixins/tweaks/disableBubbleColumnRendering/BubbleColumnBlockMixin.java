package me.ivan1f.tweakerplus.mixins.tweaks.disableBubbleColumnRendering;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin {
    @Inject(method = "randomDisplayTick", at = @At("HEAD"), cancellable = true)
    private void disableBubbleColumnRendering(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        if (TweakerPlusConfigs.DISABLE_BUBBLE_COLUMN_RENDERING.getBooleanValue()) {
            ci.cancel();
        }
    }
}
