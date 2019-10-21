package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.FrameRate;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.util.Counter;

public class SimulationScreen extends AbstractGameScreen {

    private static final float DEFAULT_CYCLE_DURATION = 0.1f;
    private static final int PAUSE_TEXT_X = Gdx.graphics.getWidth() / 2;
    private static final int PAUSE_TEXT_Y = Gdx.graphics.getHeight() - 50;
    private static final int SIMULATION_SPEED_Y = Gdx.graphics.getHeight();
    private static final int SIMULATION_SPEED_X = 50;

    private GameOfLive game ;
    private LiveEngine engine = LiveEngine.getInstance();
    private ScreenAdapter simulationSourceScreen;
    private Counter nextStateCalcCounter;
    private boolean isPaused;
    private float cycleDuration;

    public SimulationScreen(GameOfLive game, ScreenAdapter simulationSourceScreen) {
        super(game);

        this.game = game;
        this.simulationSourceScreen = simulationSourceScreen;
        nextStateCalcCounter = new Counter(0.1f);
        //pause on load
        isPaused = true;
        //set default speed
        cycleDuration = DEFAULT_CYCLE_DURATION;
    }

    @Override
    public void render(float delta) {
        //clear screen and set colour
        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //super
        super.render(delta);
        //get array dimensions
        int height = engine.getHeight();
        int width = engine.getWidth();
        //get array
        byte[][] array = engine.getLiveArray();
        //advance counter and determine if next state should be calculated
        if (nextStateCalcCounter.isFinished() && !isPaused) {
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
        //end shape rendering
        game.shapeRenderer.end();
        //begin batch drawing
        game.batch.begin();
        //draw paused text
        if (isPaused) {
            game.font.draw(game.batch, "SPACE to start simulation", PAUSE_TEXT_X, PAUSE_TEXT_Y);
            //TODO make same size as fps
        }
        //draw simulation speed
        game.font.draw(game.batch, "speed: " + cycleDuration + " s", SIMULATION_SPEED_X, SIMULATION_SPEED_Y);
        //end batch drawing
        game.batch.end();
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
                switch (keycode) {
                    case (Input.Keys.ESCAPE):
                        game.setScreen(new PauseScreen(game, simulationSourceScreen));
                        break;
                    case (Input.Keys.SPACE):
                        isPaused = !isPaused;
                        break;
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

    public ScreenAdapter getSimulationSourceScreen() {
        return simulationSourceScreen;
    }

    public void setCycleDuration(float cycleDuration) {
        this.cycleDuration = cycleDuration;
    }
}
