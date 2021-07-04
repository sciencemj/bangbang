package com.sciecenmj.bangbang;

import com.sciecenmj.bangbang.gun.GunCool;
import org.bukkit.Bukkit;
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

                                if (bossBar.containsKey(p)) {
                                    BossBar bar = bossBar.get(p);
                                    bar.setTitle("쿨타임:" + gc.getCCoolDown());
                                    bar.setColor(BarColor.YELLOW);
                                    bar.setProgress(((double) gc.getCCoolDown()) / ((double) gc.getCoolDown()));
                                    bossBar.replace(p, bar);
                                } else {
                                    BossBar bar = getServer().createBossBar("", BarColor.YELLOW, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                                    bar.addPlayer(p);
                                    bar.setVisible(true);
                                    bar.setTitle("쿨타임:" + gc.getCCoolDown());
                                    bar.setProgress(((double) gc.getCCoolDown()) / ((double) gc.getCoolDown()));
                                    bossBar.put(p, bar);
                                }
                                gc.coolDownT(1);
                            } else if (gc.isReloading()) {

                                if (bossBar.containsKey(p)) {
                                    BossBar bar = bossBar.get(p);
                                    bar.setTitle("재장전:" + gc.getCReloadTime());
                                    bar.setColor(BarColor.RED);
                                    bar.setProgress(((double) gc.getCReloadTime()) / ((double) gc.getReloadTime()));
                                    bossBar.replace(p, bar);
                                } else {
                                    BossBar bar = getServer().createBossBar("", BarColor.RED, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                                    bar.addPlayer(p);
                                    bar.setVisible(true);
                                    bar.setTitle("재장전:" + gc.getCReloadTime());
                                    bar.setProgress(((double) gc.getCReloadTime()) / ((double) gc.getReloadTime()));
                                    bossBar.put(p, bar);
                                }
                                gc.reloadT(1);
                            } else {
                                if (bossBar.containsKey(p)) {
                                    BossBar bar = bossBar.get(p);
                                    bar.setTitle("탄수:" + gc.getCurrentBullet());
                                    bar.setColor(BarColor.BLUE);
                                    bar.setProgress(((double) gc.getCurrentBullet()) / ((double) gc.getBullet()));
                                    bossBar.replace(p, bar);
                                } else {
                                    BossBar bar = getServer().createBossBar("", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                                    bar.setTitle("탄수:" + gc.getCurrentBullet());
                                    bar.addPlayer(p);
                                    bar.setVisible(true);
                                    bar.setProgress(((double) gc.getCurrentBullet()) / ((double) gc.getBullet()));
                                    bossBar.put(p, bar);
                                }
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
}
