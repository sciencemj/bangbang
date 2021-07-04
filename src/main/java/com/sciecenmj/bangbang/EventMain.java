package com.sciecenmj.bangbang;
import com.sciecenmj.bangbang.gun.Gun;
import com.sciecenmj.bangbang.gun.GunCool;
import com.sciecenmj.bangbang.gun.GunType;
import com.sun.tools.javac.comp.Todo;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class EventMain implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Action a = e.getAction();
        Player p = e.getPlayer();
        p.sendMessage("interact");
        if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)){
            p.sendMessage("clicked");
            GunType type = GunType.PISTOL;
            Gun g = type.getGun();
            int gunCool = -1;
            if (BangBang.gunTime.containsKey(p)) {
                for (GunCool gc : BangBang.gunTime.get(p)) {
                    if (gc.getGunType().getGun().equals(g)) {
                        gunCool = BangBang.gunTime.get(p).indexOf(gc);
                        break;
                    }
                }
                if (gunCool == -1) {
                    BangBang.gunTime.get(p).add(new GunCool(type));
                    gunCool = BangBang.gunTime.get(p).size() - 1;
                }
            }else {
                List<GunCool> gunCools = new ArrayList<>();
                gunCools.add(new GunCool(type));
                BangBang.gunTime.put(p,gunCools);
                gunCool = 0;
            }
            shot(p, g, gunCool);
            p.sendMessage("shot");
        }
    }
    public void shot(Player p, Gun g, int gunCool){
        GunCool gc = BangBang.gunTime.get(p).get(gunCool);
        if (!gc.isCoolDowning() && !gc.isReloading()) {
            Vector v = p.getLocation().getDirection().multiply(g.getGap());
            Location l = p.getEyeLocation();
            World world = p.getWorld();
            world.playSound(l, g.getSound(), 1, 1);
            for (int i = 0; i < 20; i++) {
                world.spawnParticle(g.getParticle(), l.add(v), 1, 0, 0, 0);
                for (Entity entity : world.getNearbyEntities(l, 0.25, 0.25, 0.25)) {
                    try {
                        ((LivingEntity) entity).damage(g.getDamage());
                    }catch (Exception e){

                    }
                }
            }
            gc.shot();
            BangBang.gunTime.get(p).remove(gunCool);
            BangBang.gunTime.get(p).add(gunCool, gc);
        }
    }
}
