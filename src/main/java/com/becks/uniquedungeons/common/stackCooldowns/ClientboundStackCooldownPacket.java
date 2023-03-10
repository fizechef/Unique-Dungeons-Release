package com.becks.uniquedungeons.common.stackCooldowns;

import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundStackCooldownPacket {
    private final ItemStack stack;
    private final int duration;

    private boolean handled = false;

    public ClientboundStackCooldownPacket(ItemStack stack, int pDuration) {
        this.stack = stack;
        this.duration = pDuration;
    }

    public ClientboundStackCooldownPacket(FriendlyByteBuf pBuffer) {
        this.stack = pBuffer.readItem();
        this.duration = pBuffer.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void toBytes(FriendlyByteBuf pBuffer) {
        pBuffer.writeItemStack(this.stack, false);
        pBuffer.writeVarInt(this.duration);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(Supplier<NetworkEvent.Context> ctx) {
       //System.out.println("Handling " + this + " " + handled);
        ctx.get().enqueueWork(() -> {
            StackCooldownUtils.updateClientCooldowns(this);
        });
        handled = true;
        ctx.get().setPacketHandled(true);
    }

    public ItemStack getItem() {
        return this.stack;
    }

    public int getDuration() {
        return this.duration;
    }
}
