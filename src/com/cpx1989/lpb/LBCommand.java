package com.cpx1989.lpb;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.cpx1989.lpb.Utils.Items;
import com.cpx1989.lpb.Utils.PlayerHandler;


public class LBCommand implements CommandExecutor {
	
	private Paintball plugin;
	public LBCommand(Paintball p) {
		plugin = p;
		
	}

	@Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		if (args.length == 0){
    		sender.sendMessage(plugin.getPrefix() + "Invalid arguments try " + ChatColor.RED + " /lpb help");
    	}
    	else if (args.length == 1){
    		if (args[0].equalsIgnoreCase("help"))
    		{	
    			sender.sendMessage(ChatColor.GRAY + " ------ " + ChatColor.RED + "LobbyPaintball" + ChatColor.GRAY + " ------ ");
    			sender.sendMessage(ChatColor.RED + "/lpb help" + ChatColor.GRAY + " - Shows all available commands!");
    			if(sender.hasPermission("lpb.reload")){
    				sender.sendMessage(ChatColor.RED + "/lpb reload" + ChatColor.GRAY + " - Reloads the plugin!");
    			}
    			sender.sendMessage(ChatColor.RED + "/lpb about" + ChatColor.GRAY + " - Shows CPx1989's bragging rights!");
    		}
    		else if (args[0].equalsIgnoreCase("about"))
    		{
    			sender.sendMessage(ChatColor.GRAY + " ------ " + ChatColor.RED + "LobbyPaintball" + ChatColor.GRAY + " ------ ");
    			sender.sendMessage(ChatColor.RED + "LobbyPaintball v" + plugin.getDescription().getVersion() + ChatColor.GRAY + " by CPx1989");
        		sender.sendMessage(ChatColor.GRAY + "Made for the hub at:");
        		sender.sendMessage(ChatColor.DARK_GREEN + "    TownCraft" + ChatColor.GRAY + "." + ChatColor.GOLD + "us");
    		}
    		else if (args[0].equalsIgnoreCase("reload")){
    			if(sender instanceof Player) {
            		Player play = (Player)sender;
    			    if(play.hasPermission("lpb.reload")){
    			    	for (Player p : new ArrayList<Player>(plugin.plist)){
    			    		PlayerHandler.preReloadCommand(p);
    			    	}
    			    	Paintball.cfg = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        				new Items();
    			    	sender.sendMessage(plugin.getPrefix() + "Config Reloaded!");
    			    	for (Player p : new ArrayList<Player>(plugin.plist)){
    			    		PlayerHandler.postReloadCommand(p);
    			    	}
    			    	plugin.plist.clear();
    			    }
    			    else{
    			    	sender.sendMessage(plugin.getPrefix() + ChatColor.RED + "You do not have permission!");
    			    }
    			}
    			else {
    				for (Player p : new ArrayList<Player>(plugin.plist)){
    					PlayerHandler.preReloadCommand(p);
			    	}
			    	Paintball.cfg = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    				new Items();
			    	sender.sendMessage(plugin.getPrefix() + "Config Reloaded!");
			    	for (Player p : new ArrayList<Player>(plugin.plist)){
			    		PlayerHandler.postReloadCommand(p);
			    	}
			    	plugin.plist.clear();
    			}
    		}
    		else {
    			sender.sendMessage(plugin.getPrefix() + "Check your arguments or do " + ChatColor.RED + "/lpb help");
    		}
    	}
		return false;
    }
}
