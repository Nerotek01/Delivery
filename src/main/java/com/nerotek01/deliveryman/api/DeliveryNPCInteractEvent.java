package com.nerotek01.deliveryman.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeliveryNPCInteractEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final boolean rightClick;

    public DeliveryNPCInteractEvent(Player player, boolean rightClick) {
        this.player = player;
        this.rightClick = rightClick;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}