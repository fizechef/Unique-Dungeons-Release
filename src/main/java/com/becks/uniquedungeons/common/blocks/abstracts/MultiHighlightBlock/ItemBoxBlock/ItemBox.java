package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock;

import com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.SelectionBox;
import com.mojang.math.Quaternion;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ItemBox implements SelectionBox {
    private final VoxelShape shape;
    private final AABB aabb;

    private final int itemSlotNumber;

    private final ItemBoxItemRender render;

    private final ItemBoxPuttable puttable;


    public ItemBox(VoxelShape shape, AABB aabb, int itemSlotNumber, ItemBoxItemRender render, ItemBoxPuttable puttable) {
        this.shape = shape;
        this.aabb = aabb;
        this.itemSlotNumber = itemSlotNumber;
        this.render = render;
        this.puttable = puttable;
    }

    public ItemBox(VoxelShape shape, int itemSlotNumber, ItemBoxItemRender render, ItemBoxPuttable puttable) {
        this.shape = shape;
        this.aabb = shape.bounds();
        this.itemSlotNumber = itemSlotNumber;
        this.render = render;
        this.puttable = puttable;
    }

    @Override
    public double getLength(Direction.Axis a){
        return shape.max(a) - shape.min(a);
    }

    @Override
    public double getMin(Direction.Axis a){
        return shape.min(a);
    }

    @Override
    public double getMax(Direction.Axis a){
        return shape.max(a);
    }

    public int getSlot(){
        return itemSlotNumber;
    }

    @Override
    public VoxelShape getShape(){
        return shape;
    }

    @Override
    public AABB getAabb(){
        return aabb;
    }

    public double[] getRenderTranslation(ItemStack stack){
        return this.render.getRenderTranslation(this, stack);
    }

    public float getRenderScale(){
        return this.render.getRenderScale(this);
    }

    public Quaternion getRenderRotation(){
        return render.getRenderRotation(this);
    };

    public boolean puttable(Item item){
        return puttable.puttable(item);
    }
}
