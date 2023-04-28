package me.r3dx.mcbrawl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Misc implements Listener{

    private MCBrawl plugin;

    public Misc(MCBrawl plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e){
        if(e.getEntity().getShooter() instanceof Player){
            if(e.getEntityType().equals(EntityType.SNOWBALL)){
                if(e.getHitBlock() == null){
                    Location loc = e.getHitEntity().getLocation();
                    e.getHitEntity().getWorld().createExplosion(loc.getX(), loc.getY(),loc.getZ(),1, false,false);
                }else if(e.getHitEntity() == null){
                    Location loc = e.getHitBlock().getLocation();
                    e.getHitBlock().getWorld().createExplosion(loc.getX(), loc.getY(),loc.getZ(),1, false,false);
                }
            }
        }
    }

    @EventHandler
    private void hitGlass(PlayerMoveEvent e){
        Player p = e.getPlayer();
        p.setAllowFlight(true);
        Block a = p.getWorld().getBlockAt(p.getLocation().subtract(1,0,0));
        Block b = p.getWorld().getBlockAt(p.getLocation().subtract(-1,0,0));
        Block c = p.getWorld().getBlockAt(p.getLocation().subtract(0,0,1));
        Block x = p.getWorld().getBlockAt(p.getLocation().subtract(0,0,-1));
        Block y = p.getWorld().getBlockAt(p.getLocation().subtract(0,-1,0));
        Block z = p.getWorld().getBlockAt(p.getLocation().subtract(0,1,0));
        if(a.getType() == Material.GLASS ||
                b.getType() == Material.GLASS ||
                c.getType() == Material.GLASS ||
                x.getType() == Material.GLASS ||
                y.getType() == Material.GLASS ||
                z.getType() == Material.GLASS){
            p.setHealth(0);
        }
    }
}
