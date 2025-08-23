/*    */ package com.nerotek01.deliveryman.addons;
/*    */ 
/*    */ import com.Zrips.CMI.CMI;
/*    */ import com.Zrips.CMI.Containers.CMILocation;
/*    */ import com.Zrips.CMI.Modules.Holograms.CMIHologram;
/*    */ import com.nerotek01.deliveryman.interfaces.HologramAddon;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Location;
/*    */ 
/*    */ public class CMIAddon
/*    */   implements HologramAddon
/*    */ {
/* 15 */   private HashMap<Integer, CMIHologram> holograms = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void createHologram(int id, Location spawn, List<String> lines) {
/* 19 */     CMIHologram h = new CMIHologram(UUID.randomUUID().toString(), new CMILocation(spawn.clone().add(0.0D, 2.0D, 0.0D)));
/* 20 */     for (String l : lines) {
/* 21 */       h.addLine(l);
/*    */     }
/* 23 */     CMI.getInstance().getHologramManager().addHologram(h);
/* 24 */     this.holograms.put(Integer.valueOf(id), h);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteHologram(int id) {
/* 29 */     if (this.holograms.containsKey(Integer.valueOf(id))) {
/* 30 */       CMIHologram h = this.holograms.get(Integer.valueOf(id));
/* 31 */       h.hide();
/* 32 */       h.remove();
/* 33 */       this.holograms.remove(Integer.valueOf(id));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasHologram(int id) {
/* 39 */     return this.holograms.containsKey(Integer.valueOf(id));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delete() {
/* 44 */     this.holograms.values().forEach(CMIHologram::remove);
/* 45 */     this.holograms.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\addons\CMIAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */