package com.goon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.goon.game.CaptainGoonGame;
import com.goon.game.animations.Explosion;
import com.goon.game.ships.*;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    private final CaptainGoonGame game;
    private final OrthographicCamera camera;
    private final TextureRegion background;

    private ArrayList<Ship> ships = new ArrayList<>();
    private PlayerShip player;
    private Boss boss;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private ArrayList<Explosion> explosions = new ArrayList<>();
    private static int EXPLOSION_SIZE = 300;
    private final Sound bigExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/big_explosion1.mp3"));

    private boolean isOver = false;

    public GameScreen(final CaptainGoonGame game) {
        this.game = game;
        this.game.addScreen(this);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);

        background = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/Planets.jpg")), 1920, 1200);
        player = new PlayerShip();
        boss = new Boss();
        ships.add(player);
        ships.add(boss);
        ships.add(new Alien(-20, WIDTH / 2 + 20, 3));
        ships.add(new Alien(WIDTH / 2 - 20, WIDTH + 20, -3));
    }

    private void affichageUI(int p, int b) {
        PlayerShip player = new PlayerShip(10, 10, 0.3f);
        Boss boss = new Boss(WIDTH - 10, 10, 40);
        boss.x -= boss.width;
        player.draw(game.batch);
        boss.draw(game.batch);
        game.font.draw(game.batch, p + " %", player.x + player.width + 10, player.y + player.height / 2 + 5);
        game.font.draw(game.batch, b + " %", boss.x - boss.width - 10, boss.y + boss.height / 2 + 5);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // on update chaque ship, puis on vérifie les collisions et enfin si le ship n'est plus en vie
        // on crée une grosse explosion, on définit que c'est la fin de la partie si c'est le vaisseur du joueur
        // et on remove le vaisseau du tableau
        Iterator<Ship> iterShip = ships.iterator();
        while (iterShip.hasNext()) {
            Ship ship = iterShip.next();
            ship.update();
            if (player.isAlive()) {
                player.checkCollision(ship);
                if (ship instanceof AlienShip) ship.checkCollision(player);
            }
            if (!ship.isAlive()) {
                explosions.add(new Explosion(ship.x + ship.width / 2 - EXPLOSION_SIZE / 2,
                        ship.y + ship.height / 2 - EXPLOSION_SIZE / 2, EXPLOSION_SIZE, bigExplosion, 0.5f));
                if (ship instanceof PlayerShip) isOver = true;
                iterShip.remove();
            }
        }
        // s'il ne reste que le vaisseau du joueur dans la liste alors c'est la fin de la partie
        if (ships.size() == 1 && ships.get(0) instanceof PlayerShip) isOver = true;

        game.batch.begin();
        game.batch.draw(background, -80, -10, WIDTH + 160, HEIGHT + 10);

        // on dessine dans un premier temps tous les vaisseaux
        for (Ship ship : ships) {
            ship.draw(game.batch);
        }
        // puis deuxième boucle afin que les explosions soient "draw" par dessus tous les ships
        for (Ship ship : ships) {
            ArrayList<Explosion> littleExplosions = ship.getExplosions();
            for (Explosion e: littleExplosions) {
                e.draw(game.batch);
            }
        }
        // puis cette dernière pour les grosses explosions
        for (Explosion e: explosions) {
            e.draw(game.batch);
        }

        // on affiche l'UI qu'on retrouve en bas de l'écran avec le pourcentage de vie des vaisseaux principaux
        affichageUI((int)(player.getHealthPourcent() * 100), (int)(boss.getHealthPourcent() * 100));

        // si c'est la fin de la partie :
        if (isOver) {
            // on assigne le bon texte centré à l'écran en fonction de si le joueur est encore en vie ou non,
            // puis on le dessine à l'écran
            GlyphLayout layout = (player.isAlive()) ?
                    new GlyphLayout(game.font, "YOU SAVED THE WORLD !") : new GlyphLayout(game.font, "GAME OVER");
            game.font.draw(game.batch, layout, Gdx.graphics.getWidth() / 2 - layout.width / 2, Gdx.graphics.getHeight() / 2 - layout.height / 2);
        }
        game.batch.end();

        // si c'est finit et en évitant un affichage instantané si on a gardé le clic, on repasse dans le menu
        if (isOver && Gdx.input.isTouched() && TimeUtils.millis() - game.lastClick > 2000) {
            game.lastClick = TimeUtils.millis();
            game.setScreen(new MainMenu(game));
        }
        // on finit par actualiser le temps du dernier clic effectué
        if (!isOver && Gdx.input.isTouched()) game.lastClick = TimeUtils.millis();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        game.music.play();
    }

    @Override
    public void hide() {
    }
    @Override
    public void pause() {
        if (game.music.isPlaying()) game.music.pause();
    }
    @Override
    public void resume() {
        if (!game.music.isPlaying()) game.music.play();
    }
    // il manque probablement ici comme dans les autres class des éléments à dispose
    @Override
    public void dispose() {
        Alien.IMG.dispose();
        Alien.IMG_PROJECTILE.dispose();
        Boss.IMG.dispose();
        Boss.IMG_PROJECTILE.dispose();
        PlayerShip.IMG.dispose();
        bigExplosion.dispose();
        Ship.SOUND_EXPLOSION.dispose();
    }
}
