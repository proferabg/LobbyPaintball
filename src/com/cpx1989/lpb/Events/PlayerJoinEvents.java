package com.cpx1989.lpb.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.cpx1989.lpb.Paintball;
import com.cpx1989.lpb.Utils.Item;
import com.cpx1989.lpb.Utils.Items;
import com.cpx1989.lpb.Utils.PlayerHandler;

public class PlayerJoinEvents implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		PlayerInventory inv = p.getInventory();
		if (Paintball.cfg.getBoolean("Options.ClearOnJoin")){
			if (inv.getBoots() != null && inv.getBoots().getType() != Material.AIR){
				inv.setBoots(new ItemStack(Material.AIR));
			}
			if (inv.getChestplate()  != null && inv.getChestplate().getType() != Material.AIR){
				inv.setChestplate(new ItemStack(Material.AIR));
			}
			if (inv.getHelmet() != null && inv.getHelmet().getType() != Material.AIR){
				inv.setHelmet(new ItemStack(Material.AIR));
			}
			if (inv.getLeggings() != null && inv.getLeggings().getType() != Material.AIR){
				inv.setLeggings(new ItemStack(Material.AIR));
			}
			for (Item i : Items.items.values()){
				if(inv.contains(i.getItemStack())){
					inv.remove(i.getItemStack());
				}
			}
		}
		inv.setItem(PlayerHandler.getOptSlot(), Items.getOptIn());
	}

}
