package com.nerotek01.deliveryman.utils;

import com.nerotek01.deliveryman.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadLocalRandom;

public class ItemBurstEffect {

    private static final Map<UUID, ConcurrentLinkedDeque<Runnable>> queues = new ConcurrentHashMap<>();
    private static final Set<UUID> active = ConcurrentHashMap.newKeySet();

    public static void play(Main plugin, Player player) {
        UUID uuid = player.getUniqueId();
        if (active.contains(uuid)) {
            queues.computeIfAbsent(uuid, k -> new ConcurrentLinkedDeque<>())
                    .add(() -> startAnimation(plugin, player));
            return;
        }
        startAnimation(plugin, player);
    }

    private static void startAnimation(Main plugin, Player player) {
        UUID uuid = player.getUniqueId();
        active.add(uuid);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Item> items = new ArrayList<>();

        int count = 16 + random.nextInt(5);
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

            if (random.nextInt(10) < 3) {
                ItemMeta meta = stack.getItemMeta();
                if (meta != null) {
                    meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    stack.setItemMeta(meta);
                }
            }

            Location spawnLoc = player.getEyeLocation().add(
                    (random.nextDouble() - 0.5) * 1.5,
                    0.5 + random.nextDouble(),
                    (random.nextDouble() - 0.5) * 1.5
            );
            Item item = spawnLoc.getWorld().dropItem(spawnLoc, stack);
            item.setPickupDelay(32767);
            items.add(item);
        }

        new BukkitRunnable() {
            int ticks = 0;
            final int totalTicks = 100;

            @Override
            public void run() {
                if (ticks >= totalTicks || !player.isOnline()) {
                    for (Item item : items) {
                        if (item.isValid()) item.remove();
                    }
                    active.remove(uuid);
                    ConcurrentLinkedDeque<Runnable> queue = queues.remove(uuid);
                    if (player.isOnline() && queue != null) {
                        Runnable next = queue.poll();
                        if (next != null) {
                            next.run();
                        }
                    }
                    cancel();
                    return;
                }

                Location playerLoc = player.getLocation();
                double baseY = playerLoc.getY() + 2.0;
                double angle = ticks * 0.35;

                for (int i = 0; i < items.size(); i++) {
                    Item item = items.get(i);
                    if (!item.isValid()) continue;

                    double itemAngle = angle + (i * (2 * Math.PI / items.size()));
                    double radius = 1.5 + Math.sin(ticks * 0.1 + i) * 0.4;
                    double y = baseY + Math.sin(ticks * 0.2 + i * 0.5) * 0.6;

                    double x = playerLoc.getX() + radius * Math.cos(itemAngle);
                    double z = playerLoc.getZ() + radius * Math.sin(itemAngle);

                    Location target = new Location(playerLoc.getWorld(), x, y, z);
                    item.teleport(target);
                    item.setVelocity(new Vector(0, 0, 0));
                }
                ticks++;
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }

    public static void cleanupPlayer(UUID uuid) {
        active.remove(uuid);
        ConcurrentLinkedDeque<Runnable> q = queues.remove(uuid);
        if (q != null) q.clear();
    }
}
