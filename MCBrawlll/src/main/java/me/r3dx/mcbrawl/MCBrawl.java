package me.r3dx.mcbrawl;

import me.r3dx.mcbrawl.commands.PlayCommand;
import me.r3dx.mcbrawl.commands.SetLocationCommand;
import org.bukkit.plugin.java.JavaPlugin;


public final class MCBrawl extends JavaPlugin {

    PlayCommand play;
    @Override
    public void onEnable() {
        System.out.println("MCBrawl loaded successfully!");
        getServer().getPluginManager().registerEvents(new Knockback(this), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(this), this);
        getServer().getPluginManager().registerEvents(new Misc(this), this);
        getCommand("play").setExecutor(new PlayCommand(this));
        getCommand("SetLocation").setExecutor(new SetLocationCommand());
    }

    @Override
    public void onDisable() {
        System.out.println("MCBrawl disabled successfully!");
    }
}