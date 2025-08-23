/*    */ package com.nerotek01.deliveryman.addons;
/*    */ 
/*    */ import com.nerotek01.deliveryman.Main;
/*    */ import com.nerotek01.deliveryman.interfaces.HologramAddon;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import me.arasple.mc.trhologram.api.TrHologramAPI;
/*    */ import me.arasple.mc.trhologram.hologram.Hologram;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class TrHologramAddon
/*    */   implements HologramAddon {
/* 14 */   private HashMap<Integer, Hologram> holograms = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void createHologram(int id, Location spawn, List<String> lines) {
/* 18 */     Location loc = spawn.clone();
/* 19 */     Hologram h = TrHologramAPI.createHologram((Plugin)Main.get(), String.valueOf(id), loc.clone().add(0.0D, 1.3D + lines.size() * 0.3D, 0.0D), lines);
/* 20 */     this.holograms.put(Integer.valueOf(id), h);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteHologram(int id) {
/* 25 */     if (this.holograms.containsKey(Integer.valueOf(id))) {
/* 26 */       ((Hologram)this.holograms.get(Integer.valueOf(id))).delete();
/* 27 */       this.holograms.remove(Integer.valueOf(id));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasHologram(int id) {
/* 33 */     return this.holograms.containsKey(Integer.valueOf(id));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delete() {
/* 38 */     for (Hologram h : this.holograms.values()) {
/* 39 */       h.destroyAll();
/* 40 */       h.delete();
/*    */     } 
/* 42 */     this.holograms.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\addons\TrHologramAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */