/*    */ package com.nerotek01.deliveryman.addons;
/*    */ 
/*    */ import com.gmail.filoghost.holographicdisplays.api.Hologram;
/*    */ import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
/*    */ import com.nerotek01.deliveryman.Main;
/*    */ import com.nerotek01.deliveryman.interfaces.HologramAddon;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class HolographicDisplaysAddon
/*    */   implements HologramAddon {
/* 14 */   private HashMap<Integer, Hologram> holograms = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void createHologram(int id, Location spawn, List<String> lines) {
/* 18 */     Location loc = spawn.clone();
/* 19 */     Hologram h = HologramsAPI.createHologram((Plugin)Main.get(), loc.clone().add(0.0D, 1.3D + lines.size() * 0.3D, 0.0D));
/* 20 */     for (String l : lines) {
/* 21 */       h.appendTextLine(l.replaceAll("&", "§"));
/*    */     }
/* 23 */     this.holograms.put(Integer.valueOf(id), h);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteHologram(int id) {
/* 28 */     if (this.holograms.containsKey(Integer.valueOf(id))) {
/* 29 */       ((Hologram)this.holograms.get(Integer.valueOf(id))).delete();
/* 30 */       this.holograms.remove(Integer.valueOf(id));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasHologram(int id) {
/* 36 */     return this.holograms.containsKey(Integer.valueOf(id));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delete() {
/* 41 */     for (Hologram h : this.holograms.values()) {
/* 42 */       h.delete();
/*    */     }
/* 44 */     this.holograms.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\addons\HolographicDisplaysAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */