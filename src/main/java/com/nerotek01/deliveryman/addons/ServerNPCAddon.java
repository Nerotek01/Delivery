/*    */ package com.nerotek01.deliveryman.addons;
/*    */ 
/*    */ import com.isnakebuzz.servernpc.Events.NPCInteractEvent;
/*    */ import com.isnakebuzz.servernpc.Main;
/*    */ import com.isnakebuzz.servernpc.NPC.SnakeNPC;
/*    */ import com.isnakebuzz.servernpc.NPC.Utilities.NPCSettings;
/*    */ import com.isnakebuzz.servernpc.Skins.SkinData;
/*    */ import com.nerotek01.deliveryman.Main;
/*    */ import com.nerotek01.deliveryman.api.DeliveryNPCInteractEvent;
/*    */ import com.nerotek01.deliveryman.skins.SkinProperty;
/*    */ import java.util.HashMap;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ public class ServerNPCAddon implements Listener {
/* 20 */   private HashMap<Location, SnakeNPC> npcs = new HashMap<>();
/*    */   private Main plugin;
/*    */   
/*    */   public ServerNPCAddon(Main plugin) {
/* 24 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public void createNPC(Location loc, SkinProperty sp) {
/* 28 */     String n = UUID.randomUUID().toString().substring(0, 8);
/* 29 */     NPCSettings s = new NPCSettings();
/* 30 */     s.setLookClose(true);
/* 31 */     SkinData sd = new SkinData(sp.getValue(), sp.getSignature(), this.plugin.getCm().getDeliverySkin());
/*    */     try {
/* 33 */       SnakeNPC npc = Main.getAPI().createNPC(n, UUID.randomUUID(), s, sd, loc, null);
/* 34 */       this.npcs.put(loc, npc);
/* 35 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeNPC(Location loc) {
/* 40 */     SnakeNPC npc = this.npcs.get(loc);
/* 41 */     npc.delete();
/* 42 */     this.npcs.remove(loc);
/*    */   }
/*    */   
/*    */   public int getEntityID(Location loc) {
/* 46 */     SnakeNPC npc = this.npcs.get(loc);
/* 47 */     if (npc != null) {
/* 48 */       return npc.getEntityID();
/*    */     }
/* 50 */     return -1;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onNPCClick(NPCInteractEvent e) {
/* 55 */     int id = e.getSnakeNPC().getEntityID();
/* 56 */     if (!this.plugin.getNpc().getNpcs().containsKey(Integer.valueOf(id)))
/* 57 */       return;  Bukkit.getPluginManager().callEvent((Event)new DeliveryNPCInteractEvent(e.getPlayer(), e.getClickType().equals(NPCInteractEvent.ClickType.RIGHT_CLICK)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\addons\ServerNPCAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */