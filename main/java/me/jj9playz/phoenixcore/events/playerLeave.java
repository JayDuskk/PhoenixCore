package me.jj9playz.phoenixcore.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class playerLeave implements Listener {

    me.jj9playz.phoenixcore.main main = JavaPlugin.getPlugin(me.jj9playz.phoenixcore.main.class);

    @EventHandler
    public void playerQuit(PlayerQuitEvent e){

        Player player = e.getPlayer();

        if(main.getConfig().getBoolean("core-quit-custom")){
            String quitMessage = main.getConfig().getString("quit-message");
            assert quitMessage != null;
            quitMessage = ChatColor.translateAlternateColorCodes('&', quitMessage);
            quitMessage = quitMessage.replaceAll("%player%", player.getDisplayName());


            e.setQuitMessage(quitMessage);
        }
    }
}
