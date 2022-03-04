package me.ivan1f.tweakerplus.impl.bundleOriginInSchematic;

import net.minecraft.util.math.BlockPos;

public interface ILitematicaSchematic {
    boolean hasOrigin();
    void setOrigin(BlockPos origin);
    BlockPos getOrigin();
}
