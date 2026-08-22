package com.nerotek01.deliveryman.listeners;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.menus.RewardMenu;
import com.nerotek01.deliveryman.rewards.Reward;
import com.nerotek01.deliveryman.utils.CountdownFormatter;
import com.nerotek01.deliveryman.utils.ItemBurstEffect;
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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class MenuListener implements Listener {
    private static final String NBT_KEY = "ULTRADM";
    private static final String NBT_FIELD = "ID";
    private static final Pattern SAFE_NAME = Pattern.compile("[^A-Za-z0-9_]");
    private static final long CLICK_COOLDOWN_MS = 500L;
    private static final long SPAM_MESSAGE_COOLDOWN_MS = 2000L;
    private static final String SPAM_MESSAGE = "\u00a7cYou are clicking too fast! Please slow down.";

    private final Main plugin;
    private final Map<UUID, Long> lastClickTime = new ConcurrentHashMap<>();
    private final Map<UUID, Long> lastSpamMessageTime = new ConcurrentHashMap<>();

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
        if (!(e.getPlayer() instanceof Player)) return;
        String viewTitle;
        try {
            viewTitle = e.getView().getTitle();
        } catch (Throwable ignored) {
            return;
        }
        if (!isRewardMenu(viewTitle, e.getInventory())) return;
        plugin.getRem().add((Player) e.getPlayer());
    }

    @EventHandler
    public void onMenu(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Inventory topInventory;
        String viewTitle;
        try {
            topInventory = e.getView().getTopInventory();
            viewTitle = e.getView().getTitle();
        } catch (Throwable ignored) {
            return;
        }
        if (topInventory == null || !isRewardMenu(viewTitle, topInventory)) return;

        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();

        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        int slot = e.getRawSlot();
        if (slot == RewardMenu.CLOSE_SLOT) {
            p.closeInventory();
            return;
        }
        if (slot == RewardMenu.INFO_SLOT) {
            return;
        }

        if (isClickCooldownActive(p)) {
            sendSpamMessage(p);
            return;
        }

        ItemStack item = e.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;

        String id = NBTEditor.getString(item, NBT_KEY, NBT_FIELD);
        if (id == null) return;

        Reward reward = plugin.getRm().getRewards().get(id);
        if (reward == null) return;

        PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd == null) {
            pd = plugin.getDm().getOrCreatePlayerData(p);
        }
        handleRewardClick(p, pd, reward);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        lastClickTime.remove(uuid);
        lastSpamMessageTime.remove(uuid);
        ItemBurstEffect.cleanupPlayer(uuid);
    }

    private boolean isClickCooldownActive(Player p) {
        UUID uuid = p.getUniqueId();
        long now = System.currentTimeMillis();
        Long last = lastClickTime.get(uuid);
        if (last != null && (now - last) < CLICK_COOLDOWN_MS) {
            return true;
        }
        lastClickTime.put(uuid, now);
        return false;
    }

    private void sendSpamMessage(Player p) {
        UUID uuid = p.getUniqueId();
        long now = System.currentTimeMillis();
        Long lastMsg = lastSpamMessageTime.get(uuid);
        if (lastMsg == null || (now - lastMsg) >= SPAM_MESSAGE_COOLDOWN_MS) {
            p.sendMessage(SPAM_MESSAGE);
            lastSpamMessageTime.put(uuid, now);
        }
    }

    private boolean isRewardMenu(String viewTitle, Inventory inventory) {
        if (inventory == null || viewTitle == null) return false;
        String expectedTitle = plugin.getLang().get("menus.rewards.title");
        if (expectedTitle == null || !expectedTitle.equals(viewTitle)) return false;
        int expectedSize = plugin.getCm().getRewardsRows() * 9;
        return inventory.getSize() == expectedSize;
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
            String countdownStr = CountdownFormatter.format(plugin, remaining);
            String message = reward.getClaimed().getMessage();
            if (message != null) {
                message = message
                        .replace("<cooldown>", countdownStr)
                        .replace("<reward>", reward.getClaimed().getName());
            }
            sendMessages(p, message);
            reward.getClaimed().playSound(p);
        } else {
            claimReward(p, pd, reward);
        }
    }

    private void claimReward(Player p, PlayerData pd, Reward reward) {
        ItemBurstEffect.play(plugin, p);

        String message = reward.getNoClaimed().getMessage();
        if (message != null) {
            message = message
                    .replace("<reward>", reward.getNoClaimed().getName())
                    .replace("<status>", "\u00a7e");
        }
        sendMessages(p, message);
        pd.claim(reward.getId(), System.currentTimeMillis());
        executeCommands(p, reward.getRewards());
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
        String safeName = sanitizeName(p.getName());
        for (String cmd : commands) {
            if (cmd == null || cmd.isEmpty()) continue;
            String resolved = cmd.replace("<player>", safeName);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), resolved);
        }
    }

    private String sanitizeName(String name) {
        if (name == null) return "";
        String cleaned = SAFE_NAME.matcher(name).replaceAll("");
        return cleaned.isEmpty() ? name : cleaned;
    }
}
