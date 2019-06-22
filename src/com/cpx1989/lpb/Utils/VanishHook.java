package com.cpx1989.lpb.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.kitteh.vanish.VanishPlugin;

public class VanishHook {
	
	public static boolean isHooked = false;
    private static VanishPlugin dependency;

	public VanishHook(Plugin p) {
		if (dependency == null && !isHooked) {
            try {
                dependency = (VanishPlugin) Bukkit.getPluginManager().getPlugin("VanishNoPacket");

                if (dependency != null && dependency.isEnabled()) {
                    isHooked = true;
                	p.getLogger().info("Hooked into VanishNoPacket!");
                }
            } catch (Exception e) {
            	p.getLogger().info("Could not hook into VanishNoPacket!");
            }
        }
	}

	public static boolean isVanished(Player player) {
		return isVanished(player.getName());
	}

	public static boolean isVanished(String player) {
		return isHooked && dependency.getManager().isVanished(player);
	}
}
