package com.nerotek01.deliveryman.interfaces;

import org.bukkit.entity.Player;

public interface Database {

    void loadPlayer(Player player);

    void savePlayer(Player player);

    void savePlayerSync(Player player);

    void close();

    default String backendName() {
        return getClass().getSimpleName();
    }
}
