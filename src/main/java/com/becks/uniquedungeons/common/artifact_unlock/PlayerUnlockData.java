package com.becks.uniquedungeons.common.artifact_unlock;

import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItem;
import com.becks.uniquedungeons.core.init.itemInit.ArtifactInit;
import com.becks.uniquedungeons.network.packets.PaketSyncUnlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class PlayerUnlockData {
    private final List<ArtifactItem> knownArtifacts = new ArrayList<>();

    public void saveToNBT(CompoundTag nbt) {

        CompoundTag artifactTag = new CompoundTag();
        for (int i = 0; i < knownArtifacts.size(); i++) {
            ArtifactItem artifact = knownArtifacts.get(i);
            artifactTag.putString("artifact" + i, knownArtifacts.get(i).toString());
        }
        artifactTag.putInt("size", knownArtifacts.size());
        nbt.put("knownArtifacts", artifactTag);
    }

    public void loadFromNBT(CompoundTag nbt) {
        CompoundTag artifactTag = nbt.getCompound("knownArtifacts");
        knownArtifacts.clear();
        for (int i = 0; i < artifactTag.getInt("size"); i++) {
            String compoundString = artifactTag.getString("artifact" + i);
            //System.out.println(ArtifactInit.lookUp(compoundString));
            ArtifactItem artifact = ArtifactInit.lookUp(compoundString);
            knownArtifacts.add(artifact);
        }
    }

    public boolean unlock(ArtifactItem artifact){
        if (!knownArtifacts.contains(artifact)){
            knownArtifacts.add(artifact);
        }
        return true;
    }

    public boolean knows(ArtifactItem artifact){
        for (ArtifactItem t : knownArtifacts){
            if (t.equals(artifact)){
                return true;
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    protected void sync(PaketSyncUnlock paket) {
        this.loadFromNBT(paket.getTag());
    }
}
