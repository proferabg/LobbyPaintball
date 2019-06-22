package com.cpx1989.lpb.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.cpx1989.lpb.Utils.Item;
import com.cpx1989.lpb.Utils.Items;

public class PlayerDropItemEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		for (Item i : Items.getItems().values()){
			if (player.getItemInHand().equals(i.getItemStack())){
				e.setCancelled(true);
			} else if(e.getItemDrop().getItemStack().equals(i.getItemStack())){
				if (!player.hasPermission("lpb.drop")){
					e.setCancelled(true);
				}
			}
		}
	}

}
