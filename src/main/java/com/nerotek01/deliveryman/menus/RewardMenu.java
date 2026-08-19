package com.nerotek01.deliveryman.menus;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.rewards.Reward;
import com.nerotek01.deliveryman.utils.CountdownFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RewardMenu {
    public static final int CLOSE_SLOT = 40;
    public static final int INFO_SLOT = 44;

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
        if (playerData == null) {
            playerData = plugin.getDm().getOrCreatePlayerData(player);
        }
        final PlayerData finalPd = playerData;
        plugin.getRm().getRewards().values().forEach(reward ->
                inventory.setItem(reward.getSlot(), createRewardIcon(player, finalPd, reward)));

        inventory.setItem(CLOSE_SLOT, createCloseButton());
        inventory.setItem(INFO_SLOT, createInfoBook());

        player.openInventory(inventory);
        activeViews.add(player.getUniqueId());
    }

    public void updateRewardMenu() {
        updateActiveMenus();
    }

    public void updateActiveMenus() {
        for (UUID playerId : activeViews) {
            Player player = Bukkit.getPlayer(playerId);
            if (player == null || !player.isOnline()) {
                activeViews.remove(playerId);
                continue;
            }
            Inventory openInventory;
            try {
                openInventory = player.getOpenInventory().getTopInventory();
            } catch (Exception ignored) {
                activeViews.remove(playerId);
                continue;
            }
            if (openInventory == null || openInventory.getSize() == 0) {
                activeViews.remove(playerId);
                continue;
            }
            String expected = plugin.getLang().get("menus.rewards.title");
            String current = null;
            try {
                current = player.getOpenInventory().getTitle();
            } catch (Throwable ignored) {
            }
            if (expected == null || current == null || !current.equals(expected)) {
                activeViews.remove(playerId);
                continue;
            }
            updateInventory(player, openInventory);
        }
    }

    private void updateInventory(Player player, Inventory inventory) {
        if (!player.isOnline()) return;
        PlayerData playerData = plugin.getDm().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        plugin.getRm().getRewards().values().forEach(reward ->
                inventory.setItem(reward.getSlot(), createRewardIcon(player, playerData, reward)));
        inventory.setItem(CLOSE_SLOT, createCloseButton());
        inventory.setItem(INFO_SLOT, createInfoBook());
    }

    private ItemStack createRewardIcon(Player player, PlayerData playerData, Reward reward) {
        if (playerData.hasClaimed(reward.getId())) {
            long claimedTime = playerData.getClaimTime(reward.getId());
            long cooldownEnd = claimedTime + reward.getUnit().toMillis(reward.getCountdown());
            long remaining = cooldownEnd - System.currentTimeMillis();
            if (remaining <= 0) {
                playerData.resetClaim(reward.getId());
                return reward.getIcon(player, false, "");
            }
            return reward.getIcon(player, true, CountdownFormatter.format(plugin, remaining));
        }
        return reward.getIcon(player, false, "");
    }

    private ItemStack createCloseButton() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("\u00a7cClose");
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createInfoBook() {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("\u00a76\u00a7lMenu Guide");
            List<String> lore = Arrays.asList(
                    "\u00a77",
                    "\u00a77Welcome to the Mystery Dust",
                    "\u00a77Delivery menu!",
                    "\u00a77",
                    "\u00a77Claim your daily and rank-based",
                    "\u00a77rewards here. Each reward has",
                    "\u00a77a cooldown shown in its lore.",
                    "\u00a77",
                    "\u00a7eClick an available reward to",
                    "\u00a7eclaim it instantly!"
            );
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void add(Player player) {
        activeViews.add(player.getUniqueId());
    }

    public void remove(Player player) {
        activeViews.remove(player.getUniqueId());
    }

    public boolean isViewing(Player player) {
        return activeViews.contains(player.getUniqueId());
    }
}
