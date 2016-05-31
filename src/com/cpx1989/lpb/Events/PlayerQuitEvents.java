package com.cpx1989.lpb.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.cpx1989.lpb.Paintball;
import com.cpx1989.lpb.Utils.PlayerHandler;

public class PlayerQuitEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if(PlayerHandler.invs.containsKey(p)){
			p.getInventory().setContents(PlayerHandler.invs.get(p));
			PlayerHandler.invs.remove(p);
		}
		Paintball.instance.sb.getScoreboard().resetScores(p);
	}

}
