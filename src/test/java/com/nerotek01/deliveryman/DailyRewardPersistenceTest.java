package com.nerotek01.deliveryman;

import com.google.gson.Gson;
import com.nerotek01.deliveryman.data.PlayerData;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DailyRewardPersistenceTest {

    private final Gson gson = new Gson();

    @Test
    void gsonRoundTripPreservesClaimTimestamp() {
        UUID uuid = UUID.randomUUID();
        PlayerData pd = new PlayerData(uuid);
        long claimTime = System.currentTimeMillis();
        pd.claim("daily", claimTime);

        String json = gson.toJson(pd);
        PlayerData loaded = gson.fromJson(json, PlayerData.class);

        assertTrue(loaded.hasClaimed("daily"));
        assertEquals(claimTime, loaded.getClaimTime("daily"));
    }

    @Test
    void claimSurvivesSimulatedRejoin() {
        UUID uuid = UUID.randomUUID();
        PlayerData sessionOne = new PlayerData(uuid);
        long claimTime = System.currentTimeMillis();
        sessionOne.claim("daily", claimTime);

        String storedJson = gson.toJson(sessionOne);

        PlayerData sessionTwo = gson.fromJson(storedJson, PlayerData.class);

        long countdown = 1;
        TimeUnit unit = TimeUnit.DAYS;
        long cooldownEnd = sessionTwo.getClaimTime("daily") + unit.toMillis(countdown);
        long remaining = cooldownEnd - System.currentTimeMillis();

        assertTrue(remaining > 0, "Cooldown must still be active right after rejoin, but remaining=" + remaining);
        assertTrue(sessionTwo.hasClaimed("daily"));
    }

    @Test
    void freshPlayerDataHasNoClaims() {
        PlayerData pd = new PlayerData(UUID.randomUUID());
        assertFalse(pd.hasClaimed("daily"));
        assertEquals(0L, pd.getClaimTime("daily"));
    }

    @Test
    void mapValueTypesSurviveRoundTrip() {
        PlayerData pd = new PlayerData(UUID.randomUUID());
        pd.claim("daily", 1700000000123L);
        pd.setStreak("daily", 3);

        PlayerData loaded = gson.fromJson(gson.toJson(pd), PlayerData.class);

        Object claimValue = null;
        for (var entry : loaded.getClaimed().entrySet()) {
            if (entry.getKey().equals("daily")) claimValue = entry.getValue();
        }
        assertTrue(claimValue instanceof Long, "Claim value must deserialize as Long but was "
                + (claimValue == null ? "null" : claimValue.getClass().getName()));
        assertEquals(1700000000123L, loaded.getClaimTime("daily"));
        assertEquals(3, loaded.getStreakCount("daily"));
    }

    @Test
    void corruptedJsonThrowsAndRequiresExplicitFallback() {
        assertThrows(Exception.class, () -> gson.fromJson("{not valid json", PlayerData.class));
    }

    @Test
    void nullLiteralJsonDeserializesToNull() {
        assertNull(gson.fromJson("null", PlayerData.class));
    }

    @Test
    void multipleRewardsSurviveRoundTrip() {
        PlayerData pd = new PlayerData(UUID.randomUUID());
        long now = System.currentTimeMillis();
        pd.claim("daily", now);
        pd.claim("vip", now - 5000L);
        pd.claim("mvp++", now + 1234L);

        PlayerData loaded = gson.fromJson(gson.toJson(pd), PlayerData.class);

        assertEquals(now, loaded.getClaimTime("daily"));
        assertEquals(now - 5000L, loaded.getClaimTime("vip"));
        assertEquals(now + 1234L, loaded.getClaimTime("mvp++"));
    }
}
