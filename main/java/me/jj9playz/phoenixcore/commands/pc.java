package me.jj9playz.phoenixcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class pc implements CommandExecutor {

    me.jj9playz.phoenixcore.main main = JavaPlugin.getPlugin(me.jj9playz.phoenixcore.main.class);




    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;


        if(cmd.getName().equalsIgnoreCase("pc")){
            if(sender instanceof Player){
                if(args.length > 0){
                    if(args[0].equalsIgnoreCase("ban")) {
                            main.openBanGui(player);
                            return true;
                    }
                    if(args[0].equalsIgnoreCase("vanish")){
                        if(player.hasPermission("pc.vanish")){
                            if(main.invisible_players.contains(player)){
                                for(Player people : Bukkit.getOnlinePlayers()) {
                                    people.showPlayer(main, player);
                                }
                                main.invisible_players.remove(player);
                                player.sendMessage(main.prefix + "You are Now Visible to other players on the server");
                                return true;
                            }else if(!main.invisible_players.contains(player)){
                                for(Player people : Bukkit.getOnlinePlayers()) {
                                    if(!people.hasPermission("pc.vanish.see")) {
                                        people.hidePlayer(main, player);

                                    }
                                }
                                main.invisible_players.add(player);
                                player.sendMessage(main.prefix + ChatColor.RESET + "You are Now Invisible to other players on the server");
                                return true;
                            }
                        }
                    }
                    if(args[0].equalsIgnoreCase("admin")){
                        if(player.hasPermission("pc.admin")){
                            main.openAdminGUI(player);
                        }
                    }
                }
            }
        }

        return true;
    }
}
