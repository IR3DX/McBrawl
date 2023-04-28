package me.r3dx.mcbrawl.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocationCommand implements CommandExecutor{

    private static Location firstPlayerSpawn;
    private static Location secondPlayerSpawn;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            switch(args[0]){
                case "1":
                    firstPlayerSpawn = p.getLocation();
                    break;
                case "2":
                    secondPlayerSpawn = p.getLocation();
                    break;
        }}
        return true;
    }

    public static Location getFirstLocation(){
        return firstPlayerSpawn;
    }

    public static Location getSecondLocation(){
        return secondPlayerSpawn;
    }
}
