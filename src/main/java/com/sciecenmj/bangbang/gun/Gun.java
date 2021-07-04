package com.sciecenmj.bangbang.gun;

import lombok.Getter;
import org.bukkit.Particle;
import org.bukkit.Sound;

@Getter
public class Gun {
    float gap;
    double damage;
    int range;
    Sound sound = Sound.BLOCK_GILDED_BLACKSTONE_BREAK;
    Particle particle = Particle.ASH;
    int bullet;
    int reloadTime;
    int coolDown;
    public Gun(float gap, double damage, int range, int bullet, int reloadTime, int coolDown){
        this.gap = gap;
        this.damage = damage;
        this.range = range;
        this.bullet = bullet;
        this.reloadTime = reloadTime;
        this.coolDown = coolDown;
    }
    public Gun(float gap, double damage,
               int range, int bullet, int reloadTime, int coolDown, Sound sound, Particle particle){
        this.gap = gap;
        this.damage = damage;
        this.range = range;
        this.bullet = bullet;
        this.reloadTime = reloadTime;
        this.coolDown = coolDown;
        this.sound = sound;
        this.particle = particle;
    }
}
