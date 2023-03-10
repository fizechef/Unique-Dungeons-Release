package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.LockingItemBoxBlock;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBox;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxBlock;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock.ItemBoxBlockEntity;
import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.SelectionBox;
import com.becks.uniquedungeons.common.blocks.lockPedestal.LockPedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;

public abstract class LockingItemBoxBlock extends ItemBoxBlock {

    public static final BooleanProperty LOCKED = BlockStateProperties.POWERED;

    protected LockingItemBoxBlock(Properties p_49224_) {
        super(p_49224_);
    }

    public abstract SelectionBox getLockItemBox();

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        InteractionResult superResult = null;
        if (pHand.equals(InteractionHand.MAIN_HAND)){
           //System.out.println("with main hand");
            Item interactionItem = pPlayer.getMainHandItem().getItem();
            ArrayList box = new ArrayList<SelectionBox>();
            box.add(this.getLockItemBox());
            LockItemBox selection = (LockItemBox) getPlayerSelection(box , this.getBlockEntityType(), pLevel, pPos, pPlayer, pHit);
            if (selection != null){
               //System.out.println("on lockitembox " + selection.getSlot());
                ItemBoxBlockEntity bEntity = (ItemBoxBlockEntity) pLevel.getBlockEntity(pPos);
                if (selection.puttable(interactionItem)){
                   //System.out.println("with matching");
                    if (bEntity.getItemType(selection.getSlot()) == null || bEntity.getItemType(selection.getSlot()).equals(Items.AIR)){
                       //System.out.println("that is empty");
                        bEntity.setItem(selection.getSlot(), pPlayer.getMainHandItem().copy());
                        pLevel.sendBlockUpdated(pPos, pState, pState, 3);
                        superResult = InteractionResult.CONSUME;
                    }
                }
            }
        }
        if (superResult == null){
            superResult = super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        if (locked(pLevel, pPos) != pLevel.getBlockState(pPos).getValue(LOCKED)){
           //System.out.println("Changing lock to " + locked(pLevel, pPos));
            pLevel.setBlockAndUpdate(pPos, pLevel.getBlockState(pPos).setValue(LOCKED, locked(pLevel, pPos)));
        }
        return superResult;
    }

    private boolean locked(Level pLevel, BlockPos pPos){
        ItemBoxBlockEntity bEntity = (ItemBoxBlockEntity) pLevel.getBlockEntity(pPos);
        ItemStack lockItem = bEntity.getItem(((LockItemBox)this.getLockItemBox()).getSlot());
        if (lockItem != null && !lockItem.getItem().equals(Items.AIR)){
            ItemStack compareItem = bEntity.getItem(((LockItemBox)this.getLockItemBox()).getLockBox().getSlot());
            if (compareItem != null && !compareItem.getItem().equals(Items.AIR)){
                ItemStack compareItemNoDamage = compareItem.copy();
                compareItemNoDamage.setDamageValue(0);
                compareItemNoDamage.setCount(1);
                ItemStack lockItemNoDamage = lockItem.copy();
                lockItemNoDamage.setDamageValue(0);
                lockItemNoDamage.setCount(1);
                return ItemStack.matches(compareItemNoDamage, lockItemNoDamage);
            }
        }
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LOCKED);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(LOCKED, false);
    }

    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        return pState.getValue(LOCKED)?15:0;
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof LockPedestalBlockEntity) {
            //System.out.println(((ItemBoxBlockEntity)blockentity).getItem(((ItemBox)this.getLockItemBox()).getSlot()));
            return (((ItemBoxBlockEntity)blockentity).getItemType(((ItemBox)this.getLockItemBox()).getSlot()).equals(Items.AIR) || ((ItemBoxBlockEntity)blockentity).getItemType(((ItemBox)this.getLockItemBox()).getSlot()) == null)?0:(pBlockState.getValue(LOCKED)?7:14);
        }
        return 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }
}
