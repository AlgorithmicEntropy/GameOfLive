package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.GameOfLive;

public class AbstractZoomableScreen extends AbstractGameScreen {

    private static final int CAM_MOVE_VERTICAL_AMOUNT = 50;
    private static final int CAM_MOVE_HORIZONTAL_AMOUNT = 50;
    private static final float ZOOM_DIVIDER = 30;

    private GameOfLive game;
    protected InputMultiplexer inputMultiplexer;

    public AbstractZoomableScreen(GameOfLive game) {
        super(game);
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //input handling
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            game.cam.translate(0, CAM_MOVE_VERTICAL_AMOUNT * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            game.cam.translate(-CAM_MOVE_HORIZONTAL_AMOUNT * delta, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.cam.translate(0, -CAM_MOVE_VERTICAL_AMOUNT * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            game.cam.translate(CAM_MOVE_HORIZONTAL_AMOUNT * delta, 0);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
        inputMultiplexer = new InputMultiplexer();
        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean scrolled(int amount) {
                game.cam.zoom += amount / ZOOM_DIVIDER;
                //clamp zoom level
                game.cam.zoom = MathUtils.clamp(game.cam.zoom, 0.1f, Gdx.graphics.getWidth()/game.cam.viewportWidth);
                return super.scrolled(amount);
            }
        };
        inputMultiplexer.addProcessor(inputAdapter);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
