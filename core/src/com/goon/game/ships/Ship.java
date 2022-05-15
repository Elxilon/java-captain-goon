package com.goon.game.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.goon.game.animations.Explosion;
import com.goon.game.projectiles.Projectile;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Ship extends Rectangle {
    private final TextureRegion shipImg;
    protected ArrayList<Projectile> projectiles = new ArrayList<>();
    protected long lastProjTime;
    private int speedProj, health, maxHealth;
    private Color color;
    public static final Sound SOUND_EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("sounds/mini_explosion.mp3"));

    private ArrayList<Explosion> explosions = new ArrayList<>();
    private static int EXPLOSION_SIZE = 100;

    public int getSpeedProj() { return speedProj; }
    protected void setSpeedProj(int speedProj) {
        this.speedProj = speedProj;
    }

    public int getHealth() { return health; }
    protected void setHealth(int health) { this.health = health; }

    public int getMaxHealth() { return maxHealth; }
    protected void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    protected void setColor(Color color) { this.color = color; }

    public ArrayList<Explosion> getExplosions() { return explosions; }

    public Ship (TextureRegion shipImg) {
        super();
        this.shipImg = shipImg;
        color = new Color(Color.WHITE);
    }

    protected abstract void updatePosition();
    protected abstract void updateProjectiles();

    public void update() {
        updatePosition();
        updateProjectiles();
    }

    public boolean isAlive() { return health > 0; }

    // méthode qui retourne en pourcentage (entre 0 et 1) la vie restante
    public float getHealthPourcent() { return (float)health / maxHealth; }

    // dans cette méthode, pour chacun projectile du ship, on vérifie qu'il n'atteint pas le bord de l'écran
    // sinon on supprime le projectile, et on vérifie surtout s'il touche le ship en paramètre, si c'est le cas
    // on ajoute une explosion, on change l'opacité du rouge et on remove le projectile
    public void checkCollision(Ship ship) {
        Iterator<Projectile> iterProj = projectiles.iterator();
        while (iterProj.hasNext()) {
            Projectile p = iterProj.next();
            if (speedProj > 0 && p.getY() > Gdx.graphics.getHeight()) iterProj.remove();
            else if (p.getY() + p.height < 0) iterProj.remove();
            if (Intersector.overlaps(p, ship)) {
                explosions.add(new Explosion(p.getX() + p.getWidth() / 2 - EXPLOSION_SIZE / 2,
                        p.getY() + p.getHeight() / 2 - EXPLOSION_SIZE / 2, EXPLOSION_SIZE, SOUND_EXPLOSION, 0.1f));
                ship.setHealth(Math.max(ship.getHealth() - p.getDamage(), 0));
                ship.setColor(new Color(1, ship.getHealthPourcent(), ship.getHealthPourcent(), 1));
                iterProj.remove();
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (Projectile p : projectiles) {
            p.draw(batch);
        }
        batch.setColor(color);
        batch.draw(shipImg, x, y, width, height);
        batch.setColor(Color.WHITE);
    }
}
