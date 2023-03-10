package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.MultiHighlightBlock;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.SelectionBox;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collection;

public abstract class ItemBoxBlock extends MultiHighlightBlock {

    protected ItemBoxBlock(Properties p_49224_) {
        super(p_49224_);
    }

    public abstract Collection<SelectionBox> getItemBoxes();

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        //System.out.println("interacting");
        if (pHand.equals(InteractionHand.MAIN_HAND)){
            //System.out.println("with main hand");
            Item interactionItem = pPlayer.getMainHandItem().getItem();
            ItemBox selection = (ItemBox) getPlayerSelection(this.getItemBoxes() , this.getBlockEntityType(), pLevel, pPos, pPlayer, pHit);
            //AABB selectionAABB = (DirectionHelper.rotateAABBblockCenterRelated(selection.getAabb(), DirectionHelper.getRotation(Direction.SOUTH, pState.getValue(FACING)))).move(pPos);
            if (selection != null){
                //System.out.println("on itembox " + selection.getSlot());
                ItemBoxBlockEntity bEntity = (ItemBoxBlockEntity) pLevel.getBlockEntity(pPos);
                if (interactionItem.equals(Items.AIR)){
                    //System.out.println("with empty hand");
                    if (bEntity.getItemType(selection.getSlot()) != null && !(bEntity.getItemType(selection.getSlot()).equals(Items.AIR))){
                        //System.out.println("that is full");
                        pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY(), pPos.getZ() + 0.5, bEntity.removeItem(selection.getSlot())));
                        pLevel.sendBlockUpdated(pPos, pState, pState, 3);
                        return InteractionResult.CONSUME;
                    }
                }
                else if (selection.puttable(interactionItem)){
                    //System.out.println("with pilz");
                    if (bEntity.getItemType(selection.getSlot()) == null || bEntity.getItemType(selection.getSlot()).equals(Items.AIR)){
                        //System.out.println("that is empty");
                        bEntity.setItem(selection.getSlot(), pPlayer.getMainHandItem().split(1));
                        pLevel.sendBlockUpdated(pPos, pState, pState, 3);
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
}
