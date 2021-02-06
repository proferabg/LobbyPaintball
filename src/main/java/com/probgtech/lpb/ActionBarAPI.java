package com.probgtech.lpb;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBarAPI {
	
	public static void sendActionBar(Player player, String msg){
		String message = ChatColor.translateAlternateColorCodes('&', msg);
		String nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		try {
			Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
	        Object craftPlayer = craftPlayerClass.cast(player);
	        Object packet;
	        Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
	        Class<?> packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");
	        
	        Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
	        Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
	        try {
	            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
	            Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
	            Object chatMessageType = null;
	            for (Object obj : chatMessageTypes) {
	                if (obj.toString().equals("GAME_INFO")) {
	                    chatMessageType = obj;
	                }
	            }
	            Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
	            packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass, UUID.class}).newInstance(chatCompontentText, chatMessageType, player.getUniqueId());
	        } catch (ClassNotFoundException cnfe) {
	            Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
	            packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
	        }
	        Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
	        Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
	        Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
	        Object playerConnection = playerConnectionField.get(craftPlayerHandle);
	        Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
	        sendPacketMethod.invoke(playerConnection, packet);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void sendActionBar(List<Player> players, String msg){
		for(Player p : players) sendActionBar(p, msg);
	}
	
	public static void clearActionBar(Player player){
		sendActionBar(player, "");
	}

}
