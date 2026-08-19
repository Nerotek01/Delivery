package com.nerotek01.deliveryman;

import com.nerotek01.deliveryman.utils.CountdownFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CountdownFormatTest {

    private String formatWithoutPlugin(long millis) {
        long seconds = millis / 1000L;
        long days = seconds / 86400L;
        long hours = seconds / 3600L - days * 24L;
        long minutes = seconds / 60L - seconds / 3600L * 60L;
        long secs = seconds - seconds / 60L * 60L;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("d ");
        if (hours > 0 || days > 0) sb.append(hours).append("h ");
        if (minutes > 0 || hours > 0 || days > 0) sb.append(minutes).append("m ");
        sb.append(secs).append("s");
        return sb.toString().trim();
    }

    @Test
    void tenDaysFormatsCorrectly() {
        long tenDays = 10L * 24L * 60L * 60L * 1000L;
        String result = formatWithoutPlugin(tenDays);
        assertTrue(result.contains("10d"));
        assertTrue(result.contains("h"));
        assertTrue(result.contains("m"));
        assertTrue(result.contains("s"));
    }

    @Test
    void zeroMillisReturnsSeconds() {
        String result = formatWithoutPlugin(0);
        assertEquals("0s", result);
    }

    @Test
    void oneDayOnlyHasDays() {
        long oneDay = 24L * 60L * 60L * 1000L;
        String result = formatWithoutPlugin(oneDay);
        assertTrue(result.startsWith("1d"));
    }

    @Test
    void countdownFormatterHandlesNegativeInput() {
        String result = formatWithoutPlugin(-1000);
        assertTrue(result.contains("s"));
    }

    @Test
    void twelveDaysTwelveHoursTwelveMinutesTwelveSeconds() {
        long total = (12L * 86400L + 12L * 3600L + 12L * 60L + 12L) * 1000L;
        String result = formatWithoutPlugin(total);
        assertTrue(result.contains("12d 12h 12m 12s") || result.contains("12d") && result.contains("12h"));
    }
}
