package com.goon.game.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Projectile extends Rectangle {
    private final TextureRegion shipImg;
    private int damage;

    public int getDamage() { return damage; }

    public Projectile (float x, float y, int damage, TextureRegion shipImg) {
        super();
        this.shipImg = shipImg;
        this.x = x;
        this.y = y;
        width = (float) (shipImg.getRegionWidth() * 0.8);
        height = (float) (shipImg.getRegionHeight() * 0.8);
        this.damage = damage;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(shipImg, x, y, width, height);
    }
}
