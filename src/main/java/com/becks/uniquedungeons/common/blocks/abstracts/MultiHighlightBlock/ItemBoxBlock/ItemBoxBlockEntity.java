package com.becks.uniquedungeons.common.blocks.abstracts.MultiHighlightBlock.ItemBoxBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class ItemBoxBlockEntity<C extends IItemHandlerModifiable & INBTSerializable<CompoundTag>> extends BlockEntity implements Container{

    public static final String LOOT_TABLE_TAG = "LootTable";
    public static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";

    @Nullable
    protected ResourceLocation lootTable;
    protected long lootTableSeed;
    public static InventoryFactory<ItemStackHandler> defaultInventory(int slots)
    {
        return self -> new ItemStackHandler(slots);
    }

    protected C inventory;
    public ItemBoxBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState state, InventoryFactory<C> inventoryFactory) {
        super(p_155228_, p_155229_, state);
        this.inventory = inventoryFactory.create(this);
    }

    public Item getItemType(int slot) {
        if (slot > inventory.getSlots()){
            return null;
        }
        return inventory.getStackInSlot(slot).getItem();
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot > inventory.getSlots()){
            return null;
        }
        return inventory.getStackInSlot(slot);
    }

    public ItemStack removeItem(int slot) {
        this.setChanged();
        if (slot > inventory.getSlots()){
            return null;
        }
        return inventory.extractItem(slot, 1, false);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.setChanged();
        if (slot <= inventory.getSlots() && inventory.getStackInSlot(slot).getItem().equals(Items.AIR)){
            stack.setCount(1);
            inventory.insertItem(slot, stack,false);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        //System.out.println("Saving ItemBoxBlockEntity");
        tag.put("inventory", inventory.serializeNBT());
        trySaveLootTable(tag);
    }



    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        //System.out.println("Loading ItemBoxBlockEntity");
        inventory.deserializeNBT(tag.getCompound("inventory"));
        tryLoadLootTable(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet)
    {
        if (packet.getTag() != null)
        {
            handleUpdateTag(packet.getTag());
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag()
    {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", inventory.serializeNBT());
        trySaveLootTable(tag);
        return tag;
    }

    @FunctionalInterface
    public interface InventoryFactory<C extends IItemHandlerModifiable & INBTSerializable<CompoundTag>>
    {
        C create(ItemBoxBlockEntity<C> entity);
    }

    protected boolean tryLoadLootTable(CompoundTag pTag) {
        if (pTag.contains("LootTable", 8)) {
            this.lootTable = new ResourceLocation(pTag.getString("LootTable"));
            this.lootTableSeed = pTag.getLong("LootTableSeed");
            this.unpackLootTable();
            return true;
        } else {
            return false;
        }
    }

    protected boolean trySaveLootTable(CompoundTag pTag) {
        if (this.lootTable == null) {
            return false;
        } else {
            pTag.putString("LootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                pTag.putLong("LootTableSeed", this.lootTableSeed);
            }

            return true;
        }
    }

    public void unpackLootTable() {
        //System.out.println("Unpacking Loottable");
        if (this.lootTable == null){
            //System.out.println("No Loottable");
            return;
        }
        if (this.level != null && this.level.isClientSide){
            //System.out.println("Client Level");
            return;
        }
        MinecraftServer server = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
        if (server == null) {
            //System.out.println("No Server");
            return;
        }
        //System.out.println("Loottable present");
        LootTable loottable = server.getLootTables().get(this.lootTable);

        this.lootTable = null;
        LootContext.Builder lootcontext$builder = (new LootContext.Builder(server.overworld())).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition)).withOptionalRandomSeed(this.lootTableSeed);
        loottable.fill(this, lootcontext$builder.create(LootContextParamSets.CHEST));
        this.setChanged();
    }

    @Override
    public void onLoad() {
        //System.out.println("Loaded in " + this.level);
        super.onLoad();
    }



    @Override
    public int getContainerSize() {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < inventory.getSlots(); i++){
            if (!this.getItem(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return this.removeItem(pSlot);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return this.removeItem(pSlot);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < inventory.getSlots(); i++){
            setItem(i, ItemStack.EMPTY);
        }
    }
}
