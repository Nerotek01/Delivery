/*    */ package com.nerotek01.deliveryman.addons;
/*    */ 
/*    */ import com.sainttx.holograms.api.Hologram;
/*    */ import com.sainttx.holograms.api.HologramManager;
/*    */ import com.sainttx.holograms.api.HologramPlugin;
/*    */ import com.sainttx.holograms.api.line.HologramLine;
/*    */ import com.sainttx.holograms.api.line.TextLine;
/*    */ import com.nerotek01.deliveryman.interfaces.HologramAddon;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class HologramsAddon
/*    */   implements HologramAddon
/*    */ {
/* 18 */   public HashMap<Integer, Hologram> holograms = new HashMap<>();
/*    */   private HologramManager hologramManager;
/*    */   
/*    */   public HologramsAddon() {
/* 22 */     this.hologramManager = ((HologramPlugin)JavaPlugin.getPlugin(HologramPlugin.class)).getHologramManager();
/*    */   }
/*    */ 
/*    */   
/*    */   public void createHologram(int id, Location spawn, List<String> lines) {
/* 27 */     Location loc = spawn.clone();
/* 28 */     Hologram h = new Hologram(UUID.randomUUID().toString(), loc.clone().add(0.0D, 0.9D + lines.size() * 0.3D, 0.0D), false);
/* 29 */     for (String l : lines) {
/* 30 */       TextLine textLine = new TextLine(h, l.replaceAll("&", "§"));
/* 31 */       h.addLine((HologramLine)textLine);
/*    */     } 
/* 33 */     h.spawn();
/* 34 */     this.hologramManager.addActiveHologram(h);
/* 35 */     this.holograms.put(Integer.valueOf(id), h);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteHologram(int id) {
/* 40 */     if (this.holograms.containsKey(Integer.valueOf(id))) {
/* 41 */       this.hologramManager.deleteHologram(this.holograms.get(Integer.valueOf(id)));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasHologram(int id) {
/* 47 */     return this.holograms.containsKey(Integer.valueOf(id));
/*    */   }
/*    */ 
/*    */   
/*    */   public void delete() {
/* 52 */     for (Hologram h : this.holograms.values()) {
/* 53 */       this.hologramManager.deleteHologram(h);
/*    */     }
/* 55 */     this.holograms.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\addons\HologramsAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */