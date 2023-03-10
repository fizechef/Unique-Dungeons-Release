package com.becks.uniquedungeons.common.artifact_unlock;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;

public class ArtifactUnlockServer{

    private static final ArtifactUnlockServer instance = new ArtifactUnlockServer();

    private static Timer timer;
    private static TimerTask saveTask;

    private final Map<UUID, PlayerUnlockData> playerSaveQueue = new HashMap<>();
    private final Map<UUID, PlayerUnlockData> awaitingSaveQueue = new HashMap<>();
    private boolean inSave = false, skipTick = false;

    private ArtifactUnlockServer() {}

    public static ArtifactUnlockServer getInstance() {
        return instance;
    }

    public void onServerStart() {
        reset();

        saveTask = new TimerTask() {
            @Override
            public void run() {
                instance.doSave();
            }
        };
        timer = new Timer("ResearchIOThread", true);
        timer.scheduleAtFixedRate(saveTask, 30_000, 30_000);
    }

    public void onServerStop() {
       //System.out.println("Flush Save");
        this.flushAndSaveAll();

        reset();
    }

    public static ArtifactUnlockServer stop(Runnable onStop) {
        return wrap(null, onStop);
    }

    public static ArtifactUnlockServer start(Runnable onStart) {
        return wrap(onStart, null);
    }

    public static ArtifactUnlockServer wrap(@Nullable Runnable onStart, @Nullable Runnable onStop) {
        return new ArtifactUnlockServer() {
            @Override
            public void onServerStart() {
                if (onStart != null) {
                    onStart.run();
                }
            }

            @Override
            public void onServerStop() {
                if (onStop != null) {
                    onStop.run();
                }
            }
        };
    }

    private void reset() {
        if (saveTask != null) {
            saveTask.cancel();
            saveTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void doSave() {
       //System.out.println("Save");
        if (skipTick) {
            return;
        }

        inSave = true;
        for (Map.Entry<UUID, PlayerUnlockData> entry : playerSaveQueue.entrySet()) {
            saveNow(entry.getKey(), entry.getValue());
        }
        playerSaveQueue.clear();
        inSave = false;

        playerSaveQueue.putAll(awaitingSaveQueue);
        awaitingSaveQueue.clear();
    }

    private void scheduleSave(UUID playerUUID, PlayerUnlockData copiedProgress) {
        if (inSave) {
            awaitingSaveQueue.put(playerUUID, copiedProgress);
        } else {
            playerSaveQueue.put(playerUUID, copiedProgress);
        }
    }

    private void cancelScheduledSave(UUID playerUUID) {
        awaitingSaveQueue.remove(playerUUID);
        playerSaveQueue.remove(playerUUID);
    }

    private void flushAndSaveAll() {
        skipTick = true;
        playerSaveQueue.putAll(awaitingSaveQueue);
        for (Map.Entry<UUID, PlayerUnlockData> entry : playerSaveQueue.entrySet()) {
            saveNow(entry.getKey(), entry.getValue());
        }
        playerSaveQueue.clear();
        awaitingSaveQueue.clear();
        skipTick = false;
        inSave = false;
    }

    public static void saveProgress(UUID playerUUID, PlayerUnlockData copiedProgress) {
        if (instance != null) {
            instance.scheduleSave(playerUUID, copiedProgress);
        }
    }

    public static void cancelSave(UUID playerUUID) {
        if (instance != null) {
            instance.cancelScheduledSave(playerUUID);
        }
    }

    static void saveNow(UUID playerUUID, PlayerUnlockData progress) {
        File playerFile = UnlockUtils.getPlayerFile(playerUUID);
       //System.out.println("Saving now to: ");
       //System.out.println(playerFile);
        try {
            CompoundTag nbt = new CompoundTag();
            progress.saveToNBT(nbt);
            NbtIo.write(nbt, playerFile);
        } catch (IOException ignored) {}
    }
}
