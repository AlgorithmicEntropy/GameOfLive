package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.util.Settings;

public class GameOfLive extends Game {
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	public Skin skin;
	public OrthographicCamera cam;
	public Viewport viewport;
	public Settings settings;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));
        settings = Settings.getInstance();

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        setScreen(new MenuScreen(this));
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
