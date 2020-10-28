package me.jj9playz.phoenixcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class pcTabComplete implements TabCompleter {

    List<String> arguments = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(arguments.isEmpty()){
            arguments.add("ban");
            arguments.add("vanish");
            arguments.add("race");
        }
        List<String> result = new ArrayList<>();
        if(args.length==1){
            for(String a : arguments){
                if(a.toLowerCase().startsWith(args[0].toLowerCase())){
                    result.add(a);

                }
                return result;
            }
        }

        return arguments;
    }
}
