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
        Player p = Bukkit.getPlayer(vote.getUsername());
        if (p == null) return;

        String serviceName = vote.getServiceName();
        String rewardId = plugin.getRm().getVotes().get(serviceName);
        if (rewardId == null) return;

        if (plugin.getDm().isVoting(p.getName(), serviceName)) {
            Reward reward = plugin.getRm().getRewards().get(rewardId);
            PlayerData pd = plugin.getDm().getPlayerData(p);

            p.sendMessage(reward.getNoClaimed().getMessage()
                    .replace("<reward>", reward.getNoClaimed().getName())
                    .replace("<status>", "§e"));

            pd.getClaimed().put(reward.getId(), System.currentTimeMillis());
            executeCommands(p, reward.getRewards());
            plugin.getDm().removeVoting(p.getName(), serviceName);
        }
    }

    private void executeCommands(Player p, Iterable<String> commands) {
        String playerName = p.getName();
        commands.forEach(cmd ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("<player>", playerName))
        );
    }
}