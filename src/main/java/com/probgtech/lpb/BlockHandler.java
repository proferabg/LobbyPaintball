package com.probgtech.lpb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import com.probgtech.lpb.Utils.PlayerHandler;

public class BlockHandler {

	Paintball p;
	
	public BlockHandler(Paintball paintball) {
		p = paintball;
		startTimer();
	}
	
	private void startTimer() {
		p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable() {
			public void run() {
				Map<Location, BlockState> blocks = new HashMap<Location, BlockState>(p.blist);
				Map<Location, Long> times = new HashMap<Location, Long>(Paintball.instance.tlist);
				for(Entry<Location, BlockState> entry  : blocks.entrySet()){
					Location l = entry.getKey();
					if (PlayerHandler.getDiff(times.get(l)) >= Paintball.cfg.getInt("Options.Regen_Time")){
						BlockState is = entry.getValue();
						is.update(true);
						p.blist.remove(l);
						p.tlist.remove(l);
					}
					
				}
			}
		}, 0, 20L);
	}

	public static void restoreAllBlocks() {
		Map<Location, BlockState> blocks = new HashMap<Location, BlockState>(Paintball.instance.blist);
		for(Entry<Location, BlockState> entry  : blocks.entrySet()){
			Location l = entry.getKey();
			BlockState is = entry.getValue();
			is.update(true);
			Paintball.instance.blist.remove(l);
		}
	}
	
	public static List<Block> circle(Location loc, int radius, int height, boolean hollow, boolean sphere, int plusY){
        List<Block> circleblocks = new ArrayList<Block>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
 
        for(int x = cx - radius; x <= cx + radius; x++){
            for (int z = cz - radius; z <= cz + radius; z++){
                for(int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++){
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
 
                    if(dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))){
                        Location l = new Location(loc.getWorld(), x, y + plusY, z);
                        circleblocks.add(l.getBlock());
                    }
                }
            }
        }
 
        return circleblocks;
    }
}
