package com.nerotek01.deliveryman.interfaces;

import java.util.List;
import org.bukkit.Location;

public interface HologramAddon {
  void createHologram(int paramInt, Location paramLocation, List<String> paramList);
  
  void deleteHologram(int paramInt);
  
  boolean hasHologram(int paramInt);
  
  void delete();
}


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\interfaces\HologramAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */