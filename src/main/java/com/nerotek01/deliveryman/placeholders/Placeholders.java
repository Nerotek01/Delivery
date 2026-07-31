package com.nerotek01.deliveryman.placeholders;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    private final Main plugin;

    public Placeholders(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "udm";
    }

    @Override
    public String getAuthor() {
        return "Nerotek01";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String id) {
        if (p == null) return "";
        PlayerData pd = this.plugin.getDm().getPlayerData(p);
        if ("rewards".equals(id)) {
            return String.valueOf(this.plugin.getRm().getAvailableRewards(p, pd));
        }
        return "";
    }
}
