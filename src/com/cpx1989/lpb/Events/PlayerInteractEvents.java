package com.cpx1989.lpb.Events;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.cpx1989.lpb.ActionBarAPI;
import com.cpx1989.lpb.Paintball;
import com.cpx1989.lpb.Utils.Item;
import com.cpx1989.lpb.Utils.Items;
import com.cpx1989.lpb.Utils.PlayerHandler;
import com.cpx1989.lpb.Utils.Items.ItemType;
import com.cpx1989.lpb.Utils.VanishHook;

public class PlayerInteractEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerInteract(PlayerInteractEvent event){
		Player p = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = p.getItemInHand();
			Map<Integer, Item> items = new HashMap<Integer, Item>();
			for (Entry<Integer, Item> e : Items.getItems().entrySet()){
				items.put(e.getKey(), e.getValue());
			}
			for (Item i : items.values()){
				if (item.equals(i.getItemStack())){
					if (i.getType() == ItemType.OPTIN){
						if (VanishHook.isVanished(p)){
							ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must unvanish to join paintball!"); 
							return;
						}
						if (PlayerHandler.times.containsKey(p)){
							if (PlayerHandler.times.get(p).containsKey(i.getIndex())){
								if (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) < i.getCooldown()){
									int secToGo = Math.abs((int) (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) - i.getCooldown()));
									ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must wait "+ secToGo + " second(s) till you can join paintball!");
						    		event.setCancelled(true);
									return;
								}
								PlayerHandler.joinGame(p);
								Map<Integer, Long> l = new HashMap<Integer, Long>(PlayerHandler.times.get(p));
								l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
								PlayerHandler.times.put(p, l);
								event.setCancelled(true);
								return;
							}
							PlayerHandler.joinGame(p);
							Map<Integer, Long> l = new HashMap<Integer, Long>(PlayerHandler.times.get(p));
							l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
							PlayerHandler.times.put(p, l);
							event.setCancelled(true);
							return;
						}
						PlayerHandler.joinGame(p);
						Map<Integer, Long> l = new HashMap<Integer, Long>();
						l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
						PlayerHandler.times.put(p, l);
						event.setCancelled(true);
						return;
						
						
					} else if (i.getType() == ItemType.OPTOUT){
						if (VanishHook.isVanished(p)){
							ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must unvanish to join paintball!"); 
							return;
						}
						if (PlayerHandler.times.containsKey(p)){
							if (PlayerHandler.times.get(p).containsKey(i.getIndex())){
								if (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) < i.getCooldown()){
									int secToGo = Math.abs((int) (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) - i.getCooldown()));
									ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must wait "+ secToGo + " second(s) till you can leave paintball!");
									event.setCancelled(true);
									return;
								}
							}
						}
						PlayerHandler.leaveGame(p);
						event.setCancelled(true);
						Map<Integer, Long> l = new HashMap<Integer, Long>();
						l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
						PlayerHandler.times.put(p, l);
					} else if (i.getType() == ItemType.SINGLE || i.getType() == ItemType.RAPID || i.getType() == ItemType.BURST){
						if (p.hasPermission(i.getPermission())){
							if (PlayerHandler.times.containsKey(p) && !p.hasPermission("lpb.staff")){
								if (PlayerHandler.times.get(p).containsKey(i.getIndex())){
									if (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) >= i.getCooldown()){
										if (i.getType() == ItemType.SINGLE) {PlayerHandler.single(p, i.getPower());}
										else if (i.getType() == ItemType.BURST) {PlayerHandler.burst(p, i.getPower(), i.getShots(), i.getSpread());}
										else if (i.getType() == ItemType.RAPID) {PlayerHandler.rapid(p, i.getPower(), i.getShots(), i.getSpeed());}
							    		event.setCancelled(true);
							    		Map<Integer, Long> l = PlayerHandler.times.get(p);
							    		l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
							        	PlayerHandler.times.put(p, l);
									} else {
										int secToGo = Math.abs((int) (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) - i.getCooldown()));
										ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must wait "+ secToGo + " second(s) till you can shoot this item again!");
									}
								} else {
									if (i.getType() == ItemType.SINGLE) {PlayerHandler.single(p, i.getPower());}
									else if (i.getType() == ItemType.BURST) {PlayerHandler.burst(p, i.getPower(), i.getShots(), i.getSpread());}
									else if (i.getType() == ItemType.RAPID) {PlayerHandler.rapid(p, i.getPower(), i.getShots(), i.getSpeed());}
						    		event.setCancelled(true);
						    		Map<Integer, Long> l = PlayerHandler.times.get(p);
						    		l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
						        	PlayerHandler.times.put(p, l);
								}
							} else {
								if (i.getType() == ItemType.SINGLE) {PlayerHandler.single(p, i.getPower());}
								else if (i.getType() == ItemType.BURST) {PlayerHandler.burst(p, i.getPower(), i.getShots(), i.getSpread());}
								else if (i.getType() == ItemType.RAPID) {PlayerHandler.rapid(p, i.getPower(), i.getShots(), i.getSpeed());}
					    		event.setCancelled(true);
					    		Map<Integer, Long> l = new HashMap<Integer, Long>();
					    		l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
					        	PlayerHandler.times.put(p, l);
							}
						} else {
							ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You do not have permission to use this item!");
						}
					} 
				}
			}
		}
		p.updateInventory();
	}
}
