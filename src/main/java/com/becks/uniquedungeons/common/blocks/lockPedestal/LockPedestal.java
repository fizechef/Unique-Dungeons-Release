package com.becks.uniquedungeons.common.blocks.lockPedestal;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.*;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.LockingItemBoxBlock.LockItemBox;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.LockingItemBoxBlock.LockingItemBoxBlock;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.SelectionBox;
import com.becks.uniquedungeons.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;


public class LockPedestal extends LockingItemBoxBlock {
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


	public LockPedestal(Properties properties) {
		super(properties);
	}

	@Override
	public SelectionBox getLockItemBox() {
		return new LockItemBox((ItemBox) this.getItemBoxes().toArray()[0],Shapes.or(Block.box(6D, 6D, 3.9D, 10, 10, 5)), 1, ItemBoxItemRender.VERTICAL_FLAT_ITEM, ItemBoxPuttable.NO_BLOCK_ITEM);
	}


	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new LockPedestalBlockEntity(pos, state);
	}

	@Override
	public Collection<SelectionBox> getBoxes() {
		Collection<SelectionBox> c = getItemBoxes();
		c.add(getLockItemBox());
		return c;
	}

	@Override
	public BlockEntityType<? extends BlockEntity> getBlockEntityType() {
		return BlockEntityInit.LOCK_PEDESTAL.get();
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
