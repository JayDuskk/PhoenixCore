package me.jj9playz.phoenixcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class phoenix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("phoenix")){

            String help = ChatColor.BLACK + "==========================================\n"
                    + ChatColor.GOLD + "" + ChatColor.BOLD  + "Phoenix Core\n"
                    + ChatColor.RESET + ChatColor.GRAY + "/phoenix " + ChatColor.WHITE + ": Shows all commands"
                    + ChatColor.GRAY + "/pc " + ChatColor.GRAY + ": Main Command for Phoenix Core"
                    + ChatColor.BLACK + "\n==========================================\n";
            player.sendMessage(help);

        }

        return true;
    }
}
