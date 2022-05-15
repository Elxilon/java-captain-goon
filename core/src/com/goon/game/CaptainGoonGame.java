package com.goon.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.goon.game.screens.MainMenu;

public class CaptainGoonGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public long lastClick;
	public Music music;

	private Array<Screen> screens = new Array<>();

	public void addScreen(Screen screen){
		this.screens.add(screen);
	}

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		System.out.println(font.getScaleX());

		music = Gdx.audio.newMusic(Gdx.files.internal("music/Meathook.mp3"));
		music.setLooping(true);
		music.setVolume(0.05f);
		music.play();

		Screen mm = new MainMenu(this) ;
		this.addScreen(mm);
		this.setScreen(mm);
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		music.dispose();
		for(Screen s: screens){
			s.dispose();
		}
	}
}
