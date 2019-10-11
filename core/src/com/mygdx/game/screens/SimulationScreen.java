package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.FrameRate;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.generators.RandomSpreadLiveGenerator;
import com.mygdx.game.util.Counter;

public class SimulationScreen extends ScreenAdapter {

    private GameOfLive game ;
    private LiveEngine engine = LiveEngine.getInstance();
    private FrameRate frameRate;
    private Counter nextStateCalcCounter;

    public SimulationScreen(GameOfLive game) {
        this.game = game;
        //frameRate = new FrameRate();
        nextStateCalcCounter = new Counter(0.1f);
    }

    @Override
    public void render(float delta) {
        //super
        super.render(delta);
        //update cam
        game.cam.update();
        game.batch.setProjectionMatrix(game.cam.combined);
        //clear screen and set colour
        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //get array dimensions
        int height = engine.getHeight();
        int width = engine.getWidth();
        //get array
        byte[][] array = engine.getLiveArray();
        //advance counter and determine if next state should be calculated
        if (nextStateCalcCounter.isFinished()) {
            engine.calculateNextState();
            nextStateCalcCounter.reset();
        } else {
            nextStateCalcCounter.subtract(delta);
        }
        //calculate cell dimensions
        int squareHeight = Gdx.graphics.getHeight() / height;
        int squareWidth = Gdx.graphics.getWidth() / width;

        game.batch.setTransformMatrix(game.cam.view);
        game.batch.setProjectionMatrix(game.cam.projection);
        //draw living cells to screen
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 1, 0,1);
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (array[x][y] == 1)
                    game.shapeRenderer.rect(x*squareWidth, y*squareHeight, squareWidth, squareHeight);
            }
        }
        game.shapeRenderer.end();

        //draw and update frameRate
        //frameRate.update();
        //frameRate.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        game.viewport.update(width, height);
        game.cam.update();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new PauseScreen(game));
                }
                return super.keyDown(keycode);
            }
        });
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
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
