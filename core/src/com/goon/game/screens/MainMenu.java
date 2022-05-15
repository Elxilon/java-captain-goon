package com.goon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.goon.game.CaptainGoonGame;

public class MainMenu implements Screen {
    final CaptainGoonGame game;
    OrthographicCamera camera;
    private final Texture background;

    public MainMenu(final CaptainGoonGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        background = new Texture(Gdx.files.internal("backgrounds/Blue Nebula 1 - 1024x1024.png"));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        GlyphLayout title = new GlyphLayout(game.font, "Welcome to Captain Goon !!!");
        GlyphLayout subtitle = new GlyphLayout(game.font, "Tap anywhere to begin!");

        game.batch.begin();
        game.batch.draw(background, 0, 0);

        game.font.draw(game.batch, title, Gdx.graphics.getWidth() / 2 - title.width / 2, Gdx.graphics.getHeight() / 2);
        game.font.draw(game.batch, subtitle, Gdx.graphics.getWidth() / 2 - subtitle.width / 2, Gdx.graphics.getHeight() / 2 - game.font.getLineHeight());
        game.batch.end();

        if (Gdx.input.isTouched() && TimeUtils.millis() - game.lastClick > 1000) {
            game.lastClick = TimeUtils.millis();
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (game.music.isPlaying()) game.music.pause();
    }

    @Override
    public void resume() {
        if (!game.music.isPlaying()) game.music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
