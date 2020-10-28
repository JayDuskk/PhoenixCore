package me.jj9playz.phoenixcore.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

public class playerJoin implements Listener {

    me.jj9playz.phoenixcore.main main = JavaPlugin.getPlugin(me.jj9playz.phoenixcore.main.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        for(int i = 0; i < main.invisible_players.size(); i++){
            if(!player.hasPermission("pc.vanish.see")) {
                player.hidePlayer(main, main.invisible_players.get(i));
            }
        }

        if(main.getConfig().getBoolean("core-messages")){
            String coreMessage = ChatColor.BLACK + "==========================================\n"
                    + ChatColor.GOLD + "" + ChatColor.BOLD  + "Phoenix Core\n"
                    + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "Phoenix Core is the core plugin for the many extensions.\n"
                    + "All extensions are free so please consider donating."
                    + ChatColor.WHITE + "This message can be disabled in config.yml"
                    + ChatColor.BLACK + "\n==========================================\n";
            player.sendMessage(coreMessage);
        }

        if(main.getConfig().getBoolean("core-join-custom")) {
            String joinMessage = main.getConfig().getString("join-message");
            assert joinMessage != null;
            joinMessage = ChatColor.translateAlternateColorCodes('&', joinMessage);
            joinMessage = joinMessage.replaceAll("%player%", player.getDisplayName());


            e.setJoinMessage(joinMessage);
        }

        PermissionAttachment attachment = player.addAttachment(main);
        main.permsAttach.put(player.getUniqueId(), attachment);

    }
}
