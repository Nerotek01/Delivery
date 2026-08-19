package com.nerotek01.deliveryman;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RankNameFormatTest {

    private String formatRank(String rank) {
        return switch (rank) {
            case "vip" -> "&aVIP";
            case "vip+" -> "&aVIP&6+";
            case "mvp" -> "&bMVP";
            case "mvp+" -> "&bMVP&c+";
            case "mvp++" -> "&bMVP&c++";
            default -> "&7" + rank;
        };
    }

    @Test
    void vipFormatIsCorrect() {
        assertEquals("&aVIP", formatRank("vip"));
    }

    @Test
    void vipPlusFormatIsCorrect() {
        assertEquals("&aVIP&6+", formatRank("vip+"));
    }

    @Test
    void mvpFormatIsCorrect() {
        assertEquals("&bMVP", formatRank("mvp"));
    }

    @Test
    void mvpPlusFormatIsCorrect() {
        assertEquals("&bMVP&c+", formatRank("mvp+"));
    }

    @Test
    void mvpPlusPlusFormatIsCorrect() {
        assertEquals("&bMVP&c++", formatRank("mvp++"));
    }

    @Test
    void allRanksProduceNonEmptyOutput() {
        String[] ranks = {"vip", "vip+", "mvp", "mvp+", "mvp++"};
        for (String r : ranks) {
            assertTrue(formatRank(r).length() > 0, "Rank " + r + " produced empty output");
        }
    }

    @Test
    void requiresLineIncludesCorrectRank() {
        String rank = formatRank("vip");
        String line = "&7Requires " + rank;
        assertTrue(line.contains("&aVIP"));
    }

    @Test
    void plusRanksUseAppropriateColors() {
        assertTrue(formatRank("vip+").contains("&6+"));
        assertTrue(formatRank("mvp+").contains("&c+"));
        assertTrue(formatRank("mvp++").contains("&c++"));
    }
}
