package me.r3dx.mcbrawl;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DoubleJump implements Listener {

    private MCBrawl plugin;
    private Map<UUID, Byte> jumpMap = new HashMap<>();

    public DoubleJump(MCBrawl plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerDoubleSpaceStroke(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
            e.setCancelled(true);
            if (jumpMap.get(p.getUniqueId()) < 2) {
                byte jumpCountIncrementer = jumpMap.get(p.getUniqueId());
                jumpCountIncrementer++;
                jumpMap.put(p.getUniqueId(), jumpCountIncrementer);
                Vector v = p.getLocation().getDirection().multiply(0.2).setY(0.7);
                p.setVelocity(v);
                w.spawnParticle(Particle.CLOUD, p.getLocation().add(0,-2,0), 100, 0, 0, 0, 0);
                w.playEffect(p.getLocation(), Effect.GHAST_SHOOT, 0);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void groundedReset(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block b = p.getWorld().getBlockAt(p.getLocation().subtract(0, 1, 0));
        if (!(b.getType().isAir())) {
            jumpMap.put(p.getUniqueId(), (byte) 0);
        }
    }
}
