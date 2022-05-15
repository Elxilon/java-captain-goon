package com.goon.game.ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.goon.game.projectiles.Projectile;

import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class AlienShip extends Ship {
    private int start, end, speed, damage;
    private TextureRegion projImg;
    private static final int SPEED_PROJ = -4;

    protected void setStart(int start) {
        this.start = start;
    }
    protected void setEnd(int end) {
        this.end = end;
    }
    protected void setSpeed(int speed) {
        this.speed = speed;
    }
    protected void setDamage(int damage) { this.damage = damage; }

    public AlienShip(TextureRegion shipImg, TextureRegion projImg) {
        super(shipImg);
        this.projImg = projImg;
        setSpeedProj(SPEED_PROJ);
    }

    // update du vaisseau en fonction du sinus, peut être améliorer en utilisant % 2*pi
    @Override
    protected void updatePosition() {
        float deg = (x < (start + end) / 2) ? 180 * (x + 30 - start) / (end - start) :
                180 * (x + width - 30 - start) / (end - start);
        if ((x <= start && x + speed * sin(toRadians(deg)) < x) ||
                (x + width >= end && x + speed * sin(toRadians(deg)) > x)) speed = -speed;
        x += speed * sin(toRadians(deg));
    }

    @Override
    protected void updateProjectiles() {
        if (TimeUtils.millis() - lastProjTime > 1000) {
            projectiles.add(new Projectile(x + width / 2 - projImg.getRegionWidth() / 2, y, damage, projImg));
            lastProjTime = TimeUtils.millis();
        }
        for (Projectile p : projectiles) {
            p.setY(p.getY() + getSpeedProj());
        }
    }
}
