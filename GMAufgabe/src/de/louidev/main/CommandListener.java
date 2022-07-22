package de.louidev.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandListener implements CommandExecutor  {
	
	FileConfiguration config = Main.getPlugin().getConfig();
	
	String getRandomSeriennummer(int length) {
		
		String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+-=?/";
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i != length; i++) {
			
			Random rnd = new Random();
			int x = rnd.nextInt(abc.length());
			
			sb.append(abc.charAt(x));
			
		}
		
		return sb.toString();
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(args.length == 0) {
				
				if(!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
					
					ItemStack i = p.getInventory().getItemInMainHand();
					ItemMeta meta = i.getItemMeta();
					
					if(i.getAmount() == 1) {
						
						if(meta.hasLore()) {
							List<String> lore = meta.getLore();
							
							for(String s : lore) {
								if(s.startsWith("§r§b§lSeriennummer: ")) {
									p.sendMessage("§c>>> §7Dieses Item hat bereits eine Seriennummer.");
									p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 10, 10);
									return false;
								}
							}
						}
						
						String nummer = getRandomSeriennummer(10);
						
						List<String> newLore = new ArrayList<String>();
						newLore.add("§r§b§lSeriennummer: §r§b" + nummer);
						meta.setLore(newLore);
						i.setItemMeta(meta);
						
						FileConfiguration config = Main.getPlugin().getConfig();
						config.set(p.getUniqueId() + "." + nummer, i);
						Main.getPlugin().saveConfig();
						
						p.sendMessage("§aErfolg! Dem Item wurde eine Seriennummer hinzugefügt!");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 10);
						
						
					} else {
						p.sendMessage("§c>>> §7Du kannst nur einzelnen Items eine Seriennummer geben!");
						p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 10, 10);
						
					}
					
				} else {
					p.sendMessage("§c>>> §7Bitte halte das Item in deiner Hand, welchem du eine Seriennummer hinzufügen willst.");
					p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 10, 10);
				}
						
			} else {
						
				p.sendMessage("§c>>> §7Bitte benutze §l/seriennummer§r§7.");
				p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 10, 10);
						
			}
			
		}
		
		return false;
	}
	
	
	
}
