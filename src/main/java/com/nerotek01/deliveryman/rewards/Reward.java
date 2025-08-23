package com.nerotek01.deliveryman.rewards;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.enums.RewardType;
import com.nerotek01.deliveryman.utils.NBTEditor;
import com.nerotek01.deliveryman.xseries.XSound;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Reward {

    private final String id;
    private final String permission;
    private final String noPermissionMessage;
    private final int slot;
    private final RewardType type;
    private final RewardStatus noClaimed;
    private final RewardStatus claimed;
    private final List<String> rewards;
    private final float noPermissionVolume;
    private final float noPermissionPitch;
    private final boolean fireworkExplode;
    private final Sound noPermissionSound;

    private String message = "";
    private String voteSite = "";
    private int countdown = 999999;
    private TimeUnit unit = TimeUnit.DAYS;

    public Reward(Main plugin, String path) {
        this.id = plugin.getRewards().get(path + ".id");
        this.type = RewardType.valueOf(plugin.getRewards().get(path + ".type"));
        this.permission = plugin.getRewards().get(path + ".permission");
        this.slot = plugin.getRewards().getInt(path + ".slot");
        this.fireworkExplode = plugin.getRewards().getBooleanOrDefault(path + ".fireworkExplode", false);

        this.noPermissionSound = XSound.matchXSound(
                        plugin.getRewards().getOrDefault(path + ".noPermission.sound",
                                XSound.BLOCK_ANVIL_LAND.parseSound().name()))
                .orElse(XSound.BLOCK_ANVIL_LAND).parseSound();

        this.noPermissionVolume = Math.min(plugin.getRewards().getIntOrDefault(path + ".noPermission.volume", 1), 10);
        this.noPermissionPitch = Math.min(plugin.getRewards().getIntOrDefault(path + ".noPermission.pitch", 1), 10);
        this.noPermissionMessage = plugin.getRewards().getOrDefault(path + ".noPermission.message",
                "&cYou don't have permission for this.");

        if (type == RewardType.NORMAL || type == RewardType.VOTE) {
            this.countdown = plugin.getRewards().getInt(path + ".countdown");
            this.unit = TimeUnit.valueOf(plugin.getRewards().get(path + ".timeUnit"));
        }
        if (type == RewardType.MESSAGE || type == RewardType.VOTE) {
            this.message = plugin.getRewards().get(path + ".message");
        }
        if (type == RewardType.VOTE) {
            this.voteSite = plugin.getRewards().get(path + ".voteSite");
        }

        this.noClaimed = new RewardStatus(plugin, path + ".noClaimed");
        this.claimed = new RewardStatus(plugin, path + ".claimed");
        this.rewards = Collections.unmodifiableList(plugin.getRewards()
                .getListOrDefault(path + ".rewards", Collections.emptyList()));
    }

    public String getId() { return id; }
    public String getPermission() { return permission; }
    public String getMessage() { return message; }
    public String getVoteSite() { return voteSite; }
    public String getNoPermissionMessage() { return noPermissionMessage; }
    public int getSlot() { return slot; }
    public int getCountdown() { return countdown; }
    public RewardType getType() { return type; }
    public RewardStatus getNoClaimed() { return noClaimed; }
    public RewardStatus getClaimed() { return claimed; }
    public List<String> getRewards() { return rewards; }
    public float getNoPermissionVolume() { return noPermissionVolume; }
    public float getNoPermissionPitch() { return noPermissionPitch; }
    public boolean isFireworkExplode() { return fireworkExplode; }
    public Sound getNoPermissionSound() { return noPermissionSound; }
    public TimeUnit getUnit() { return unit; }

    public ItemStack getIcon(Player p, boolean claimed, String countdownStr) {
        if (claimed) {
            return (ItemStack) NBTEditor.set(
                    this.claimed.getIcon("§c", countdownStr), this.id, "ULTRADM", "ID");
        }
        String color = p.hasPermission(this.permission) ? "§e" : "§c";
        return (ItemStack) NBTEditor.set(
                this.noClaimed.getIcon(color, ""), this.id, "ULTRADM", "ID");
    }

    public void playSound(Player p) {
        p.playSound(p.getLocation(), this.noPermissionSound, this.noPermissionVolume, this.noPermissionPitch);
    }
}
