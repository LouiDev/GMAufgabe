package de.louidev.main;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Timer {
	
	static int id;
	
	static void sendError(Player p, Player p2, String nummer) {
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			
			if(player.hasPermission("admin")) {
				
				player.sendMessage("§7>>> §c§lWarnung: §r§cIn dem Inventar von §l" + p.getName() + " §r§cund von §l" + p2.getName() + " §r§cwurden 2 unterschiedliche Items mit der Seriennummer §l" + nummer + " §r§cerkannt.");
				player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 10, 10);
				
			}
			
		}
		
	}
	
	static void startTimer() {
		
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				HashMap<String, Player> seriennummern = new HashMap<>();
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					
					Inventory inv = p.getInventory();
					ItemStack[] items = inv.getContents();
					
					for(ItemStack i : items) {
						
						if(i != null) {
							
							if(i.hasItemMeta()) {
								
								ItemMeta iMeta = i.getItemMeta();
								
								if(iMeta.hasLore()) {
									
									List<String> lore = iMeta.getLore();
									
									for(String s : lore) {
										
										if(s.startsWith("§r§b§lSeriennummer: ")) {
											
											String[] args = s.split("§b");
											String nummer = args[2];
											
											if(seriennummern.containsKey(nummer)) {
												
												Player p2 = seriennummern.get(nummer);
												
												//Duplikat erkannt
												sendError(p, p2, nummer);
												
											} else {
												
												seriennummern.put(nummer, p);
												
											}
											
										}
										
									}
									
								}
								
							}
						}
						
					}
					
				}
				
			}
		}, 0, 20 * 60 * 10); //Alle 10min
		
	}
	
	static void endTimer() {
		Bukkit.getScheduler().cancelTask(id);
	}
	
}
