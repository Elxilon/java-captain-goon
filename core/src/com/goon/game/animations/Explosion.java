package com.goon.game.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    private float x, y;
    private int size;
    private TextureAtlas atlas;
    private Animation<TextureRegion> explosion;
    private float stateTime = 0f;
    public Sound sound;

    public Explosion(float x, float y, int size, Sound sound, float volume) {
        this.x = x;
        this.y = y;
        this.size = size;
        atlas = new TextureAtlas(Gdx.files.internal("sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack.atlas"));
        explosion = new Animation<TextureRegion>(0.033f, atlas.getRegions());
        this.sound = sound;
        this.sound.play(volume);
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(explosion.getKeyFrame(stateTime), x, y, size, size);
    }

}
