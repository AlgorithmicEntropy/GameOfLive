package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.FrameRate;
import com.mygdx.game.GameOfLive;

public abstract class AbstractGameScreen extends ScreenAdapter {

    private GameOfLive game;
    private FrameRate frameRate;

    public AbstractGameScreen(GameOfLive game) {
        this.game = game;
        this.frameRate = new FrameRate(game);
    }

    @Override
    public void render(float delta) {
        //clear screen and set color
        Color color = game.settings.getBackroundColor();
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //super
        super.render(delta);
        //draw and update frameRate
        frameRate.update();
        frameRate.render();
        //standard keys for all screens
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            game.settings.setDebugEnabled(!game.settings.isDebugEnabled());
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        /*
        game.cam.translate(width / 2, height / 2);
        game.cam.update();
        game.batch.setProjectionMatrix(game.cam.combined);
        */
        //TODO fix resize
    }

    @Override
    public void show() {
        super.show();
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
