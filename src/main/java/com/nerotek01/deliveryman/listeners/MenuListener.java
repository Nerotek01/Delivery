package com.nerotek01.deliveryman.listeners;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.enums.RewardType;
import com.nerotek01.deliveryman.rewards.Reward;
import com.nerotek01.deliveryman.utils.InstantFirework;
import com.nerotek01.deliveryman.utils.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {
    private final Main plugin;

    public MenuListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            plugin.getRem().remove((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            String title = e.getView().getTitle();
            String expected = plugin.getLang().get("menus.rewards.title");
            if (expected != null && expected.equals(title)) {
                plugin.getRem().add((Player) e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onMenu(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        String title;
        try {
            title = e.getView().getTitle();
        } catch (Throwable ignored) {
            return;
        }
        String expected = plugin.getLang().get("menus.rewards.title");
        if (expected == null || !expected.equals(title)) return;

        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE
                || e.getCurrentItem() == null
                || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        String id = NBTEditor.getString(item, "ULTRADM", "ID");
        if (id == null) return;

        Reward reward = plugin.getRm().getRewards().get(id);
        if (reward == null) return;

        PlayerData pd = plugin.getDm().getPlayerData(p);
        handleRewardClick(p, pd, reward);
    }

    private void handleRewardClick(Player p, PlayerData pd, Reward reward) {
        if (!p.hasPermission(reward.getPermission())) {
            sendMessages(p, reward.getNoPermissionMessage());
            reward.playSound(p);
            return;
        }

        if (pd.hasClaimed(reward.getId())) {
            long claimedTime = pd.getClaimTime(reward.getId());
            long cooldownEnd = claimedTime + reward.getUnit().toMillis(reward.getCountdown());
            long remaining = cooldownEnd - System.currentTimeMillis();
            if (remaining <= 0) {
                pd.resetClaim(reward.getId());
                claimReward(p, pd, reward);
                return;
            }
            sendMessages(p, reward.getClaimed().getMessage());
            reward.getClaimed().playSound(p);
        } else {
            claimReward(p, pd, reward);
        }
    }

    private void claimReward(Player p, PlayerData pd, Reward reward) {
        if (reward.isFireworkExplode()) {
            new InstantFirework(p.getLocation().clone().add(0, 1, 0));
        }

        switch (reward.getType()) {
            case NORMAL, UNIQUE -> {
                String message = reward.getNoClaimed().getMessage()
                        .replace("<reward>", reward.getNoClaimed().getName())
                        .replace("<status>", "\u00a7e");
                sendMessages(p, message);
                pd.claim(reward.getId(), System.currentTimeMillis());
                executeCommands(p, reward.getRewards());
                p.closeInventory();
            }
            case MESSAGE, VOTE -> {
                sendMessages(p, reward.getMessage());
                p.closeInventory();
            }
        }

        if (reward.getType() == RewardType.VOTE) {
            plugin.getDm().setVoting(p.getName(), reward.getVoteSite());
        }

        reward.getNoClaimed().playSound(p);
    }

    private void sendMessages(Player p, String message) {
        if (message == null) return;
        for (String line : message.split("\\n")) {
            p.sendMessage(line.replace("&", "\u00a7"));
        }
    }

    private void executeCommands(Player p, Iterable<String> commands) {
        if (commands == null) return;
        String playerName = p.getName();
        for (String cmd : commands) {
            if (cmd == null || cmd.isEmpty()) continue;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("<player>", playerName));
        }
    }
}
