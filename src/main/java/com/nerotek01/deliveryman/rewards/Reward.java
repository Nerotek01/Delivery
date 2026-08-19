package com.nerotek01.deliveryman.rewards;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.utils.NBTEditor;
import com.nerotek01.deliveryman.xseries.XSound;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Reward {

    private static final String NBT_KEY = "ULTRADM";
    private static final String NBT_FIELD = "ID";

    private final String id;
    private final String permission;
    private final int slot;
    private final RewardStatus noClaimed;
    private final RewardStatus claimed;
    private final List<String> rewards;
    private final float noPermissionVolume;
    private final float noPermissionPitch;
    private final Sound noPermissionSound;
    private final String noPermissionMessage;

    private int countdown = 999999;
    private TimeUnit unit = TimeUnit.DAYS;

    public Reward(Main plugin, String path) {
        this.id = plugin.getRewards().get(path + ".id");

        this.permission = plugin.getRewards().get(path + ".permission");
        this.slot = plugin.getRewards().getInt(path + ".slot");

        this.noPermissionSound = XSound.matchXSound(
                        plugin.getRewards().getOrDefault(path + ".noPermission.sound",
                                XSound.BLOCK_ANVIL_LAND.parseSound().name()))
                .orElse(XSound.BLOCK_ANVIL_LAND).parseSound();

        this.noPermissionVolume = Math.min(plugin.getRewards().getIntOrDefault(path + ".noPermission.volume", 1), 10);
        this.noPermissionPitch = Math.min(plugin.getRewards().getIntOrDefault(path + ".noPermission.pitch", 1), 10);
        this.noPermissionMessage = plugin.getRewards().getOrDefault(path + ".noPermission.message",
                "&cYou don't have permission for this.");

        if (plugin.getRewards().isSet(path + ".countdown")) {
            int parsedCountdown = plugin.getRewards().getInt(path + ".countdown");
            if (parsedCountdown <= 0) {
                plugin.getLogger().warning("Reward '" + path + "' has countdown <= 0 - rewards will be instantly re-claimable.");
            }
            this.countdown = parsedCountdown;
        }
        TimeUnit parsedUnit;
        try {
            parsedUnit = TimeUnit.valueOf(plugin.getRewards().get(path + ".timeUnit"));
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("Invalid timeUnit for '" + path + "' - defaulting to DAYS.");
            parsedUnit = TimeUnit.DAYS;
        }
        this.unit = parsedUnit;

        this.noClaimed = new RewardStatus(plugin, path + ".noClaimed");
        this.claimed = new RewardStatus(plugin, path + ".claimed");
        this.rewards = Collections.unmodifiableList(plugin.getRewards()
                .getListOrDefaultStrings(path + ".rewards", Collections.emptyList()));
    }

    public String getId() { return id; }
    public String getPermission() { return permission; }
    public String getNoPermissionMessage() { return noPermissionMessage; }
    public int getSlot() { return slot; }
    public int getCountdown() { return countdown; }
    public RewardStatus getNoClaimed() { return noClaimed; }
    public RewardStatus getClaimed() { return claimed; }
    public List<String> getRewards() { return rewards; }
    public float getNoPermissionVolume() { return noPermissionVolume; }
    public float getNoPermissionPitch() { return noPermissionPitch; }
    public Sound getNoPermissionSound() { return noPermissionSound; }
    public TimeUnit getUnit() { return unit; }

    public ItemStack getIcon(Player p, boolean claimed, String countdownStr) {
        if (claimed) {
            return (ItemStack) NBTEditor.set(
                    this.claimed.getIcon("\u00a7c", countdownStr), this.id, NBT_KEY, NBT_FIELD);
        }
        String color = p.hasPermission(this.permission) ? "\u00a7e" : "\u00a7c";
        return (ItemStack) NBTEditor.set(
                this.noClaimed.getIcon(color, ""), this.id, NBT_KEY, NBT_FIELD);
    }

    public void playSound(Player p) {
        p.playSound(p.getLocation(), this.noPermissionSound, this.noPermissionVolume, this.noPermissionPitch);
    }
}
