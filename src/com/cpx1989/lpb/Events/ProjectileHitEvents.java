package com.cpx1989.lpb.Events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.cpx1989.lpb.BlockHandler;
import com.cpx1989.lpb.Paintball;

public class ProjectileHitEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event){
		Projectile sb = event.getEntity();
		if (sb.getShooter() instanceof Player && Paintball.cfg.getBoolean("Options.Paint") && sb instanceof Snowball){
			Player p = (Player) sb.getShooter();
			if (Paintball.instance.plist.contains(p)){
				int randomNum = 0 + (int)(Math.random()*15);
				Map<Location, BlockState> blocks = new HashMap<Location, BlockState>(Paintball.instance.blist);
				Location b1 = sb.getLocation();
				int radius = Paintball.cfg.getInt("Options.Paint_Radius");
				List<Block> block1 = BlockHandler.circle(b1, radius, radius, false, true, 0);
				
				for(Block block : block1){
		        	boolean addBlock = false;
		        	boolean setBlock = false;
		        	if (block.getType() == Material.AIR) {
		        		continue;
		        	}
		        	for (int i : Paintball.cfg.getIntegerList("Blocks")){
		        		if (block.getTypeId() == i){
		        			addBlock = true;
		        			setBlock = true;
		        		}
		        	}
		        	for(Location l : blocks.keySet()){
		        		if (l.equals(block.getLocation())){
		        			addBlock = false;
			        		Paintball.instance.tlist.put(block.getLocation().clone(), System.currentTimeMillis() / 1000L);
		        		}
		        	}
		        	if (addBlock){
		        		//ItemStack is = new ItemStack(block.getType(), block.getData());
		        		final BlockState is = block.getState();
		        		Paintball.instance.blist.put(block.getLocation().clone(), is);
		        		Paintball.instance.tlist.put(block.getLocation().clone(), System.currentTimeMillis() / 1000L);
		        	}
		        	if (setBlock){
			        	block.setType(Material.WOOL);
			        	block.setData((byte) randomNum);
		        	}
				}
			}
		}
	}

}
