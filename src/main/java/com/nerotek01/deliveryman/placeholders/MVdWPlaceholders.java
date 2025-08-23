package com.nerotek01.deliveryman.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MVdWPlaceholders {

    private final Main plugin;

    public MVdWPlaceholders(Main plugin) {
        this.plugin = plugin;
    }

    public void register() {
        PlaceholderAPI.registerPlaceholder((Plugin) this.plugin, "udm_rewards", e -> {
            Player p = e.getPlayer();
            PlayerData pd = this.plugin.getDm().getPlayerData(p);
            return String.valueOf(this.plugin.getRm().getRewards(p, pd));
        });
    }
}
