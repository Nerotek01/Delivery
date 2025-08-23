package com.nerotek01.deliveryman.menus;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.rewards.Reward;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RewardMenu {
    private final Main plugin;
    private final Set<UUID> activeViews = ConcurrentHashMap.newKeySet();

    public RewardMenu(Main plugin) {
        this.plugin = plugin;
    }

    public void createRewardMenu(Player player) {
        int rows = plugin.getCm().getRewardsRows();
        String title = plugin.getLang().get("menus.rewards.title");
        Inventory inventory = Bukkit.createInventory(null, rows * 9, title);

        PlayerData playerData = plugin.getDm().getPlayerData(player);
        plugin.getRm().getRewards().values().forEach(reward -> {
            inventory.setItem(reward.getSlot(), createRewardIcon(player, playerData, reward));
        });

        player.openInventory(inventory);
        activeViews.add(player.getUniqueId());
    }

    public void updateActiveMenus() {
        Iterator<UUID> iterator = activeViews.iterator();
        while (iterator.hasNext()) {
            UUID playerId = iterator.next();
            Player player = Bukkit.getPlayer(playerId);

            if (player == null || !player.isOnline()) {
                iterator.remove();
                continue;
            }

            Inventory openInventory = player.getOpenInventory().getTopInventory();
            if (openInventory == null) {
                iterator.remove();
                continue;
            }

            updateInventory(player, openInventory);
        }
    }

    private void updateInventory(Player player, Inventory inventory) {
        PlayerData playerData = plugin.getDm().getPlayerData(player);
        plugin.getRm().getRewards().values().forEach(reward -> {
            inventory.setItem(reward.getSlot(), createRewardIcon(player, playerData, reward));
        });
    }

    private ItemStack createRewardIcon(Player player, PlayerData playerData, Reward reward) {
        if (playerData.getClaimed().containsKey(reward.getId())) {
            long claimedTime = playerData.getClaimed().get(reward.getId());
            long cooldownEnd = claimedTime + reward.getUnit().toMillis(reward.getCountdown());
            long remaining = cooldownEnd - System.currentTimeMillis();

            if (remaining <= 0) {
                playerData.getClaimed().remove(reward.getId());
                return reward.getIcon(player, false, "");
            }
            return reward.getIcon(player, true, formatCountdown(remaining));
        }
        return reward.getIcon(player, false, "");
    }

    private String formatCountdown(long millis) {
        long seconds = millis / 1000;
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (days * 24);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60;
        long secs = seconds - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

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

        return timeFormat
                .replace("<days>", String.valueOf(days))
                .replace("<hours>", String.valueOf(hours))
                .replace("<minutes>", String.valueOf(minutes))
                .replace("<seconds>", String.valueOf(secs));
    }

    public void removeViewer(Player player) {
        activeViews.remove(player.getUniqueId());
    }
}