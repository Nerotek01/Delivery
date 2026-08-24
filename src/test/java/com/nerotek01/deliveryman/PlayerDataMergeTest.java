package com.nerotek01.deliveryman;

import com.google.gson.Gson;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.managers.DataManager;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerDataMergeTest {

    @Test
    void mergeIntoEmptyMapInstallsLoadedData() {
        DataManager dm = new DataManager();
        UUID uuid = UUID.randomUUID();
        PlayerData loaded = new PlayerData(uuid);
        long now = System.currentTimeMillis();
        loaded.claim("daily", now);

        dm.mergePlayer(uuid, loaded);

        assertSame(loaded, dm.getPlayerData(uuid));
        assertTrue(dm.getPlayerData(uuid).hasClaimed("daily"));
    }

    @Test
    void lateLoadDoesNotEraseNewerInMemoryClaim() {
        DataManager dm = new DataManager();
        UUID uuid = UUID.randomUUID();

        PlayerData temp = dm.getOrCreatePlayerData(uuid);
        long claimTime = System.currentTimeMillis();
        temp.claim("daily", claimTime);

        PlayerData staleLoad = new PlayerData(uuid);
        staleLoad.claim("daily", claimTime - 86400000L);

        dm.mergePlayer(uuid, staleLoad);

        assertEquals(claimTime, dm.getPlayerData(uuid).getClaimTime("daily"));
    }

    @Test
    void loadedClaimFillsMissingInMemoryKey() {
        DataManager dm = new DataManager();
        UUID uuid = UUID.randomUUID();

        PlayerData temp = dm.getOrCreatePlayerData(uuid);
        temp.claim("daily", System.currentTimeMillis());

        PlayerData loaded = new PlayerData(uuid);
        long vipTime = System.currentTimeMillis();
        loaded.claim("vip", vipTime);

        dm.mergePlayer(uuid, loaded);

        assertTrue(dm.getPlayerData(uuid).hasClaimed("vip"));
        assertEquals(vipTime, dm.getPlayerData(uuid).getClaimTime("vip"));
        assertTrue(dm.getPlayerData(uuid).hasClaimed("daily"));
    }

    @Test
    void loadedNewerClaimOverwritesOlderInMemoryClaim() {
        DataManager dm = new DataManager();
        UUID uuid = UUID.randomUUID();

        PlayerData temp = dm.getOrCreatePlayerData(uuid);
        long oldTime = System.currentTimeMillis() - 3600000L;
        temp.claim("daily", oldTime);

        PlayerData loaded = new PlayerData(uuid);
        long newTime = System.currentTimeMillis();
        loaded.claim("daily", newTime);

        dm.mergePlayer(uuid, loaded);

        assertEquals(newTime, dm.getPlayerData(uuid).getClaimTime("daily"));
    }

    @Test
    void removedClaimIsNotResurrectedByOlderLoadEntry() {
        DataManager dm = new DataManager();
        UUID uuid = UUID.randomUUID();

        PlayerData temp = dm.getOrCreatePlayerData(uuid);
        temp.claim("daily", System.currentTimeMillis());
        temp.resetClaim("daily");

        PlayerData loaded = new PlayerData(uuid);
        loaded.claim("daily", 0L);

        dm.mergePlayer(uuid, loaded);

        assertFalse(dm.getPlayerData(uuid).hasClaimed("daily"));
    }

    @Test
    void streaksMergeKeepsHighestValue() {
        DataManager dm = new DataManager();
        UUID uuid = UUID.randomUUID();

        PlayerData temp = dm.getOrCreatePlayerData(uuid);
        temp.setStreak("daily", 5);

        PlayerData loaded = new PlayerData(uuid);
        loaded.setStreak("daily", 9);
        loaded.setStreak("vip", 2);

        dm.mergePlayer(uuid, loaded);

        assertEquals(9, dm.getPlayerData(uuid).getStreakCount("daily"));
        assertEquals(2, dm.getPlayerData(uuid).getStreakCount("vip"));
    }

    @Test
    void removePlayerClearsEntry() {
        DataManager dm = new DataManager();
        UUID uuid = UUID.randomUUID();
        dm.getOrCreatePlayerData(uuid);

        dm.removePlayer(uuid);

        assertNull(dm.getPlayerData(uuid));
    }

    @Test
    void fullRejoinCycleEnforcesCooldown() {
        DataManager sessionOne = new DataManager();
        UUID uuid = UUID.randomUUID();
        Gson gson = new Gson();

        PlayerData pd = sessionOne.getOrCreatePlayerData(uuid);
        long claimTime = System.currentTimeMillis();
        pd.claim("daily", claimTime);

        String persisted = gson.toJson(pd);

        DataManager sessionTwo = new DataManager();
        PlayerData loaded = gson.fromJson(persisted, PlayerData.class);
        sessionTwo.mergePlayer(uuid, loaded);

        PlayerData reloaded = sessionTwo.getPlayerData(uuid);
        assertTrue(reloaded.hasClaimed("daily"));
        long cooldownEnd = reloaded.getClaimTime("daily") + TimeUnit.DAYS.toMillis(1);
        assertTrue(cooldownEnd > System.currentTimeMillis(),
                "Daily cooldown must still gate the reward after a full rejoin cycle");
    }
}
