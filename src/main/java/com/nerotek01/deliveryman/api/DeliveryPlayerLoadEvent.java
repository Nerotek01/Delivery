package com.nerotek01.deliveryman.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeliveryPlayerLoadEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;

    public DeliveryPlayerLoadEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
