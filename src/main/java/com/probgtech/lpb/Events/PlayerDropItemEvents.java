package com.probgtech.lpb.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.probgtech.lpb.Utils.Item;
import com.probgtech.lpb.Utils.Items;

public class PlayerDropItemEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		for (Item i : Items.getItems().values()){
			if (player.getItemInHand().equals(i.getItemStack())) e.setCancelled(true);
			else if(e.getItemDrop().getItemStack().equals(i.getItemStack()) && !player.hasPermission("lpb.drop")) e.setCancelled(true);
		}
	}

}
