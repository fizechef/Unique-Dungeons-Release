package com.becks.uniquedungeons.common.blocks;

import java.util.stream.Stream;
import com.becks.uniquedungeons.common.blocks.abstracts.AbstractWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.property.Properties;
import net.minecraft.world.phys.shapes.Shapes;


public class DarkAntiqueStonePillar extends AbstractWaterloggableBlock {
	
	private final VoxelShape SHAPE  = Block.box(2, 0, 2, 14, 16, 14);
				
	public DarkAntiqueStonePillar(Properties properties) {
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

}
