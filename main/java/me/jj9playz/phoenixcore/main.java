package me.jj9playz.phoenixcore;

import me.jj9playz.phoenixcore.commands.pc;
import me.jj9playz.phoenixcore.commands.pcTabComplete;
import me.jj9playz.phoenixcore.commands.phoenix;
import me.jj9playz.phoenixcore.events.chatEvent;
import me.jj9playz.phoenixcore.events.playerJoin;
import me.jj9playz.phoenixcore.events.playerLeave;
import me.jj9playz.phoenixcore.gui.admingui.adminGUIEvents;
import me.jj9playz.phoenixcore.gui.bangui.banGUIEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class main extends JavaPlugin {

    public String banreson;
    public int hours;
    public int days;
    public int months;
    public String desc;
    public String def;

    public String prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("prefix")));

    public ArrayList<Player> invisible_players = new ArrayList<>();

    public PluginManager pm = getServer().getPluginManager();

    public String perm;
    public String key;
    public ArrayList<String> perms = new ArrayList<>();
    public HashMap<String, String> keys = new HashMap<>();

    public HashMap<UUID, PermissionAttachment> permsAttach = new HashMap<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "Phoenix Core has been enabled.");

        getServer().getPluginManager().registerEvents(new playerJoin(), this);
        getServer().getPluginManager().registerEvents(new playerLeave(), this);
        getServer().getPluginManager().registerEvents(new chatEvent(), this);
        getServer().getPluginManager().registerEvents(new banGUIEvents(), this);
        getServer().getPluginManager().registerEvents(new adminGUIEvents(), this);



        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("phoenix")).setExecutor(new phoenix());
        Objects.requireNonNull(getCommand("pc")).setExecutor(new pc());
        Objects.requireNonNull(getCommand("pc")).setTabCompleter(new pcTabComplete());

        for(String s : Objects.requireNonNull(getConfig().getConfigurationSection("chat-formats")).getKeys(false)) {

            Set<Permission> permissions = pm.getPermissions();

            perm = getConfig().getString("chat-formats." + s + ".permission.name");
            key = getConfig().getString("chat-formats." + s + ".format");
            desc = getConfig().getString("chat-formats." + s + ".permission.description");
            def = getConfig().getString("chat-formats." + s + ".permission.default");
            if(!perms.contains(perm)) {
                perms.add(perm);
            }
            if(!keys.containsKey(perm)) {
                keys.put(perm, key);
            }

            assert def != null;
            Permission permis = new Permission(perm, desc, PermissionDefault.getByName(def));

            if (!permissions.contains(permis)) {
                pm.addPermission(permis);
            }


        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "Phoenix Core has been disabled.");
    }



    public void openBanGui(Player player){
        ArrayList<Player> player_list = new ArrayList<>(player.getServer().getOnlinePlayers());

        Inventory banGui = Bukkit.createInventory(player, 45, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ban GUI");

        for(int i = 0; i < player_list.size();i++){
            ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD,1);
            SkullMeta smeta = (SkullMeta) playerhead.getItemMeta();
            smeta.setOwningPlayer(player_list.get(i));
            smeta.setDisplayName(player_list.get(i).getDisplayName());
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY + "Player Health:" + player_list.get(i).getHealth());
            lore.add(ChatColor.GRAY + "XP: " + player_list.get(i).getExp());
            smeta.setLore(lore);
            playerhead.setItemMeta(smeta);

            banGui.addItem(playerhead);
        }

        player.openInventory(banGui);
    }

    public void openDetailsGui(Player player){

        Inventory detailsGUI = Bukkit.createInventory(player, 9, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Details for Ban");


        //Reason for Ban
        ItemStack reason = new ItemStack(Material.BOOK);
        ItemMeta reason_meta = reason.getItemMeta();
        reason_meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Reason for Ban");
        ArrayList<String> reason_lore = new ArrayList<>();
        reason_lore.add(ChatColor.DARK_GRAY + "Reason: " + banreson);
        reason_meta.setLore(reason_lore);
        reason.setItemMeta(reason_meta);
        detailsGUI.setItem(1, reason);

        //Time of ban
        ItemStack time = new ItemStack(Material.CLOCK);
        ItemMeta tm = time.getItemMeta();
        tm.setDisplayName(ChatColor.AQUA + "Ban Duration");
        ArrayList<String> tlore = new ArrayList<>();
        tlore.add(ChatColor.DARK_GRAY + "Time: " + months + "M, " + days + "D, "+ hours + "H");
        tm.setLore(tlore);
        time.setItemMeta(tm);
        detailsGUI.setItem(4, time);

        //Confirm
        ItemStack confirm = new ItemStack(Material.GREEN_BANNER);
        ItemMeta confirm_meta = reason.getItemMeta();
        confirm_meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Confirm Details");
        ArrayList<String> clore = new ArrayList<>();
        clore.add(ChatColor.DARK_GRAY + "Reason: " + banreson);
        clore.add(ChatColor.DARK_GRAY + "Time: " + months + "M, " + days + "D, "+ hours + "H");
        confirm_meta.setLore(clore);
        confirm.setItemMeta(confirm_meta);
        detailsGUI.setItem(7, confirm);


        //Empty Squares
        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta em = empty.getItemMeta();
        em.setDisplayName(" ");
        empty.setItemMeta(em);
        detailsGUI.setItem(0, empty);
        detailsGUI.setItem(2, empty);
        detailsGUI.setItem(3, empty);
        detailsGUI.setItem(5, empty);
        detailsGUI.setItem(6, empty);
        detailsGUI.setItem(8, empty);

        player.openInventory(detailsGUI);
    }

    public void openConfirmMenu(Player player, Player playerToBan){
        Inventory confirmGUI = Bukkit.createInventory(player, 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Confirm Ban");

        //Ban Options
        ItemStack ban = new ItemStack(Material.GREEN_BANNER);
        ItemMeta ban_meta = ban.getItemMeta();
        ban_meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Confirm");
        ban.setItemMeta(ban_meta);
        confirmGUI.setItem(1, ban);

        //Player they are Banning
        ItemStack player_head = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta ph_meta = player_head.getItemMeta();
        ph_meta.setDisplayName(playerToBan.getDisplayName());
        ArrayList<String> ph_lore = new ArrayList<>();
        ph_lore.add(ChatColor.DARK_GRAY + "REASON: " + banreson);
        ph_lore.add(ChatColor.DARK_GRAY + "TIME: " + months + "Months, " + days + "Days, "+ hours + "Hours");
        ph_meta.setLore(ph_lore);
        player_head.setItemMeta(ph_meta);
        confirmGUI.setItem(4, player_head);

        //Player they are Banning
        ItemStack cancel = new ItemStack(Material.RED_BANNER);
        ItemMeta cancel_meta = cancel.getItemMeta();
        cancel_meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Cancel");
        cancel.setItemMeta(cancel_meta);
        confirmGUI.setItem(7, cancel);

        //Empty Squares
        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta em = empty.getItemMeta();
        em.setDisplayName(" ");
        empty.setItemMeta(em);
        confirmGUI.setItem(0, empty);
        confirmGUI.setItem(2, empty);
        confirmGUI.setItem(3, empty);
        confirmGUI.setItem(5, empty);
        confirmGUI.setItem(6, empty);
        confirmGUI.setItem(8, empty);

        player.openInventory(confirmGUI);

    }
    public void openTimeGUI(Player player){
        Inventory timeGUI = Bukkit.createInventory(player, 9, ChatColor.AQUA + "" + ChatColor.BOLD + "Ban Duration");

        ItemStack min = new ItemStack(Material.EMERALD_ORE);
        ItemMeta mim = min.getItemMeta();
        mim.setDisplayName(ChatColor.GREEN + "Add: 1 Minute");
        min.setItemMeta(mim);
        timeGUI.setItem(2, min);

        ItemStack hour = new ItemStack(Material.EMERALD);
        ItemMeta hm = hour.getItemMeta();
        hm.setDisplayName(ChatColor.GREEN + "Add: 1 Hour");
        hour.setItemMeta(hm);
        timeGUI.setItem(3, hour);

        ItemStack day = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta dm = day.getItemMeta();
        dm.setDisplayName(ChatColor.GREEN + "Add: 1 Day");
        day.setItemMeta(dm);
        timeGUI.setItem(4, day);

        ItemStack month = new ItemStack(Material.CLOCK);
        ItemMeta mm = day.getItemMeta();
        mm.setDisplayName(ChatColor.GREEN + "Add: 1 Month");
        month.setItemMeta(mm);
        timeGUI.setItem(5, month);

        ItemStack year = new ItemStack(Material.BARRIER);
        ItemMeta ym = year.getItemMeta();
        ym.setDisplayName(ChatColor.GREEN + "Add: 1 Year");
        year.setItemMeta(ym);
        timeGUI.setItem(6, year);

        //Ban Options
        ItemStack ban = new ItemStack(Material.GREEN_BANNER);
        ItemMeta ban_meta = ban.getItemMeta();
        ban_meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Confirm");
        timeGUI.setItem(8, ban);

        player.openInventory(timeGUI);
    }
    public void openAdminGUI(Player player){
        Inventory adminGUI = Bukkit.createInventory(player, 9, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Admin GUI");

        ItemStack ban = new ItemStack(Material.BARRIER);
        ItemMeta bm = ban.getItemMeta();
        bm.setDisplayName(ChatColor.RED + "Ban a User");
        ban.setItemMeta(bm);
        adminGUI.setItem(1, ban);

        ItemStack vanish = new ItemStack(Material.GLASS);
        ItemMeta vm = ban.getItemMeta();
        vm.setDisplayName(ChatColor.RED + "Vanish");
        vanish.setItemMeta(vm);
        adminGUI.setItem(4, vanish);

        ItemStack exit = new ItemStack(Material.RED_BANNER);
        ItemMeta em = ban.getItemMeta();
        em.setDisplayName(ChatColor.RED + "Vanish");
        exit.setItemMeta(em);
        adminGUI.setItem(7, exit);

        player.openInventory(adminGUI);

    }


}
