package com.cpx1989.lpb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.cpx1989.lpb.Events.EntityDamageByEntityEvents;
import com.cpx1989.lpb.Events.EntityDamageEvents;
import com.cpx1989.lpb.Events.HangingBreakByEntityEvents;
import com.cpx1989.lpb.Events.InventoryClickEvents;
import com.cpx1989.lpb.Events.PlayerDropItemEvents;
import com.cpx1989.lpb.Events.PlayerInteractEvents;
import com.cpx1989.lpb.Events.PlayerJoinEvents;
import com.cpx1989.lpb.Events.PlayerQuitEvents;
import com.cpx1989.lpb.Events.ProjectileHitEvents;
import com.cpx1989.lpb.Utils.Items;
import com.cpx1989.lpb.Utils.PlayerHandler;
import com.cpx1989.lpb.Utils.VanishHook;

public class Paintball extends JavaPlugin {
	
	static PluginDescriptionFile pluginyml;
	Logger logger;
	public static Paintball instance;
	public Board sb;
	public static FileConfiguration cfg;
	public List<Player> plist = new ArrayList<Player>();
	public Map<Location, BlockState> blist = new HashMap<Location, BlockState>();
	public Map<Location, Long> tlist = new HashMap<Location, Long>();
	
	public void onEnable() {
		//Get logger before anything else
		logger = Logger.getLogger("Minecraft");
		
		//save instance
		instance = this;
		
		//get description
		pluginyml = getDescription();

		//get config
		cfg = getConfig();
		cfg.options().copyDefaults(true);
		saveConfig();
		
		//log authors
		logger.info(getPrefix() + "Plugin by " + pluginyml.getAuthors());
		
		//register scoreboard
		sb = new Board(this);
		

		getCommand("lpb").setExecutor(new LBCommand(this));
		
		new Items();
		
		//register command listener
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EntityDamageByEntityEvents(), this);
		pm.registerEvents(new HangingBreakByEntityEvents(), this);
		pm.registerEvents(new InventoryClickEvents(), this);
		pm.registerEvents(new PlayerDropItemEvents(), this);
		pm.registerEvents(new PlayerInteractEvents(), this);
		pm.registerEvents(new PlayerJoinEvents(), this);
		pm.registerEvents(new PlayerQuitEvents(), this);
		pm.registerEvents(new ProjectileHitEvents(), this);
		pm.registerEvents(new EntityDamageEvents(), this);
		
		new BlockHandler(this);
		new VanishHook(this);
		
		for (Player p : getServer().getOnlinePlayers()){
			PlayerInventory inv = p.getInventory();
			if (!inv.contains(Items.getOptIn())){
				inv.setItem(PlayerHandler.getOptSlot(), Items.getOptIn());
			}
		}

	}

	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		for (Player p : new ArrayList<Player>(plist)){
    		PlayerHandler.resetPlayer(p);
    	}
		BlockHandler.restoreAllBlocks();
	}
	
	public String getPrefix(){
		
		return ChatColor.translateAlternateColorCodes('&', "&7[&bLobby&cPaintball&7] ");
	}
	
	public static Paintball getInstance(){
		return instance;
	}

}
