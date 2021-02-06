package com.probgtech.lpb.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.probgtech.lpb.Paintball;

public class Items {
	
	public static Map<Integer, Item> items = new HashMap<Integer, Item>();
	ItemStack optin;
	
	public enum ItemType {
		SINGLE, RAPID, BURST, GRENADE, STICK, TELEPORT, OPTIN, OPTOUT;
	}
	
	public Items(){
		initItems();
	}
	
	private void initItems() {
		items.clear();
		for(int i = 1; i < 35; i++){
			if (Paintball.cfg.isSet("Items."+i)){
				ItemType t = ItemType.SINGLE;
				String name = ChatColor.translateAlternateColorCodes('&', Paintball.cfg.getString("Items."+i+".Name"));
				List<String> lores = Paintball.cfg.getStringList("Items."+i+".Lores");
				List<String> fin_lores = new ArrayList<String>();
				for (String s : lores){
					fin_lores.add(ChatColor.translateAlternateColorCodes('&', s));
				}
				for (ItemType t1 : ItemType.values()){
					if (Paintball.cfg.getString("Items."+i+".Type").equalsIgnoreCase(t1.toString())){
						t = t1;
					}
				}
				String material = Paintball.cfg.getString("Items."+i+".Material");
				Item item = new Item(i, name, fin_lores, t, material);
				if (Paintball.cfg.isSet("Items."+i+".Power")) item.setPower(Paintball.cfg.getInt("Items."+i+".Power"));
				if (Paintball.cfg.isSet("Items."+i+".Permission")) item.setPermission(Paintball.cfg.getString("Items."+i+".Permission"));
				if (Paintball.cfg.isSet("Items."+i+".Cooldown")) item.setCooldown(Paintball.cfg.getInt("Items."+i+".Cooldown"));
				if (Paintball.cfg.isSet("Items."+i+".Shots")) item.setShots(Paintball.cfg.getInt("Items."+i+".Shots"));
				if (Paintball.cfg.isSet("Items."+i+".Spread")) item.setSpread(Paintball.cfg.getDouble("Items."+i+".Spread"));
				if (Paintball.cfg.isSet("Items."+i+".Speed")) item.setSpeed(Paintball.cfg.getDouble("Items."+i+".Speed"));
				items.put(i, item);
				
			}
		}
		
	}

	public static ItemStack getOptIn() {
		ItemType t = ItemType.OPTIN;
		String name = ChatColor.translateAlternateColorCodes('&', Paintball.cfg.getString("Items.OPTIN.Name"));
		List<String> lores = Paintball.cfg.getStringList("Items.OPTIN.Lores");
		List<String> fin_lores = new ArrayList<String>();
		for (String s : lores){
			fin_lores.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		String material = Paintball.cfg.getString("Items.OPTIN.Material");
		Item item = new Item(1000, name, fin_lores, t, material);
		if (Paintball.cfg.isSet("Items.OPTIN.Permission")) item.setPermission(Paintball.cfg.getString("Items.OPTIN.Permission"));
		if (Paintball.cfg.isSet("Items.OPTIN.Cooldown")) item.setCooldown(Paintball.cfg.getInt("Items.OPTIN.Cooldown"));
		items.put(1000, item);
		return item.getItemStack();
	}
	
	public static ItemStack getOptOut() {
		ItemType t = ItemType.OPTOUT;
		String name = ChatColor.translateAlternateColorCodes('&', Paintball.cfg.getString("Items.OPTOUT.Name"));
		List<String> lores = Paintball.cfg.getStringList("Items.OPTOUT.Lores");
		List<String> fin_lores = new ArrayList<String>();
		for (String s : lores){
			fin_lores.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		String material = Paintball.cfg.getString("Items.OPTOUT.ID");
		Item item = new Item(1001, name, fin_lores, t, material);
		if (Paintball.cfg.isSet("Items.OPTOUT.Permission")) item.setPermission(Paintball.cfg.getString("Items.OPTOUT.Permission"));
		if (Paintball.cfg.isSet("Items.OPTOUT.Cooldown")) item.setCooldown(Paintball.cfg.getInt("Items.OPTOUT.Cooldown"));
		items.put(1001, item);
		return item.getItemStack();
	}

	public static Map<Integer, Item> getItems() {
		return items;
	}

}
