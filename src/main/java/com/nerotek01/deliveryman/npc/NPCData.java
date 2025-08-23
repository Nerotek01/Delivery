package com.nerotek01.deliveryman.npc;

import com.nerotek01.deliveryman.Main;
import org.bukkit.Location;

public class NPCData {

    private final Main plugin;
    private final Location loc;
    private NPC npc;

    public NPCData(Main plugin, Location loc) {
        this.plugin = plugin;
        this.loc = loc;

        String skin = plugin.getCm().getDeliverySkin();

        if (plugin.getAdm().isNPCAddon()) {
            plugin.getAdm().getNpc().createNPC(loc, plugin.getSc().loadSkinProperty(skin));
            if (plugin.getAdm().isHologramAddon()) {
                plugin.getAdm().getHologram().createHologram(
                        plugin.getAdm().getNpc().getEntityID(loc),
                        loc.clone().add(0.0D, 0.7D, 0.0D),
                        plugin.getLang().getList("holograms.delivery")
                );
            }
        } else {
            this.npc = plugin.getVc().getNPC(loc, plugin.getSc().loadSkinProperty(skin));
            if (plugin.getAdm().isHologramAddon() && npc != null) {
                plugin.getAdm().getHologram().createHologram(
                        npc.getEntityID(),
                        loc.clone().add(0.0D, 0.7D, 0.0D),
                        plugin.getLang().getList("holograms.delivery")
                );
            }
        }
    }

    public Main getPlugin() { return plugin; }
    public Location getLoc() { return loc; }
    public NPC getNpc() { return npc; }

    public void delete() {
        if (plugin.getAdm().isNPCAddon()) {
            if (plugin.getAdm().isHologramAddon()) {
                plugin.getAdm().getHologram().deleteHologram(plugin.getAdm().getNpc().getEntityID(loc));
            }
            plugin.getAdm().getNpc().removeNPC(loc);
        } else {
            if (npc != null) {
                if (plugin.getAdm().isHologramAddon()) {
                    plugin.getAdm().getHologram().deleteHologram(npc.getEntityID());
                }
                npc.remove();
            }
        }
    }

    public int getID() {
        return plugin.getAdm().isNPCAddon() ?
                plugin.getAdm().getNpc().getEntityID(loc) :
                (npc != null ? npc.getEntityID() : -1);
    }
}
