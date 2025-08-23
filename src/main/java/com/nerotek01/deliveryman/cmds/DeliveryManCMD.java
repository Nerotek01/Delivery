package com.nerotek01.deliveryman.cmds;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeliveryManCMD implements CommandExecutor {
    private final Main plugin;

    public DeliveryManCMD(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "menu":
                plugin.getRem().createRewardMenu(player);
                return true;

            case "add":
                return handleAddCommand(player, args);

            case "remove":
                return handleRemoveCommand(player, args);

            case "reload":
                return handleReloadCommand(player);

            default:
                sendHelp(player);
                return true;
        }
    }

    private boolean handleAddCommand(Player player, String[] args) {
        if (!player.hasPermission("deliveryman.admin")) {
            player.sendMessage(plugin.getLang().get("setup.noPermission"));
            return true;
        }

        if (args.length < 2) {
            sendHelp(player);
            return true;
        }

        String key = args[1];
        String configPath = "npcs." + key;

        if (plugin.getConfig().isSet(configPath)) {
            player.sendMessage(plugin.getLang().get("setup.alreadyNPC"));
            return true;
        }

        plugin.getConfig().set(configPath, Utils.getLocationString(player.getLocation()));
        plugin.saveConfig();
        plugin.getNpc().reload();

        player.sendMessage(plugin.getLang().get("setup.addNPC")
                .replace("<key>", key)
                .replace("<loc>", Utils.getFormatedLocation(player.getLocation())));
        return true;
    }

    private boolean handleRemoveCommand(Player player, String[] args) {
        if (!player.hasPermission("deliveryman.admin")) {
            player.sendMessage(plugin.getLang().get("setup.noPermission"));
            return true;
        }

        if (args.length < 2) {
            sendHelp(player);
            return true;
        }

        String key = args[1];
        String configPath = "npcs." + key;

        if (!plugin.getConfig().isSet(configPath)) {
            player.sendMessage(plugin.getLang().get("setup.alreadyNPC"));
            return true;
        }

        plugin.getConfig().set(configPath, null);
        plugin.saveConfig();
        plugin.getNpc().reload();

        player.sendMessage(plugin.getLang().get("setup.removeNPC")
                .replace("<key>", key));
        return true;
    }

    private boolean handleReloadCommand(Player player) {
        if (!player.hasPermission("deliveryman.admin")) {
            player.sendMessage(plugin.getLang().get("setup.noPermission"));
            return true;
        }

        plugin.reload();
        player.sendMessage(plugin.getLang().get("setup.reload"));
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§7§m--------------------------------");
        sender.sendMessage("§e/udm menu §a- §bOpen delivery menu");
        if (sender.hasPermission("deliveryman.admin")) {
            sender.sendMessage("§e/udm add <key> §a- §bAdd NPC");
            sender.sendMessage("§e/udm remove <key> §a- §bRemove NPC");
            sender.sendMessage("§e/udm reload §a- §bReload config");
        }
        sender.sendMessage("§7§m--------------------------------");
    }
}