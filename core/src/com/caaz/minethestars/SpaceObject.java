package com.caaz.minethestars;

/**
 * Created by caaz on 2/22/15.
 */
public class SpaceObject {
    private boolean rock = false;
    private boolean player = false;
    private boolean bullet = false;
    public Object obj;

    public SpaceObject(int what, Object obj){
        rock = (what==0);
        player = (what==1);
        bullet = (what==2);
        this.obj = obj;
    }
    public boolean isRock() {
        return rock;
    }

    public void setRock(boolean rock) {
        this.rock = rock;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public boolean isBullet() {
        return bullet;
    }

    public void setBullet(boolean bullet) {
        this.bullet = bullet;
    }

}
