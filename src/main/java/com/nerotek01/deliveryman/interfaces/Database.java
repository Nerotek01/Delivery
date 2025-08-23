package com.nerotek01.deliveryman.interfaces;

import org.bukkit.entity.Player;

public interface Database {
  void loadPlayer(Player paramPlayer);
  
  void savePlayer(Player paramPlayer);
  
  void savePlayerSync(Player paramPlayer);
  
  void close();
}


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\interfaces\Database.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */