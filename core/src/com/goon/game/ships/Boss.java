package com.goon.game.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Boss extends AlienShip {
    public static final Texture IMG = new Texture(Gdx.files.internal("sprites/ships/spco_small.png")),
        IMG_PROJECTILE = new Texture(Gdx.files.internal("sprites/ammo/aliendropping_small.png"));
    private static final int START = 0, END = Gdx.graphics.getWidth(), SIZE = 120, SPEED = 4,
            DMG = 15, HP = 200;
    // j'ai doublié les HP du boss par rapport à l'exemple à reproduire, car trop facile

    public Boss(int x, int y, int size) {
        super(new TextureRegion(IMG), new TextureRegion(IMG_PROJECTILE));
        this.x = x;
        this.y = y;
        width = size;
        height = size;
        setStart(START);
        setEnd(END);
        setSpeed(SPEED);

        setDamage(DMG);
        setHealth(HP);
        setMaxHealth(HP);
    }

    public Boss() {
        this(END / 2 - SIZE / 2, Gdx.graphics.getHeight() - SIZE, SIZE);
    }
}
