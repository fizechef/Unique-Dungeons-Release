package com.becks.uniquedungeons.common.artifact_unlock;

import com.becks.uniquedungeons.common.items.artifacts.abstracts.ArtifactItem;

public class ClientsidePLayerUnlockData extends PlayerUnlockData{
    @Override
    public boolean knows(ArtifactItem artifact) {
        return false;
    }
}
