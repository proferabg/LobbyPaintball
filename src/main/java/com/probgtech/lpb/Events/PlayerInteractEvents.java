package com.probgtech.lpb.Events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.probgtech.lpb.ActionBarAPI;
import com.probgtech.lpb.Paintball;
import com.probgtech.lpb.Utils.Item;
import com.probgtech.lpb.Utils.Items;
import com.probgtech.lpb.Utils.PlayerHandler;
import com.probgtech.lpb.Utils.Items.ItemType;

public class PlayerInteractEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = p.getItemInHand();
			Map<Integer, Item> items = new HashMap<Integer, Item>(Items.getItems());
			for (Item i : items.values()){
				if (!item.equals(i.getItemStack())) continue;
				if (i.getType() == ItemType.OPTIN) OptIn(p, i, e);
				else if (i.getType() == ItemType.OPTOUT) OptOut(p, i, e);
				else if (i.getType() == ItemType.SINGLE || i.getType() == ItemType.RAPID || i.getType() == ItemType.BURST) Fire(p, i, e);
			}
		}
		p.updateInventory();
	}

	private void Fire(Player p, Item i, PlayerInteractEvent e){
		if (!p.hasPermission(i.getPermission())){
			ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You do not have permission to use this item!");
			return;
		}
		

		if (PlayerHandler.times.containsKey(p) && 
			PlayerHandler.times.get(p).containsKey(i.getIndex()) && 
			PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) < i.getCooldown() &&
			!p.hasPermission("lpb.staff")){
				int secToGo = Math.abs((int) (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) - i.getCooldown()));
				ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must wait "+ secToGo + " second(s) till you can shoot this item again!");
				return;
		}

		if (i.getType() == ItemType.SINGLE) {PlayerHandler.single(p, i.getPower());}
		else if (i.getType() == ItemType.BURST) {PlayerHandler.burst(p, i.getPower(), i.getShots(), i.getSpread());}
		else if (i.getType() == ItemType.RAPID) {PlayerHandler.rapid(p, i.getPower(), i.getShots(), i.getSpeed());}
		e.setCancelled(true);
		Map<Integer, Long> l = PlayerHandler.times.get(p);
		l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
		PlayerHandler.times.put(p, l);
	}


	private void OptOut(Player p, Item i, PlayerInteractEvent e) {
		if (PlayerHandler.isVanished(p)){
			ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must unvanish to join paintball!"); 
			return;
		}
		if (PlayerHandler.times.containsKey(p) && PlayerHandler.times.get(p).containsKey(i.getIndex()) && PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) < i.getCooldown()){
			int secToGo = Math.abs((int) (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) - i.getCooldown()));
			ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must wait "+ secToGo + " second(s) till you can leave paintball!");
			e.setCancelled(true);
			return;
		}
		PlayerHandler.leaveGame(p);
		e.setCancelled(true);
		Map<Integer, Long> l = new HashMap<Integer, Long>();
		l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
		PlayerHandler.times.put(p, l);
	}

	private void OptIn(Player p, Item i, PlayerInteractEvent e) {
		if (PlayerHandler.isVanished(p)){
			ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must unvanish to join paintball!"); 
			return;
		}
			
		if (PlayerHandler.times.containsKey(p) && PlayerHandler.times.get(p).containsKey(i.getIndex()) && PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) < i.getCooldown()){
			int secToGo = Math.abs((int) (PlayerHandler.getDiff(PlayerHandler.times.get(p).get(i.getIndex())) - i.getCooldown()));
			ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + "You must wait "+ secToGo + " second(s) till you can join paintball!");
			e.setCancelled(true);
			return;
		}
		
		PlayerHandler.joinGame(p);
		Map<Integer, Long> l = new HashMap<Integer, Long>();
		l.put(i.getIndex(), System.currentTimeMillis() / 1000L);
		PlayerHandler.times.put(p, l);
		e.setCancelled(true);
		return;
	}
}
