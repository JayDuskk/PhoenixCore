package me.jj9playz.phoenixcore.gui.bangui;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.Date;

public class banGUIEvents implements Listener {

    me.jj9playz.phoenixcore.main main = JavaPlugin.getPlugin(me.jj9playz.phoenixcore.main.class);

    boolean chatReason = false;

    public String banReason;

    public Player whoisban;

    public Date bantime;
    public Calendar c = Calendar.getInstance();

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String msg = e.getMessage();
        if(chatReason){
            if(msg.startsWith("/")){
                player.sendMessage(ChatColor.RED + "You Can not run a command while banning a player");
            }else{
                banReason = msg;
                main.banreson = msg;
                chatReason = false;
                e.setCancelled(true);
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        main.openDetailsGui(player);
                    }
                };
                // Run the task on this plugin in 3 seconds (60 ticks)
                task.runTaskLater(main, 1);


            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) throws InterruptedException {
        Player player = (Player) e.getWhoClicked();


        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ban GUI")){
            if(e.getCurrentItem().getType() == Material.PLAYER_HEAD){
                Player whotoban = player.getServer().getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());

                whoisban = whotoban;
                main.openDetailsGui(player);

                e.setCancelled(true);
            }
        }
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Details for Ban")){
            if(e.getCurrentItem().getType() == Material.BOOK){
                player.closeInventory();
                chatReason = true;
                player.sendMessage(main.prefix + ChatColor.GRAY + " Please give a reason for the ban");
            }
            if(e.getCurrentItem().getType() == Material.CLOCK){
                bantime = new Date();
                main.openTimeGUI(player);
            }
            if(e.getCurrentItem().getType() == Material.GREEN_BANNER){
                main.openConfirmMenu(player, whoisban);
            }

        }
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "" + ChatColor.BOLD + "Ban Duration")){
            if(e.getCurrentItem().getType() == Material.EMERALD){
                c.setTime(bantime);
                c.add(Calendar.HOUR, 1);
                bantime = c.getTime();
                e.setCancelled(true);
                main.hours += 1;
            }
            if(e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
                c.setTime(bantime);
                c.add(Calendar.DATE, 1);
                bantime = c.getTime();
                e.setCancelled(true);
                main.days += 1;
            }
            if(e.getCurrentItem().getType() == Material.CLOCK){
                c.setTime(bantime);
                c.add(Calendar.MONTH, 1);
                bantime = c.getTime();
                e.setCancelled(true);
                main.months += 1;
            }
            if(e.getCurrentItem().getType() == Material.GREEN_BANNER){
                main.openDetailsGui(player);
            }
        }
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.BOLD + "Confirm Ban")){
            String bumper = org.apache.commons.lang.StringUtils.repeat("\n", 35);
            switch(e.getCurrentItem().getType()){
                case GREEN_BANNER:
                    if(!whoisban.hasPermission("rpge.ban.avoid") || !whoisban.isOp()) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(whoisban.getName(), bumper + ChatColor.RED + "You have been banned by : " + player.getDisplayName() + "\n for the Following Reason/s: " + banReason + ChatColor.RESET + "\n", bantime, player.getDisplayName());
                        whoisban.kickPlayer(ChatColor.RED + "You have been banned from the server for: " + banReason);
                        player.sendMessage(main.prefix + ChatColor.RED + "You Have Banned : " + whoisban.getDisplayName() + " For: " + banReason);
                    }else{
                        player.sendMessage(main.prefix + ChatColor.RED + "You Can Not Ban This player.");
                        whoisban.sendMessage(main.prefix + ChatColor.RED + "You have avoided being banned\n" + player.getDisplayName() + " tried to ban you for " + banReason);
                    }
                    player.closeInventory();
                case RED_BANNER:
                    main.openBanGui(player);

            }
            e.setCancelled(true);
        }

    }
}
