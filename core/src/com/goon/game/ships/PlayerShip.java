package com.goon.game.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.goon.game.projectiles.Projectile;

public class PlayerShip extends Ship {
    public static final Texture IMG = new Texture(Gdx.files.internal("sprites/ships/blueships1_small.png"));
    private static final TextureRegion IMG_PROJECTILE = new TextureRegion(new Texture(Gdx.files.internal("sprites/ammo/bullets/glowtube_small.png")));
    private static int SPEED_PROJ = 4, DMG = 10, HP = 100;

    public PlayerShip(int x, int y, float size) {
        super(new TextureRegion(IMG));
        this.x = x;
        this.y = y;
        this.width = IMG.getWidth() * size;
        this.height = IMG.getHeight() * size;

        setSpeedProj(SPEED_PROJ);
        setHealth(HP);
        setMaxHealth(HP);
    }

    public PlayerShip() {
        this(Gdx.graphics.getWidth() / 2 - 40, 0, 0.8f);
    }

    @Override
    protected void updatePosition() {
        x = Gdx.input.getX() - width / 2;
        y = Gdx.graphics.getHeight() - Gdx.input.getY() - width / 2 - 15;
        if (x < 0) x = 0;
        else if (x + width > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - width;
        if (y < 0) y = 0;
        else if (y > 88) y = 88;
    }

    @Override
    protected void updateProjectiles() {
        // si on clique tirer, un tire tous les 300 millisec, je préfère aussi la possibilité de tirer en continu
        // en maintenant le clic enfoncé, et puis ça évite de juste spam clic
        if (Gdx.input.isTouched() && TimeUtils.millis() - lastProjTime > 300) {
            projectiles.add(new Projectile(x + width / 2 - IMG_PROJECTILE.getRegionWidth() / 2, y + height, DMG, IMG_PROJECTILE));
            lastProjTime = TimeUtils.millis();
        }
        for (Projectile p : projectiles) {
            p.setY(p.getY() + getSpeedProj());
        }
    }
}
