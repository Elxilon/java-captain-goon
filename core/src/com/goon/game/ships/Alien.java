package com.goon.game.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.goon.game.screens.MainMenu;

public class Alien extends AlienShip {
    public static final Texture IMG = new Texture(Gdx.files.internal("sprites/ships/roundysh_small.png")),
            IMG_PROJECTILE = new Texture(Gdx.files.internal("sprites/ammo/wship-4.png"));
    private static final int SIZE = 80, DMG = 10, HP = 100;

    public Alien(int start, int end, int speed) {
        super(new TextureRegion(IMG), new TextureRegion(IMG_PROJECTILE));
        x = (start + end) / 2 - SIZE / 2;
        y = Gdx.graphics.getHeight() - SIZE * 2;
        width = SIZE;
        height = SIZE;
        setStart(start);
        setEnd(end);
        setSpeed(speed);

        setDamage(DMG);
        setHealth(HP);
        setMaxHealth(HP);
    }
}