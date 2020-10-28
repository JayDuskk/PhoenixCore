package me.jj9playz.phoenixcore.gui.admingui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class adminGUIEvents implements Listener {

    me.jj9playz.phoenixcore.main main = JavaPlugin.getPlugin(me.jj9playz.phoenixcore.main.class);

    @EventHandler
    public void onClick(InventoryClickEvent e) throws InterruptedException {
        Player player = (Player) e.getWhoClicked();


        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Admin GUI")) {
            if (e.getCurrentItem().getType() == Material.BARRIER) {
                main.openBanGui(player);
                e.setCancelled(true);
            }
            if(e.getCurrentItem().getType() == Material.GLASS) {
                if (main.invisible_players.contains(player)) {
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        people.showPlayer(main, player);
                    }
                    main.invisible_players.remove(player);
                    player.sendMessage(main.prefix + "You are Now Visible to other players on the server");
                    e.setCancelled(true);
                    player.closeInventory();
                } else if (!main.invisible_players.contains(player)) {
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        if (!people.hasPermission("pc.vanish.see")) {
                            people.hidePlayer(main, player);

                        }
                    }
                    main.invisible_players.add(player);
                    player.sendMessage(main.prefix + ChatColor.RESET + "You are Now Invisible to other players on the server");
                    e.setCancelled(true);
                    player.closeInventory();
                }
            }
            if(e.getCurrentItem().getType() == Material.RED_BANNER){
                e.setCancelled(true);
                player.closeInventory();
            }
        }
    }
}
