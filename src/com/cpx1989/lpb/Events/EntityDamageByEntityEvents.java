package com.cpx1989.lpb.Events;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.cpx1989.lpb.ActionBarAPI;
import com.cpx1989.lpb.Paintball;
import com.cpx1989.lpb.Utils.Item;
import com.cpx1989.lpb.Utils.Items;
import com.cpx1989.lpb.Utils.Items.ItemType;
import com.cpx1989.lpb.Utils.MessageHandler;
import com.cpx1989.lpb.Utils.PlayerHandler;

public class EntityDamageByEntityEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		Entity attacker = e.getDamager();
		Entity damaged = e.getEntity();
		if (attacker instanceof Snowball && damaged instanceof ItemFrame){
			e.setCancelled(true);
		}
		if (attacker instanceof Player && damaged instanceof Player){
			Player p = (Player) attacker;
			for (Item i : Items.getItems().values()){
				if (p.getItemInHand().equals(i.getItemStack())){
					if (i.getType() == ItemType.STICK){
						if (i.getPermission() != null && p.hasPermission(i.getPermission())){
							e.setDamage(0);
							e.setCancelled(false);
						} else {
							if (!p.hasPermission("lpb.staff")) e.setCancelled(true);
						}
					} else {
						if (!p.hasPermission("lpb.staff")) e.setCancelled(true);
					}
				} else {
					if (!p.hasPermission("lpb.staff")) e.setCancelled(true);
				}
			}
		}
		if (attacker instanceof Snowball && damaged instanceof Player && ((Snowball) attacker).getShooter() instanceof Player) {
			Player p = (Player) damaged;
			Snowball sb = (Snowball) attacker;
			Player sh = (Player) sb.getShooter();
			if (p == sh) {
				e.setCancelled(true);
				return;
			}
			if (!Paintball.instance.plist.contains(p)){
				e.setCancelled(true);
				return;
			}
			Paintball.instance.sb.addShot(sh, 1);
			if (Paintball.instance.sb.getScore(sh) > 0 && (Paintball.instance.sb.getScore(sh) % Paintball.cfg.getInt("Options.Announce")) == 0){
				MessageHandler.broadcastAPI(Paintball.instance.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&a" + sh.getName() + " &bis on a &c" + Paintball.instance.sb.getScore(sh) + " &bpoint streak!"));
			}
			if (Paintball.instance.sb.getScore(sh) == Paintball.cfg.getInt("Options.Limit")){
				Paintball.instance.sb.resetAllScores();
				MessageHandler.clearChat();
				//MessageHandler.broadcastAPI(Paintball.instance.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&a" + sh.getName() + " &bhas reached &c" + Paintball.cfg.getInt("Options.Limit") + " &bpoints! Resetting scores."));
				for (Player p1 : Paintball.instance.plist){
					p1.sendMessage(Paintball.instance.getPrefix() + ChatColor.translateAlternateColorCodes('&', "&a" + sh.getName() + " &bhas reached &c" + Paintball.cfg.getInt("Options.Limit") + " &bpoints! Resetting scores."));
					PlayerHandler.shootFireWork(p1);
				}
			}
			if (Paintball.cfg.getBoolean("Options.Paint")){
				PlayerInventory pi = p.getInventory();
				if (pi.getHelmet().getType() == Material.LEATHER_HELMET){
					LeatherArmorMeta him = (LeatherArmorMeta) pi.getHelmet().getItemMeta();
					him.setColor(getRandColor());
					pi.getHelmet().setItemMeta(him);
				}
				if (pi.getChestplate().getType() == Material.LEATHER_CHESTPLATE){
					LeatherArmorMeta cim = (LeatherArmorMeta) pi.getChestplate().getItemMeta();
					cim.setColor(getRandColor());
					pi.getChestplate().setItemMeta(cim);
				}
				if (pi.getLeggings().getType() == Material.LEATHER_LEGGINGS){
					LeatherArmorMeta lim = (LeatherArmorMeta) pi.getLeggings().getItemMeta();
					lim.setColor(getRandColor());
					pi.getLeggings().setItemMeta(lim);
				}
				if (pi.getBoots().getType() == Material.LEATHER_BOOTS){
					LeatherArmorMeta bim = (LeatherArmorMeta) pi.getBoots().getItemMeta();
					bim.setColor(getRandColor());
					pi.getBoots().setItemMeta(bim);
				}
				p.updateInventory();
				
			}
			ActionBarAPI.sendActionBar(p, Paintball.instance.getPrefix() + ChatColor.translateAlternateColorCodes('&', "You have been shot by &c"+sh.getName()));
			p.playSound(p.getLocation(), Sound.ENTITY_GHAST_AMBIENT, 1, 0);
			p.setVelocity(sb.getVelocity().multiply(2));
			Paintball.instance.sb.setZero(p);
			ActionBarAPI.sendActionBar(sh, Paintball.instance.getPrefix() + ChatColor.translateAlternateColorCodes('&', "You shot &c"+p.getName()));
			sh.playSound(sh.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 0);
			sb.remove();
			e.setCancelled(true);
		}
	}

	public Color getRandColor(){
		int randomNum = 0 + (int)(Math.random()*15);
		switch(randomNum){
			case 1:
				return Color.fromRGB(25, 25, 25);//black
			case 2:
				return Color.fromRGB(153, 51, 51);//red
			case 3:
				return Color.fromRGB(102, 127, 51);//green
			case 4:
				return Color.fromRGB(102, 76, 51);//brown
			case 5:
				return Color.fromRGB(51, 76, 178);//blue
			case 6:
				return Color.fromRGB(127, 63, 178);//purple
			case 7:
				return Color.fromRGB(76, 127, 153);//cyan
			case 8:
				return Color.fromRGB(153, 153, 153);//gray
			case 9:
				return Color.fromRGB(76, 76, 76);//dark gray
			case 10:
				return Color.fromRGB(242, 127, 165);//pink
			case 11:
				return Color.fromRGB(127, 204, 25);//lime
			case 12:
				return Color.fromRGB(229, 229, 51);//yellow
			case 13:
				return Color.fromRGB(102, 153, 216);//lightblue
			case 14:
				return Color.fromRGB(178, 76, 216);//magenta
			case 15:
				return Color.fromRGB(216, 127, 51);//orange
			default:
				return Color.fromRGB(255, 255, 255);//white
		}
	}
	
}
