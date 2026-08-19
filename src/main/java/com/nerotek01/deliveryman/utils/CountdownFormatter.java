package com.nerotek01.deliveryman.utils;

import com.nerotek01.deliveryman.Main;

import java.util.concurrent.TimeUnit;

public final class CountdownFormatter {

    private CountdownFormatter() {
    }

    public static String format(Main plugin, long millis) {
        if (millis < 0) millis = 0;
        long seconds = millis / 1000L;
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = seconds / 3600L - days * 24L;
        long minutes = seconds / 60L - seconds / 3600L * 60L;
        long secs = seconds - seconds / 60L * 60L;

        String timeFormat;
        if (days > 0) {
            timeFormat = plugin.getLang().get("countdown.days");
        } else if (hours > 0) {
            timeFormat = plugin.getLang().get("countdown.hours");
        } else if (minutes > 0) {
            timeFormat = plugin.getLang().get("countdown.minutes");
        } else {
            timeFormat = plugin.getLang().get("countdown.seconds");
        }
        if (timeFormat == null) timeFormat = "<days>d <hours>h <minutes>m <seconds>s";

        return timeFormat
                .replace("<days>", String.valueOf(days))
                .replace("<hours>", String.valueOf(hours))
                .replace("<minutes>", String.valueOf(minutes))
                .replace("<seconds>", String.valueOf(secs));
    }
}
