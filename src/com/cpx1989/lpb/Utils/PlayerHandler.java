package com.cpx1989.lpb.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import com.cpx1989.lpb.Paintball;
import com.cpx1989.lpb.TitleAPI;

public class PlayerHandler {
	
	public static Map<Player, ItemStack[]> invs = new HashMap<Player, ItemStack[]>();
	
	public static Map<Player, Map<Integer, Long>> times = new HashMap<Player, Map<Integer, Long>>();
	
	
	@SuppressWarnings("deprecation")
	public static void leaveGame(Player p){
		PlayerInventory inv = p.getInventory();
		Paintball.instance.plist.remove(p);
		for (Item i :Items.items.values()){
			inv.remove(i.getItemStack());
		}
		inv.setHelmet(new ItemStack(Material.AIR));
		inv.setChestplate(new ItemStack(Material.AIR));
		inv.setLeggings(new ItemStack(Material.AIR));
		inv.setBoots(new ItemStack(Material.AIR));
		inv.clear();
		if(invs.containsKey(p)){
			p.getInventory().setContents(invs.get(p));
			invs.remove(p);
		}
		inv.setItem(PlayerHandler.getOptSlot(), Items.getOptIn());
		Paintball.instance.sb.getScoreboard().resetScores(p);
		p.setScoreboard(Paintball.instance.getServer().getScoreboardManager().getNewScoreboard());
		p.setGameMode(GameMode.ADVENTURE);
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void preReloadCommand(Player p){
		PlayerInventory inv = p.getInventory();
		for (Item i :Items.items.values()){
			inv.remove(i.getItemStack());
		}
		inv.setHelmet(new ItemStack(Material.AIR));
		inv.setChestplate(new ItemStack(Material.AIR));
		inv.setLeggings(new ItemStack(Material.AIR));
		inv.setBoots(new ItemStack(Material.AIR));
		inv.clear();
		Paintball.instance.sb.getScoreboard().resetScores(p);
		p.setScoreboard(Paintball.instance.getServer().getScoreboardManager().getNewScoreboard());
		p.setGameMode(GameMode.ADVENTURE);
		p.updateInventory();
	}
	
	public static void postReloadCommand(Player p){
		PlayerInventory inv = p.getInventory();
		if(invs.containsKey(p)){
			p.getInventory().setContents(invs.get(p));
			invs.remove(p);
		}
		inv.setItem(PlayerHandler.getOptSlot(), Items.getOptIn());
		p.updateInventory();
	}
	
	public static void joinGame(Player p){
		TitleAPI.setTitle(p, ChatColor.GOLD + "Welcome to Paintball", ChatColor.DARK_AQUA + "Made for TownCraft.us", 1, 3, 1);
		PlayerInventory inv = p.getInventory();
		inv.remove(Items.getOptIn());
		invs.put(p, inv.getContents());
		inv.clear();
		Paintball.instance.plist.add(p);
		for (int i = 0; i < 35; i++){
			if (Paintball.cfg.isSet("Layout."+i)){
				if(!Paintball.cfg.getString("Layout."+i).equalsIgnoreCase("optin")){
					int i1 = Paintball.cfg.getInt("Layout."+i);
					if (Items.getItems().get(i1).permission != null){
						if (p.hasPermission(Items.getItems().get(i1).permission)){
							inv.setItem(i, Items.getItems().get(i1).getItemStack());
						}
					} else {
						inv.setItem(i, Items.getItems().get(i1).getItemStack());
					}
				}
			}
		}
		inv.setHelmet(new ItemStack(Material.LEATHER_HELMET));
		inv.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		inv.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		inv.setBoots(new ItemStack(Material.LEATHER_BOOTS));
		inv.setItem(PlayerHandler.getOptSlot(), Items.getOptOut());
		Paintball.instance.sb.addPlayer(p);
		p.setScoreboard(Paintball.instance.sb.getScoreboard());
		p.setGameMode(GameMode.ADVENTURE);
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void resetPlayer(Player p) {
		PlayerInventory inv = p.getInventory();
		Paintball.instance.plist.remove(p);
		for (Item i :Items.items.values()){
			inv.remove(i.getItemStack());
		}
		inv.setHelmet(new ItemStack(Material.AIR));
		inv.setChestplate(new ItemStack(Material.AIR));
		inv.setLeggings(new ItemStack(Material.AIR));
		inv.setBoots(new ItemStack(Material.AIR));
		inv.clear();
		if(invs.containsKey(p)){
			p.getInventory().setContents(invs.get(p));
			invs.remove(p);
		}
		Paintball.instance.sb.getScoreboard().resetScores(p);
		p.setScoreboard(Paintball.instance.getServer().getScoreboardManager().getNewScoreboard());
		p.setGameMode(GameMode.ADVENTURE);
		p.updateInventory();
	}
	
	public static int getOptSlot(){
		for (int i = 0; i < 9; i++){
			if (Paintball.cfg.isSet("Layout"+i)){
				if(Paintball.cfg.getString("Layout"+i).equalsIgnoreCase("optin")){
					return i;
				}
			}
		}
		return 8;
	}
	
	public static void single(Player p, int power) {
		Snowball sb = (Snowball) p.getWorld().spawn(p.getLocation().add(0, 1.3, 0), Snowball.class);
		sb.setShooter(p);
		sb.setVelocity(p.getLocation().getDirection().normalize().multiply(power));
	}
	
	public static void burst(Player p, int power, int num, double spread){
		Random r = new Random();
		for (int i = 0;i <= num;i++){
			Snowball sb = (Snowball) p.getWorld().spawn(p.getLocation().add(0, 1.3, 0), Snowball.class);
			sb.setShooter(p);
			if(r.nextInt(6) + 1 == 1) sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(-r.nextDouble()*spread, r.nextDouble()*spread, r.nextDouble()*spread)));
			else if (r.nextInt(6) + 1 == 2) sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(r.nextDouble()*spread, -r.nextDouble()*spread, r.nextDouble()*spread)));
			else if (r.nextInt(6) + 1 == 3) sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(r.nextDouble()*spread, r.nextDouble()*spread, -r.nextDouble()*spread)));
			else if (r.nextInt(6) + 1 == 4) sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(r.nextDouble()*spread, r.nextDouble()*spread, r.nextDouble()*spread)));
			else if (r.nextInt(6) + 1 == 5) sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(-r.nextDouble()*spread, -r.nextDouble()*spread, -r.nextDouble()*spread)));
			else if (r.nextInt(6) + 1 == 6) sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(r.nextDouble()*spread, -r.nextDouble()*spread, -r.nextDouble()*spread)));
			else if (r.nextInt(6) + 1 == 5) sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(-r.nextDouble()*spread, -r.nextDouble()*spread, r.nextDouble()*spread)));
			else if (r.nextInt(6) + 1 == 7) sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(-r.nextDouble()*spread, r.nextDouble()*spread, -r.nextDouble()*spread)));
			else sb.setVelocity(p.getLocation().getDirection().normalize().multiply(2).add(new Vector(r.nextDouble()*spread, 0, r.nextDouble()*spread)));
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void rapid(final Player p, final int power, int num, double speed){
		final int pid = Paintball.instance.getServer().getScheduler().scheduleSyncRepeatingTask(Paintball.instance, new Runnable() {
			public void run() {
			    single(p, power);
			}
		}, 0L, (long) (speed*20L));
		
		Paintball.instance.getServer().getScheduler().scheduleAsyncDelayedTask(Paintball.instance, new Runnable() {
			public void run() {
			    Paintball.instance.getServer().getScheduler().cancelTask(pid);
			}
		}, (long) (num*speed*20L));
		
	}
	
	

	public static long getDiff(Long l){
		return (System.currentTimeMillis() / 1000L) - l;
	}
	
	public static void shootFireWork(Player p) {
        Random r = new Random();
        int rt = r.nextInt(1) + 1;
        Type type = Type.BALL_LARGE;   
        if (rt == 1) type = Type.BALL;
        if (rt == 2) type = Type.BALL_LARGE;
        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);
        shootFireWork(p, type, c1, c2, r.nextBoolean(), r.nextBoolean());
	}
	
	public static void shootFireWork(Player player, Type type, Color c1, Color c2, boolean flicker, boolean trail) {
		Firework arr = (Firework) player.getWorld().spawn(player.getLocation().add(0, 1, 0), Firework.class);
        FireworkMeta fwm = arr.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder().flicker(flicker).withColor(c1).withFade(c2).with(type).trail(trail).build();
        fwm.addEffect(effect);
        fwm.setPower(0);
        arr.setFireworkMeta(fwm);
        arr.setVelocity(new Vector(0, 1, 0));
	}
	
	public static Color getColor(int i) {
        Color c = null;
        if(i==1) c=Color.AQUA;
        if(i==2) c=Color.BLACK;
        if(i==3) c=Color.BLUE;
        if(i==4) c=Color.FUCHSIA;
        if(i==5) c=Color.GRAY;
        if(i==6) c=Color.GREEN;
        if(i==7) c=Color.LIME;
        if(i==8) c=Color.MAROON;
        if(i==9) c=Color.NAVY;
        if(i==10) c=Color.OLIVE;
        if(i==11) c=Color.ORANGE;
        if(i==12) c=Color.PURPLE;
        if(i==13) c=Color.RED;
        if(i==14) c=Color.SILVER;
        if(i==15) c=Color.TEAL;
        if(i==16) c=Color.WHITE;
        if(i==17) c=Color.YELLOW;
        return c;
    }
	
	public void shoot(Player p){
		if (true){
			
		}
	}

	


}
