package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBox;
import com.becks.uniquedungeons.client.render.BoxHighlightRender;
import com.becks.uniquedungeons.util.DirectionHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.Collection;

public abstract class MultiHighlightBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected MultiHighlightBlock(Properties p_49224_) {
        super(p_49224_);
        this.defaultBlockState().setValue(FACING, Direction.NORTH);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public abstract Collection<SelectionBox> getBoxes();

    public abstract BlockEntityType<? extends BlockEntity> getBlockEntityType();

    public boolean drawHighlight(Level level, BlockPos pos, Player player, BlockHitResult rayTrace, PoseStack matrixStack, MultiBufferSource buffers, Vec3 renderPos)
    {
        SelectionBox selection = getPlayerSelection(getBoxes(), getBlockEntityType(), level, pos, player, rayTrace);
        if (selection != null)
        {
            boolean puttable = (selection instanceof ItemBox) && ((ItemBox) selection).puttable(player.getItemInHand(InteractionHand.MAIN_HAND).getItem());
            BoxHighlightRender.drawBox(matrixStack, Shapes.create(DirectionHelper.rotateAABBblockCenterRelated(selection.getShape().bounds(), DirectionHelper.getRotation(level.getBlockState(pos).getValue(FACING), Direction.SOUTH))), buffers, pos, renderPos, puttable?0:1 , puttable?1:0, 0.0F, 0.4F);
            return true;
        }
        return false;
    }

    protected static SelectionBox getPlayerSelection(Collection<SelectionBox> boxes, BlockEntityType<? extends BlockEntity> blockEntityType, BlockGetter level, BlockPos pos, Player player, BlockHitResult result)
    {
        return (SelectionBox) level.getBlockEntity(pos, blockEntityType)
                .map(block -> {
                    final Vec3 hit = result.getLocation();
                    for (SelectionBox b : boxes){
                        AABB selectionAABB = (DirectionHelper.rotateAABBblockCenterRelated(b.getAabb(), DirectionHelper.getRotation(level.getBlockState(pos).getValue(FACING), Direction.SOUTH))).move(pos);
                       //System.out.println(selectionAABB + "  " + hit);
                        if (selectionAABB.contains(hit))
                        {
                            return b;
                        }
                    }

                    return null;
                })
                .orElse(null);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return null;
    }
    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,
                (context.getNearestLookingDirection().equals(Direction.UP) || context.getNearestLookingDirection().equals(Direction.DOWN))
                ?
                Direction.NORTH
                :
                context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
}
