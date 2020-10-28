package me.jj9playz.phoenixcore.events;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;


public class chatEvent implements Listener {

    me.jj9playz.phoenixcore.main main = JavaPlugin.getPlugin(me.jj9playz.phoenixcore.main.class);


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){

        if(main.getConfig().getBoolean("core-chat-custom")){

            Player p = e.getPlayer();
            if(main.getConfig().getBoolean("dev-chat-format")) {
                if(p.getDisplayName().equalsIgnoreCase("JJ9_Playz")){
                    String formatMsg = ChatColor.GOLD + "" + ChatColor.BOLD + "Phoenix Core Dev " + ChatColor.RESET + p.getDisplayName() + ChatColor.DARK_GRAY + " >> " + ChatColor.RESET + e.getMessage();

                    e.setFormat(formatMsg);
                }
            }

                for(int i = 0; i<main.perms.size(); i++) {
                    if (p.hasPermission(main.perms.get(i))) {
                        if(p.hasPermission("pc.chat.color")) {
                            String formatMsg = main.keys.get(main.perms.get(i));
                            assert formatMsg != null;
                            formatMsg = ChatColor.translateAlternateColorCodes('&', formatMsg);
                            formatMsg = formatMsg.replaceAll("%player%", p.getDisplayName());

                            String message = ChatColor.translateAlternateColorCodes('&', e.getMessage());
                            formatMsg = formatMsg.replaceAll("%msg%", message);
                            e.setFormat(formatMsg);
                        }else if(!p.hasPermission("pc.chat.color")){
                            String formatMsg = main.keys.get(main.perms.get(i));
                            assert formatMsg != null;
                            formatMsg = ChatColor.translateAlternateColorCodes('&', formatMsg);
                            formatMsg = formatMsg.replaceAll("%player%", p.getDisplayName());

                            formatMsg = formatMsg.replaceAll("%msg%", e.getMessage());
                            e.setFormat(formatMsg);
                        }


                    } else if(!p.hasPermission(main.perms.get(i))){
                        String formatMsg = main.getConfig().getString("chat-format");
                        assert formatMsg != null;
                        formatMsg = ChatColor.translateAlternateColorCodes('&', formatMsg);
                        formatMsg = formatMsg.replaceAll("%player%", p.getDisplayName());
                        formatMsg = formatMsg.replaceAll("%msg%", e.getMessage());

                        e.setFormat(formatMsg);
                    }
                }
        }
    }
}

