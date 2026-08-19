package com.nerotek01.deliveryman.rewards;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.utils.ItemUtils;
import com.nerotek01.deliveryman.xseries.XMaterial;
import com.nerotek01.deliveryman.xseries.XSound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RewardStatus {

    private final Material material;
    private final int amount;
    private final byte data;
    private final float volume;
    private final float pitch;
    private final String name;
    private final String message;
    private final List<String> lore;
    private final Sound sound;

    public RewardStatus(Main plugin, String path) {
        String materialName = plugin.getRewards().get(path + ".material");
        int dataValue = plugin.getRewards().getInt(path + ".data");

        XMaterial xm;
        try {
            xm = XMaterial.matchDefinedXMaterial(materialName, (byte) dataValue)
                    .orElse(XMaterial.CHEST);
        } catch (Exception ex) {
            plugin.getLogger().warning("Invalid material '" + materialName + "' for '" + path + "' - defaulting to CHEST.");
            xm = XMaterial.CHEST;
        }
        Material parsedMaterial = xm.parseMaterial();
        if (parsedMaterial == null) {
            parsedMaterial = Material.CHEST;
        }
        this.material = parsedMaterial;
        this.amount = plugin.getRewards().getInt(path + ".amount");
        this.data = (byte) dataValue;

        this.sound = XSound.matchXSound(plugin.getRewards()
                        .getOrDefault(path + ".sound", XSound.ENTITY_PLAYER_LEVELUP.parseSound().name()))
                .orElse(XSound.ENTITY_PLAYER_LEVELUP).parseSound();

        this.volume = Math.min(plugin.getRewards().getIntOrDefault(path + ".volume", 1), 10);
        this.pitch = Math.min(plugin.getRewards().getIntOrDefault(path + ".pitch", 1), 10);
        this.name = plugin.getRewards().get(path + ".name");
        this.message = plugin.getRewards().get(path + ".message");

        List<String> tempLore = new ArrayList<>();
        for (String s : plugin.getRewards().getList(path + ".lore")) {
            tempLore.add(s.replace("&", "\u00a7"));
        }
        this.lore = Collections.unmodifiableList(tempLore);
    }

    public Material getMaterial() { return material; }
    public int getAmount() { return amount; }
    public byte getData() { return data; }
    public float getVolume() { return volume; }
    public float getPitch() { return pitch; }
    public String getName() { return name; }
    public String getMessage() { return message; }
    public List<String> getLore() { return lore; }
    public Sound getSound() { return sound; }

    public ItemStack getIcon(String status, String countdown, boolean canClaim) {
        List<String> loreWithPlaceholders = new ArrayList<>();
        String clickText = canClaim ? "\u00a7eClick to loot!" : "\u00a7cLocked";
        for (String s : this.lore) {
            loreWithPlaceholders.add(s
                    .replace("<cooldown>", countdown)
                    .replace("<status>", status)
                    .replace("<click>", clickText));
        }
        return new ItemUtils(XMaterial.matchDefinedXMaterial(this.material.name(), this.data)
                .orElse(XMaterial.CHEST))
                .setDisplayName(this.name.replace("<status>", status))
                .setLore(loreWithPlaceholders)
                .applyAttributes()
                .build();
    }

    public void playSound(Player p) {
        p.playSound(p.getLocation(), this.sound, this.volume, this.pitch);
    }
}
