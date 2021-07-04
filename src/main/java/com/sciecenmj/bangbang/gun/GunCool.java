package com.sciecenmj.bangbang.gun;

import lombok.Getter;

@Getter
public class GunCool {
    private GunType gunType;
    private boolean isReloading = false;
    private boolean isCoolDowning= false;
    private final int bullet;
    private int currentBullet;
    private final int reloadTime;
    private int cReloadTime = 0;
    private final int coolDown;
    private int cCoolDown = 0;
    public int shot(){
        if (currentBullet - 1 > 0){
            currentBullet--;
            isCoolDowning = true;
        }else {
            currentBullet = 0;
            isReloading = true;
        }
        return currentBullet;
    }
    public int reload(){
        currentBullet = bullet;
        return currentBullet;
    }
    public int reloadT(int i){
        cReloadTime--;
        if (cReloadTime == 0){
            cReloadTime = reloadTime;
            isReloading = false;
            reload();
        }
        return cReloadTime;
    }
    public int coolDownT(int i){
        cCoolDown--;
        if (cCoolDown == 0){
            cCoolDown = coolDown;
            isCoolDowning = false;
        }
        return cCoolDown;
    }
    public GunCool(GunType gunType){
        Gun gun = gunType.getGun();
        this.gunType = gunType;
        this.bullet = gun.getBullet();
        this.currentBullet = gun.getBullet();
        this.reloadTime = gun.getReloadTime();
        this.coolDown = gun.getCoolDown();
        this.cReloadTime = gun.getReloadTime();
        this.cCoolDown = gun.getCoolDown();
    }
}
