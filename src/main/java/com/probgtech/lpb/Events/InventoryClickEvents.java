package com.probgtech.lpb.Events;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.probgtech.lpb.Paintball;

public class InventoryClickEvents implements Listener {
	
	@EventHandler
	public void onPlayerInteract(InventoryClickEvent event) throws IOException {
		final Player p = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if (item != null && Paintball.instance.plist.contains(p) && !p.hasPermission("lpg.moveinventory")){
			event.setCancelled(true);
			event.setResult(Result.DENY);
			p.updateInventory();
		}
	}

}
