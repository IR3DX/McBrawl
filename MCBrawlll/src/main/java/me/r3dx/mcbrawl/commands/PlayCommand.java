package me.r3dx.mcbrawl.commands;

import me.r3dx.mcbrawl.MCBrawl;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayCommand implements CommandExecutor, Listener{
    private MCBrawl plugin;
    private Location hub;
    private static Player p1;
    private static Player p2;
    public static byte p1Lives = 3;
    public static byte p2Lives = 3;
    private final PotionEffect SLOWNESS = new PotionEffect(PotionEffectType.SLOW, 70, 10, true);

    public PlayCommand(MCBrawl plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            hub = p.getLocation();
            if(args[0].equals("a")){
                p1 = p;
            }else if(args[0].equals("b")){
                p2 = p;
            }else if(args[0].equals("s")){
                p1.getWorld().setSpawnLocation(SetLocationCommand.getFirstLocation());
                p2.getWorld().setSpawnLocation(SetLocationCommand.getSecondLocation());
                p1.teleport(SetLocationCommand.getFirstLocation());
                p2.teleport(SetLocationCommand.getSecondLocation());
                p1.addPotionEffect(SLOWNESS);
                p2.addPotionEffect(SLOWNESS);
                p1.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"3", "", 0, 20, 0);
                p2.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD +"3", "", 0, 20, 0);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    p1.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "2", "", 0, 20, 0);
                    p2.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "2", "", 0, 20, 0);
                }, 20);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    p1.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "1", "", 0, 20, 0);
                    p2.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "1", "", 0, 20, 0);
                }, 40);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    p1.sendTitle(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Brawl!", "", 0, 10, 0);
                    p2.sendTitle(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Brawl!", "", 0, 10, 0);
                }, 60);
            }
        }
        return true;
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent e){
        Player dead = e.getEntity();
        if(dead.equals(p2)){
            p2Lives--;
        }else if(dead.equals(p1)){
            p1Lives--;
        }
        if(p1Lives == 0){
            p1.getWorld().setSpawnLocation(hub);
            p2.getWorld().setSpawnLocation(hub);
            p1.teleport(hub);
            p2.teleport(hub);
            p2.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU WON!", "", 0, 20, 5);
            p1.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "YOU LOST :(", "", 0, 20, 5);
            p1 = null;
            p2 = null;
            p1Lives = 3;
            p2Lives = 3;
        }
        if(p2Lives == 0){
            p1.getWorld().setSpawnLocation(hub);
            p2.getWorld().setSpawnLocation(hub);
            p1.teleport(hub);
            p2.teleport(hub);
            p1.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "YOU WON!", "", 0, 20, 5);
            p2.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "YOU LOST :(", "", 0, 20, 5);
            p1 = null;
            p2 = null;
            p1Lives = 3;
            p2Lives = 3;
        }
    }
}
