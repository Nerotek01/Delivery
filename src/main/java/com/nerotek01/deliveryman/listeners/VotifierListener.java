package com.nerotek01.deliveryman.listeners;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.rewards.Reward;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener {
    private final Main plugin;

    public VotifierListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVote(VotifierEvent e) {
        Vote vote = e.getVote();
        String username = vote.getUsername();
        if (username == null || username.isEmpty()) return;

        Player p = Bukkit.getPlayer(username);
        if (p == null) return;

        String serviceName = vote.getServiceName();
        String rewardId = plugin.getRm().getVotes().get(serviceName);
        if (rewardId == null) return;

        if (plugin.getDm().isVoting(p.getName(), serviceName)) {
            Reward reward = plugin.getRm().getRewards().get(rewardId);
            if (reward == null) return;
            PlayerData pd = plugin.getDm().getPlayerData(p);

            String message = reward.getNoClaimed().getMessage();
            if (message != null) {
                String formatted = message
                        .replace("<reward>", reward.getNoClaimed().getName())
                        .replace("<status>", "\u00a7e");
                for (String line : formatted.split("\\n")) {
                    p.sendMessage(line.replace("&", "\u00a7"));
                }
            }

            pd.claim(reward.getId(), System.currentTimeMillis());
            executeCommands(p, reward.getRewards());
            plugin.getDm().removeVoting(p.getName(), serviceName);
            plugin.getPluginLogger().info("Vote reward '" + rewardId + "' granted to " + p.getName()
                    + " from service " + serviceName);
        }
    }

    private void executeCommands(Player p, Iterable<String> commands) {
        if (commands == null) return;
        String playerName = p.getName();
        for (String cmd : commands) {
            if (cmd == null || cmd.isEmpty()) continue;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("<player>", playerName));
        }
    }
}
