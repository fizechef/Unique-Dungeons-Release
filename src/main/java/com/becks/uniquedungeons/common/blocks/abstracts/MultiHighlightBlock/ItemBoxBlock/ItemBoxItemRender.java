package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public enum ItemBoxItemRender {
    HORIZONTAL_FLAT_ITEM(){
        @Override
        public double[] getRenderTranslation(ItemBox itemBox, ItemStack stack) {
            if (stack.getItem() instanceof ShieldItem){
                return new double[] { itemBox.getMin(Direction.Axis.X) + (itemBox.getLength(Direction.Axis.X)/2), itemBox.getMin(Direction.Axis.Y) + itemBox.getLength(Direction.Axis.Y), itemBox.getMin(Direction.Axis.Z)  + (itemBox.getLength(Direction.Axis.Z)/4)};
            }
            return new double[] { itemBox.getMin(Direction.Axis.X) + (itemBox.getLength(Direction.Axis.X)/2), itemBox.getMin(Direction.Axis.Y), itemBox.getMin(Direction.Axis.Z)  + (itemBox.getLength(Direction.Axis.Z)/4)};
        }

        @Override
        public float getRenderScale(ItemBox itemBox) {
            double shortestSide = Math.min(itemBox.getLength(Direction.Axis.X), itemBox.getLength(Direction.Axis.Z));
            return (float)(shortestSide * 2);
        }

        @Override
        public Quaternion getRenderRotation(ItemBox itembox) {
            return Vector3f.XP.rotationDegrees(90f);
        }
    },
    VERTICAL_FLAT_ITEM(){
        @Override
        public double[] getRenderTranslation(ItemBox itemBox, ItemStack stack) {
            return new double[] { itemBox.getMin(Direction.Axis.X) + (itemBox.getLength(Direction.Axis.X)/2), itemBox.getMin(Direction.Axis.Y)  + (itemBox.getLength(Direction.Axis.Y)/4), itemBox.getMin(Direction.Axis.Z)};
        }

        @Override
        public float getRenderScale(ItemBox itemBox) {
            double shortestSide = Math.min(itemBox.getLength(Direction.Axis.X), itemBox.getLength(Direction.Axis.Y));
            return (float)(shortestSide * 2);
        }

        @Override
        public Quaternion getRenderRotation(ItemBox itembox) {
            return Vector3f.XP.rotationDegrees(0f);
        }
    };

    public abstract double[] getRenderTranslation(ItemBox itemBox, ItemStack stack);

    public abstract float getRenderScale(ItemBox itemBox);

    public abstract Quaternion getRenderRotation(ItemBox itembox);
}
