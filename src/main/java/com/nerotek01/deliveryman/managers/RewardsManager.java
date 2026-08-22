package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.rewards.Reward;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RewardsManager {
    private final Main plugin;
    private final Map<String, Reward> rewards = new HashMap<>();

    public RewardsManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        rewards.clear();

        var section = plugin.getRewards().getConfig().getConfigurationSection("rewards");
        if (section == null) return;

        section.getKeys(false).forEach(r -> {
            try {
                Reward reward = new Reward(plugin, "rewards." + r);
                if (reward.getId() == null) {
                    plugin.getLogger().warning("Reward at 'rewards." + r + "' has no id - skipping.");
                    return;
                }
                rewards.put(reward.getId(), reward);
            } catch (Throwable ex) {
                plugin.getLogger().warning("Failed to load reward 'rewards." + r + "': " + ex.getMessage());
            }
        });
    }

    public int getAvailableRewards(Player p, PlayerData pd) {
        if (pd == null) return 0;
        long now = System.currentTimeMillis();
        return (int) rewards.values().stream()
                .filter(r -> p.hasPermission(r.getPermission()))
                .filter(r -> {
                    if (!pd.hasClaimed(r.getId())) return true;
                    long claimedTime = pd.getClaimTime(r.getId());
                    long cooldownEnd = claimedTime + r.getUnit().toMillis(r.getCountdown());
                    return now >= cooldownEnd;
                })
                .count();
    }

    public boolean isRewardAvailable(Player p, PlayerData pd, Reward r) {
        if (pd == null || r == null) return false;
        if (!p.hasPermission(r.getPermission())) return false;
        if (!pd.hasClaimed(r.getId())) return true;
        long claimedTime = pd.getClaimTime(r.getId());
        long cooldownEnd = claimedTime + r.getUnit().toMillis(r.getCountdown());
        return System.currentTimeMillis() >= cooldownEnd;
    }

    public Map<String, Reward> getRewards() { return rewards; }
}
