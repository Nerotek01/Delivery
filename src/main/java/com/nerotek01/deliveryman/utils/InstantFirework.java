package com.nerotek01.deliveryman.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class InstantFirework {

    public InstantFirework(Location loc) {
        Firework firework = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect.Type type = FireworkEffect.Type.values()[random(0, FireworkEffect.Type.values().length)];
        FireworkEffect effect = FireworkEffect.builder()
                .with(type)
                .withColor(randomColor())
                .withFade(randomColor())
                .build();

        meta.addEffect(effect);
        firework.setFireworkMeta(meta);

        try {
            Object handle = firework.getClass().getMethod("getHandle").invoke(firework);
            Field field = handle.getClass().getDeclaredField("expectedLifespan");
            field.setAccessible(true);
            field.set(handle, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Color randomColor() {
        return Color.fromRGB(random(0, 255), random(0, 255), random(0, 255));
    }

    private int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
