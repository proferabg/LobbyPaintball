package com.probgtech.lpb.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.probgtech.lpb.ActionBarAPI;
import com.probgtech.lpb.Paintball;

public class MessageHandler {
	
	public static void broadcastAPI(String msg){
		for(Player p : Paintball.instance.plist){
			ActionBarAPI.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', msg));
		}
	}
	
	
	
	public static void clearChat(){
		//for (int i=0; i < 100 ;i++) {
    	//	p.sendMessage("");
    	//}
		for (Player p : Paintball.instance.plist){
			p.sendMessage(ChatColor.DARK_RED + "-_-_-_-_-_-_-_" + ChatColor.LIGHT_PURPLE + ",------,");
			p.sendMessage(ChatColor.GOLD + "_-_-_-_-_-_-_-" + ChatColor.LIGHT_PURPLE + "| " + ChatColor.RED + "/ /    " + ChatColor.LIGHT_PURPLE + "|");
			p.sendMessage(ChatColor.YELLOW + "-_-_-_-_-_-_-_" + ChatColor.LIGHT_PURPLE + "| " + ChatColor.RED + "/ / " + ChatColor.GRAY + "/\\_/\\");
			p.sendMessage(ChatColor.DARK_AQUA + "_-_-_-_-_-_-_-" + ChatColor.LIGHT_PURPLE + "|____" + ChatColor.GRAY + "( ^ .^)");
			p.sendMessage(ChatColor.DARK_PURPLE + "_-_-_-_-_-_-_-" + ChatColor.GRAY + "\"   \"");
		}
	}

}
