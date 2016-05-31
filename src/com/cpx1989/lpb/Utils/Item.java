package com.cpx1989.lpb.Utils;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.cpx1989.lpb.Utils.Items.ItemType;

public class Item {
	
	int index;
	String name;
	List<String> lores;
	ItemType type;
	int id;
	byte data;
	int power;
	int cooldown;
	String permission;
	double spread;
	int shots;
	double speed;
	
	
	
	public Item(int index, String name, List<String> lores, ItemType type, int id){
		this.index = index;
		this.name = name;
		this.lores = lores;
		this.type = type;
		this.id = id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public void setData(byte data){
		this.data = data;
	}
	
	public void setPower(int power){
		this.power = power;
	}
	
	public void setCooldown(int cooldown){
		this.cooldown = cooldown;
	}
	
	public void setPermission(String permission){
		this.permission = permission;
	}
	
	public void setType(ItemType type){
		this.type = type;
	}
	
	public void setLores(List<String> lores){
		this.lores = lores;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public void setSpread(double spread){
		this.spread = spread;
	}
	
	public void setShots(int shots){
		this.shots = shots;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public int getID(){
		return this.id;
	}
	
	public byte getData(){
		return this.data;
	}
	
	public int getPower(){
		return this.power;
	}
	
	public int getCooldown(){
		return this.cooldown;
	}
	
	public String getPermission(){
		return this.permission;
	}
	
	public ItemType getType(){
		return this.type;
	}
	
	public List<String> getLores(){
		return this.lores;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public double getSpread(){
		return this.spread;
	}
	
	public int getShots(){
		return this.shots;
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getItemStack(){
		ItemStack is = new ItemStack(this.id);
		if (this.data > -1){
			MaterialData md = is.getData();
			md.setData(this.data);
			is.setData(md);
		}
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(this.name);
		im.setLore(this.lores);
		is.setItemMeta(im);
		if(this.type == ItemType.STICK){
			is.addUnsafeEnchantment(Enchantment.KNOCKBACK, this.power);
		}
		return is;
	}
	
	

}
