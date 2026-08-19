package com.nerotek01.deliveryman.cmds;

import com.nerotek01.deliveryman.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RewardsCMD implements CommandExecutor, TabCompleter {

    private static final String USAGE_PLAYER = "\u00a7cUsage: /rewards";
    private static final String USAGE_CONSOLE = "\u00a7cUsage: /rewards reload";

    private final Main plugin;

    public RewardsCMD(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean isPlayer = sender instanceof Player;
        boolean isConsole = !isPlayer;

        if (args.length == 0) {
            if (isPlayer) {
                return openMenu((Player) sender);
            }
            sender.sendMessage(USAGE_CONSOLE);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (isConsole) {
                try {
                    plugin.reload();
                    plugin.getLogger().info("Configuration reloaded.");
                } catch (Throwable ex) {
                    plugin.getLogger().severe("Reload failed: " + ex.getMessage());
                }
                return true;
            }
            sender.sendMessage(USAGE_PLAYER);
            return true;
        }

        sender.sendMessage(USAGE_PLAYER);
        return true;
    }

    private boolean openMenu(Player player) {
        if (!plugin.getCm().isMenuEnabled()) {
            String message = plugin.getLang().get("setup.disabled");
            if (message != null) {
                player.sendMessage(message);
            } else {
                player.sendMessage("\u00a7cThis feature is temporarily disabled until further notice. This may only be on this server!");
            }
            return true;
        }
        plugin.getRem().createRewardMenu(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1 && !(sender instanceof Player)) {
            String prefix = args[0].toLowerCase();
            List<String> result = new ArrayList<>();
            if ("reload".startsWith(prefix)) {
                result.add("reload");
            }
            return result;
        }
        return Collections.emptyList();
    }
}
