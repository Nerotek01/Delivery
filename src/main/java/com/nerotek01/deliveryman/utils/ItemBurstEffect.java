package com.nerotek01.deliveryman.utils;

import com.nerotek01.deliveryman.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ItemBurstEffect {

    public ItemBurstEffect(Main plugin, Player player) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Item> items = new ArrayList<>();
        Location headLoc = player.getEyeLocation();

        int count = 14 + random.nextInt(7);
        for (int i = 0; i < count; i++) {
            Material mat;
            int pick = random.nextInt(3);
            if (pick == 0) {
                mat = Material.EMERALD;
            } else if (pick == 1) {
                mat = Material.DIAMOND;
            } else {
                mat = random.nextBoolean() ? Material.EMERALD : Material.DIAMOND;
            }

            ItemStack stack = new ItemStack(mat, 1);
            Item item = headLoc.getWorld().dropItem(headLoc, stack);
            item.setPickupDelay(32767);
            item.setVelocity(new Vector(
                    (random.nextDouble() - 0.5) * 0.9,
                    random.nextDouble() * 0.6 + 0.3,
                    (random.nextDouble() - 0.5) * 0.9
            ));
            items.add(item);
        }

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 100 || !player.isOnline()) {
                    for (Item item : items) {
                        if (item.isValid()) item.remove();
                    }
                    cancel();
                    return;
                }
                Location head = player.getEyeLocation();
                for (Item item : items) {
                    if (!item.isValid()) continue;
                    Location itemLoc = item.getLocation();
                    double dx = head.getX() - itemLoc.getX();
                    double dy = head.getY() - itemLoc.getY();
                    double dz = head.getZ() - itemLoc.getZ();
                    double horizDist = Math.sqrt(dx * dx + dz * dz);

                    if (horizDist > 3.5 || dy < -2.5 || dy > 3.5) {
                        Location target = head.clone().add(
                                (random.nextDouble() - 0.5) * 2.0,
                                (random.nextDouble() - 0.5) * 0.8,
                                (random.nextDouble() - 0.5) * 2.0
                        );
                        item.teleport(target);
                    } else {
                        Vector pull = new Vector(dx, dy, dz);
                        double len = pull.length();
                        if (len > 0.001) {
                            pull.multiply(1.0 / len).multiply(0.04);
                        }
                        Vector gravity = new Vector(0, 0.04, 0);
                        Vector jitter = new Vector(
                                (random.nextDouble() - 0.5) * 0.05,
                                (random.nextDouble() - 0.5) * 0.05,
                                (random.nextDouble() - 0.5) * 0.05
                        );
                        Vector vel = item.getVelocity().multiply(0.92).add(pull).add(gravity).add(jitter);
                        item.setVelocity(vel);
                    }
                }
                ticks++;
            }
        }.runTaskTimer(plugin, 5L, 1L);
    }
}
