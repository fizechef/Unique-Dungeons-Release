package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface SelectionBox {
    VoxelShape getShape();
    AABB getAabb();

    double getLength(Direction.Axis a);
    double getMin(Direction.Axis a);
    double getMax(Direction.Axis a);
}
