package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.enums.RewardType;
import com.nerotek01.deliveryman.rewards.Reward;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RewardsManager {
    private final Main plugin;
    private final Map<String, Reward> rewards = new HashMap<>();
    private final Map<String, String> votes = new HashMap<>();

    public RewardsManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        rewards.clear();
        votes.clear();

        var section = plugin.getRewards().getConfig().getConfigurationSection("rewards");
        if (section == null) return;

        section.getKeys(false).forEach(r -> {
            Reward reward = new Reward(plugin, "rewards." + r);
            rewards.put(reward.getId(), reward);
            if (reward.getType() == RewardType.VOTE) {
                votes.put(reward.getVoteSite(), reward.getId());
            }
        });
    }

    public int getAvailableRewards(Player p, PlayerData pd) {
        if (pd == null) return 0;

        return (int) rewards.values().stream()
                .filter(r -> p.hasPermission(r.getPermission()))
                .filter(r -> r.getType() != RewardType.MESSAGE)
                .filter(r -> !pd.getClaimed().containsKey(r.getId()))
                .count();
    }

    public Map<String, Reward> getRewards() { return rewards; }
    public Map<String, String> getVotes() { return votes; }
}
