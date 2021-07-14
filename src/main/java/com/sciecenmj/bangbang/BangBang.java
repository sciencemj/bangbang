package com.sciecenmj.bangbang;

import com.sciecenmj.bangbang.gun.GunCool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class BangBang extends JavaPlugin {
    public static Plugin plugin;
    public static HashMap<Player, List<GunCool>> gunTime = new HashMap<>();
    public static HashMap<Player, BossBar> bossBar = new HashMap<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Bukkit.getServer().getPluginManager().registerEvents(new EventMain(), plugin);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){
                    if (gunTime.containsKey(p)) {
                        for (GunCool gc : gunTime.get(p)) {
                            if (gc.isCoolDowning()) {
                                gc.coolDownT(1);
                                if (gc.isCoolDowning()) {
                                    changeBossbar(p, ChatColor.YELLOW + "쿨타임:" + gc.getCCoolDown(),
                                            ((double) gc.getCCoolDown()) / ((double) gc.getCoolDown()), BarColor.YELLOW);
                                }else {
                                    changeBossbar(p, "탄수:" + gc.getCurrentBullet(),
                                            ((double) gc.getCurrentBullet()) / ((double) gc.getBullet()), BarColor.BLUE);
                                }
                            } else if (gc.isReloading()) {
                                gc.reloadT(1);
                                if(gc.isReloading()) {
                                    changeBossbar(p, ChatColor.RED + "재장전:" + gc.getCReloadTime(),
                                            ((double) gc.getCReloadTime()) / ((double) gc.getReloadTime()), BarColor.RED);
                                }else {
                                    changeBossbar(p, "탄수:" + gc.getCurrentBullet(),
                                            ((double) gc.getCurrentBullet()) / ((double) gc.getBullet()), BarColor.BLUE);
                                }
                            } else {
                                changeBossbar(p, "탄수:" + gc.getCurrentBullet(),
                                        ((double) gc.getCurrentBullet()) / ((double) gc.getBullet()), BarColor.BLUE);
                            }
                        }
                    }
                }
            }
        },0L,1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void changeBossbar(Player p, String s, Double progress, BarColor color){
        if (bossBar.containsKey(p)) { //TODO 재장전,쿨타임 처리 cancelingTsk로 옮기기(shot시 새로운 cancelingTask 생성)
            BossBar bar = bossBar.get(p);
            bar.setTitle(s);
            bar.setColor(color);
            bar.setProgress(progress);
            bossBar.replace(p, bar);
        } else {
            BossBar bar = getServer().createBossBar("", color, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
            bar.setTitle(s);
            bar.addPlayer(p);
            bar.setVisible(true);
            bar.setProgress(progress);
            bossBar.put(p, bar);
        }
    }
}
