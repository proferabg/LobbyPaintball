package com.probgtech.lpb.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.probgtech.lpb.Paintball;

public class EntityDamageEvents implements Listener {
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player && e.getCause() == DamageCause.FALL){
			Player p = (Player) e.getEntity();
			if (Paintball.instance.plist.contains(p)) e.setCancelled(true);
		}
	}

}
