package com.cpx1989.lpb.Events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import com.cpx1989.lpb.Paintball;

public class HangingBreakByEntityEvents implements Listener {

	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent e){
		if (e.getRemover() instanceof Player || (e.getRemover() instanceof Snowball && ((Snowball) e.getRemover()).getShooter() instanceof Player)){
			Player p = (Player) e.getRemover();
			if (Paintball.instance.plist.contains(p)){
				e.setCancelled(true);
			}
		} else if (!(e.getRemover() instanceof Player)){
			e.setCancelled(true);
		}
	}
	
}
