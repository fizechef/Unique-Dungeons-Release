package com.becks.uniquedungeons.common.stackCooldowns;

import com.becks.uniquedungeons.network.Network;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ServerStackCooldowns extends StackCooldowns {
    private final ServerPlayer player;

    public ServerStackCooldowns(ServerPlayer pPlayer) {
        this.player = pPlayer;
    }

    @Override
    protected void onCooldownStarted(ItemStack stack, int pTicks, boolean syncadd) {
       //System.out.println("Started Cooldowm Serverside");
        if (!syncadd){
            ClientboundStackCooldownPacket pkt = new ClientboundStackCooldownPacket(stack, pTicks);
            Network.sendToPlayer(pkt, player);
        }
        super.onCooldownStarted(stack, pTicks, syncadd);
    }

    @Override
    protected void onCooldownEnded(ItemStack stack, boolean syncremove) {
       //System.out.println("Ending Cooldown Serverside");
        if (!syncremove){
            ClientboundStackCooldownPacket pkt = new ClientboundStackCooldownPacket(stack, 0);
            Network.sendToPlayer(pkt, player);
        }
        ((StackCooldownItem)stack.getItem()).onCooldownEnd(player, stack);
        super.onCooldownEnded(stack, syncremove);
    }
}
