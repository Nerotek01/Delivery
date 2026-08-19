package com.nerotek01.deliveryman.placeholders;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

public class MVdWPlaceholders {

    private final Main plugin;

    public MVdWPlaceholders(Main plugin) {
        this.plugin = plugin;
    }

    public void register() {
        try {
            Class<?> placeholderApiClass = Class.forName("be.maximvdw.placeholderapi.PlaceholderAPI");
            Method registerMethod = placeholderApiClass.getMethod(
                    "registerPlaceholder", Plugin.class, String.class, Class.forName("be.maximvdw.placeholderapi.PlaceholderReplacer"));

            Object replacer = java.lang.reflect.Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class[]{Class.forName("be.maximvdw.placeholderapi.PlaceholderReplacer")},
                    (proxy, method, args) -> {
                        if (!"onPlaceholderReplace".equals(method.getName())) {
                            return null;
                        }
                        Object event = args[0];
                        Method getPlayer = event.getClass().getMethod("getPlayer");
                        Player p = (Player) getPlayer.invoke(event);
                        if (p == null) return "";
                        PlayerData pd = this.plugin.getDm().getPlayerData(p);
                        if (pd == null) return "0";
                        return String.valueOf(this.plugin.getRm().getAvailableRewards(p, pd));
                    });

            registerMethod.invoke(null, this.plugin, "udm_rewards", replacer);
        } catch (ClassNotFoundException ex) {
        } catch (Throwable ex) {
            plugin.getPluginLogger().warning("Failed to register MVdWPlaceholderAPI placeholder: " + ex.getMessage());
        }
    }
}
