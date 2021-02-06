package com.probgtech.lpb.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.probgtech.lpb.Paintball;
import com.probgtech.lpb.Utils.PlayerHandler;

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
