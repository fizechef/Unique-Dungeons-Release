package com.becks.uniquedungeons.network.packets;

import com.becks.uniquedungeons.UniqueDungeons;
import com.becks.uniquedungeons.common.artifact_unlock.UnlockUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PaketSyncUnlock {
    private final CompoundTag tag;

    //Decoder
    public PaketSyncUnlock(FriendlyByteBuf buf) {
        tag = buf.readNbt();
    }

    //Encoder
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    public PaketSyncUnlock(CompoundTag tag) {
        this.tag = tag;
    }

    public CompoundTag getTag(){
        return tag;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            UnlockUtils.updateClientResearch(this);
        });
        ctx.get().setPacketHandled(true);
    }
}
