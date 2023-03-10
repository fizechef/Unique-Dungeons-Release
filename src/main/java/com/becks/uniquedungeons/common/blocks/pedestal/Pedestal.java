package com.becks.uniquedungeons.common.blocks.pedestal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBox;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxBlock;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxItemRender;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxPuttable;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.SelectionBox;
import com.becks.uniquedungeons.core.init.BlockEntityInit;
import com.becks.uniquedungeons.util.DirectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;


public class Pedestal extends ItemBoxBlock {
	private final VoxelShape SHAPE  = Stream.of(
			Block.box(2, 0, 2, 14, 2, 14),
			Block.box(3, 2, 3, 13, 4, 13),
			Block.box(4, 4, 4, 12, 11, 12),
			Block.box(3, 11, 3, 13, 13, 13),
			Block.box(2.5, 13, 4, 13.5, 13.5, 12),
			Block.box(13, 8, 4, 13.5, 13, 12),
			Block.box(2.5, 8, 4, 3, 13, 12)
			).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();

	private final VoxelShape SHAPE_1  = Stream.of(
			Block.box(2, 0, 2, 14, 2, 14),
			Block.box(3, 2, 3, 13, 4, 13),
			Block.box(4, 4, 4, 12, 11, 12),
			Block.box(3, 11, 3, 13, 13, 13),
			Block.box(4, 13, 2.5, 12, 13.5, 13.5),
			Block.box(4, 8, 13, 12, 13, 13.5),
			Block.box(4, 8, 2.5, 12, 13, 3)
	).reduce((v1, v2) -> {return Shapes.join(v1, v2, OR);}).get();

	
	
	public Pedestal(Properties properties) {
		super(properties);
	}



	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PedestalBlockEntity(pos, state);
	}

	@Override
	public Collection<SelectionBox> getBoxes() {
		return getItemBoxes();
	}

	@Override
	public BlockEntityType<? extends BlockEntity> getBlockEntityType() {
		return BlockEntityInit.PEDESTAL.get();
	}

	@Override
	public Collection<SelectionBox> getItemBoxes() {
		List l = new ArrayList();
		l.add(new ItemBox(Shapes.or(Block.box(4.5D, 13.49D, 4.5D, 11.5, 14.5, 11.5)), 0, ItemBoxItemRender.HORIZONTAL_FLAT_ITEM, ItemBoxPuttable.NO_BLOCK_ITEM));
		return l;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {

		if (state.getValue(FACING).equals(Direction.WEST)){
			return SHAPE_1;
		} if (state.getValue(FACING).equals(Direction.EAST)){
			return SHAPE_1;
		} if (state.getValue(FACING).equals(Direction.SOUTH)){
			return SHAPE;
		}
			return SHAPE;
		}
}
