package me.r3dx.mcbrawl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Knockback implements Listener {

    private MCBrawl plugin;
    private Map<UUID,Double> heatMap = new HashMap<>();

    private final double
            SWORD_KNOCK = 1.2,
            SWORD_DAMAGE = 0.25,

            BOW_KNOCK = 1.1,
            BOW_DAMAGE = 0.21,

            HAMMER_KNOCK = 1.5,
            HAMMER_DAMAGE = 0.18,

            KATARS_KNOCK = 1.15,
            KATARS_DAMAGE = 0.3,

            AXE_KNOCK = 1.5,
            AXE_DAMAGE = 0.19,

            SCYTHE_KNOCK = 1.08,
            SCYTHE_DAMAGE = 0.31,

            UNARMED_KNOCK = 1,
            UNARMED_DAMAGE = 0.2;

    private final PotionEffect
            JUMP = new PotionEffect(PotionEffectType.JUMP, 999999, 1, true),
            REGEN = new PotionEffect(PotionEffectType.REGENERATION, 999999, 10, true),
            SAT = new PotionEffect(PotionEffectType.SATURATION, 999999, 1, true);

    public Knockback(MCBrawl plugin) {
            this.plugin = plugin;
        }

    @EventHandler
    private void onPlayerHitPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player damaged) {
            switch(damager.getItemInHand().getType()){
                case DIAMOND_SWORD:
                    knockback(damager, damaged, SWORD_KNOCK, SWORD_DAMAGE);
                    break;
                case BOW:
                    knockback(damager, damaged, BOW_KNOCK, BOW_DAMAGE);
                    break;
                case DIAMOND_SHOVEL:
                    knockback(damager, damaged, HAMMER_KNOCK, HAMMER_DAMAGE);
                    break;
                case FEATHER:
                    knockback(damager, damaged, KATARS_KNOCK, KATARS_DAMAGE);
                    break;
                case DIAMOND_AXE:
                    knockback(damager, damaged, AXE_KNOCK, AXE_DAMAGE);
                    break;
                case DIAMOND_HOE:
                    knockback(damager, damaged, SCYTHE_KNOCK, SCYTHE_DAMAGE);
                    break;
                default:
                    knockback(damager, damaged, UNARMED_KNOCK, UNARMED_DAMAGE);
                    break;
            }
        }
    }

    private void knockback(Player damager, Player damaged, double WEAPON_KNOCK, double WEAPON_DAMAGE){
        double heatValue = heatMap.get(damaged.getUniqueId());
        heatValue += WEAPON_DAMAGE;
        heatMap.put(damaged.getUniqueId(), heatValue);
        Vector damagedV = damaged.getLocation().toVector();
        Vector damagerV = damager.getLocation().toVector();
        Vector velocity = damagedV.subtract(damagerV).normalize().multiply(WEAPON_KNOCK * heatValue).setY(0.2);
        damaged.setVelocity(velocity);
        if (heatValue >= 10) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                damaged.setVelocity(velocity.clone());
            }, 5);
            if (heatValue >= 15) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    damaged.setVelocity(velocity.clone());
                }, 1);
                if (heatValue >= 20) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        damaged.setVelocity(velocity.clone());
                    }, 1);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onJoin(PlayerJoinEvent event2) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            event2.getPlayer().addPotionEffect(JUMP);
            event2.getPlayer().addPotionEffect(REGEN);
            event2.getPlayer().addPotionEffect(SAT);
        }, 2);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onDeath(PlayerDeathEvent event3) {
        heatMap.put(event3.getEntity().getUniqueId(), 0.0);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onRespawn(PlayerRespawnEvent event5) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            event5.getPlayer().addPotionEffect(JUMP);
            event5.getPlayer().addPotionEffect(REGEN);
            event5.getPlayer().addPotionEffect(SAT);
        }, 2);
    }
}
