package me.ivan1f.tweakerplus.mixins.tweaks.disableBubbleColumnRendering;

import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// don't do the "net.minecraft.util.math.random.Random <-> net.minecraft.world.gen.random.AbstractRandom" remap thing
//#disable-remap

//#if MC >= 11900
//$$ import net.minecraft.util.math.random.Random;
//#else
import java.util.Random;
//#endif

@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin {
    @Inject(method = "randomDisplayTick", at = @At("HEAD"), cancellable = true)
    private void disableBubbleColumnRendering(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        if (TweakerPlusConfigs.DISABLE_BUBBLE_COLUMN_RENDERING.getBooleanValue()) {
            ci.cancel();
        }
    }
}

//#enable-remap
