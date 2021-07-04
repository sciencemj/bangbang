package com.sciecenmj.bangbang.gun;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bukkit.Particle;
import org.bukkit.Sound;

@Getter
public enum GunType {
    PISTOL("pistol", new Gun(0.5f, 5,10,10,60,10, Sound.BLOCK_GILDED_BLACKSTONE_BREAK, Particle.ASH));

    final private String name;
    final private Gun gun;

    private GunType(String name, Gun gun){
        this.name = name;
        this.gun = gun;
    }
}
