package com.nerotek01.deliveryman.cmds;

import com.nerotek01.deliveryman.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeliveryManCMD implements CommandExecutor, TabCompleter {

    private static final List<String> SUBCOMMANDS = Arrays.asList("menu", "reload");

    private final Main plugin;

    public DeliveryManCMD(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "menu":
                return handleMenu(sender);
            case "reload":
                return handleReload(sender);
            default:
                sendHelp(sender);
                return true;
        }
    }

    private boolean handleMenu(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("\u00a7cThis command can only be used by players.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("deliveryman.menu")) {
            player.sendMessage(plugin.getLang().get("setup.noPermission"));
            return true;
        }
        plugin.getRem().createRewardMenu(player);
        return true;
    }

    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("deliveryman.admin")) {
            sender.sendMessage(plugin.getLang().get("setup.noPermission"));
            return true;
        }
        try {
            plugin.reload();
            sender.sendMessage(plugin.getLang().get("setup.reload"));
        } catch (Throwable ex) {
            plugin.getPluginLogger().severe("Reload failed", ex);
            sender.sendMessage("\u00a7cReload failed: " + ex.getMessage());
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("\u00a78\u00a7m--------------------------------");
        sender.sendMessage("\u00a7eDeliveryMan \u00a77- Commands");
        sender.sendMessage("\u00a78\u00a7m--------------------------------");
        if (sender.hasPermission("deliveryman.menu")) {
            sender.sendMessage("\u00a7e/udm menu \u00a77- \u00a7fOpen the rewards menu");
        }
        if (sender.hasPermission("deliveryman.admin")) {
            sender.sendMessage("\u00a7e/udm reload \u00a77- \u00a7fReload configuration");
        }
        sender.sendMessage("\u00a78\u00a7m--------------------------------");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            List<String> result = new ArrayList<>();
            for (String sub : SUBCOMMANDS) {
                if (!sub.startsWith(prefix)) continue;
                if ("menu".equals(sub) && !sender.hasPermission("deliveryman.menu")) continue;
                if ("reload".equals(sub) && !sender.hasPermission("deliveryman.admin")) continue;
                result.add(sub);
            }
            return result;
        }
        return Collections.emptyList();
    }
}
