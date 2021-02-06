package com.probgtech.lpb.Events;

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

import com.probgtech.lpb.BlockHandler;
import com.probgtech.lpb.Paintball;

public class ProjectileHitEvents implements Listener {
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event){
		Projectile sb = event.getEntity();
		if (sb.getShooter() instanceof Player && Paintball.cfg.getBoolean("Options.Paint") && sb instanceof Snowball){
			Player p = (Player) sb.getShooter();
			if (Paintball.instance.plist.contains(p)){
				Material m = GetRandomWoolMaterial();
				Map<Location, BlockState> blocks = new HashMap<Location, BlockState>(Paintball.instance.blist);
				Location b1 = sb.getLocation();
				int radius = Paintball.cfg.getInt("Options.Paint_Radius");
				boolean PerBlock = Paintball.cfg.getBoolean("Options.RandomPerBlock");
				List<Block> block1 = BlockHandler.circle(b1, radius, radius, false, true, 0);
				
				for(Block block : block1){
		        	boolean addBlock = false;
		        	boolean setBlock = false;
		        	if (block.getType() == Material.AIR) {
		        		continue;
		        	}
		        	List<String> Blocks = Paintball.cfg.getStringList("Blocks");
		        	if (Blocks.contains(block.getType().toString())){
		        		addBlock = true;
		        		setBlock = true;
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
			        	BlockState bs = block.getState();
			        	bs.setType(PerBlock ? GetRandomWoolMaterial() : m);
			        	bs.update(true, true);
		        	}
				}
			}
		}
	}
	
	public Material GetRandomWoolMaterial() {
		int Random = (int)(Math.random()*15);
		switch(Random) {
		case 0:
			return Material.WHITE_WOOL;
		case 1:
			return Material.ORANGE_WOOL;
		case 2:
			return Material.MAGENTA_WOOL;
		case 3:
			return Material.LIGHT_BLUE_WOOL;
		case 4:
			return Material.YELLOW_WOOL;
		case 5:
			return Material.LIME_WOOL;
		case 6:
			return Material.PINK_WOOL;
		case 7:
			return Material.GRAY_WOOL;
		case 8:
			return Material.LIGHT_GRAY_WOOL;
		case 9:
			return Material.CYAN_WOOL;
		case 10:
			return Material.PURPLE_WOOL;
		case 11:
			return Material.BLUE_WOOL;
		case 12:
			return Material.BROWN_WOOL;
		case 13:
			return Material.GREEN_WOOL;
		case 14:
			return Material.RED_WOOL;
		case 15:
		default:
			return Material.BLACK_WOOL;
		}
	}
}
