package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.util.Counter;

public class SimulationScreen extends AbstractZoomableScreen {

    private static final float DEFAULT_CYCLE_DURATION = 0.1f;
    private static final float MIN_CYCLE_DURATION = 0.0f;
    private static final float MAX_CYCLE_DURATION = 2f;
    private static final float DEFAULT_SPEED_DELTA = 0.1f;
    private static final int PAUSE_TEXT_X = Gdx.graphics.getWidth() / 2;
    private static final int PAUSE_TEXT_Y = Gdx.graphics.getHeight() - 50;
    private static final int SIMULATION_SPEED_Y = Gdx.graphics.getHeight() - 3;
    private static final int SIMULATION_SPEED_X = 50;
    private static final int SIMULATION_COUNTER_Y = Gdx.graphics.getHeight() - 3;
    private static final int SIMULATION_COUNTER_X = 140;

    private GameOfLive game ;
    private LiveEngine engine = LiveEngine.getInstance();
    private ScreenAdapter simulationSourceScreen;
    private Counter nextStateCalcCounter;
    private boolean isPaused;

    public SimulationScreen(GameOfLive game, ScreenAdapter simulationSourceScreen) {
        super(game);

        this.game = game;
        this.simulationSourceScreen = simulationSourceScreen;
        //set simulation speed
        nextStateCalcCounter = new Counter(DEFAULT_CYCLE_DURATION);
        //pause on load ?
        isPaused = false;
    }

    @Override
    public void render(float delta) {
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
        //draw living cells to screen
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color color = game.settings.getTileColor();
        game.shapeRenderer.setColor(color.r, color.g, color.b,color.a);
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
        game.hudBatch.begin();
        //draw paused text
        if (isPaused) {
            game.font.draw(game.hudBatch, "SPACE to start simulation", PAUSE_TEXT_X, PAUSE_TEXT_Y);
        }
        //draw simulation speed
        game.font.draw(game.hudBatch, "speed: " + nextStateCalcCounter.getDuration() + " s", SIMULATION_SPEED_X, SIMULATION_SPEED_Y);
        game.font.draw(game.hudBatch, "cycle: " + engine.getCycleCounter(), SIMULATION_COUNTER_X, SIMULATION_COUNTER_Y);
        //end batch drawing
        game.hudBatch.end();
        //fps
        super.drawFPS();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case (Input.Keys.ESCAPE):
                        game.setScreen(new PauseScreen(game, simulationSourceScreen));
                        break;
                    case (Input.Keys.SPACE):
                        isPaused = !isPaused;
                        break;
                    case (Input.Keys.PLUS):
                        increaseSimSpeed();
                        break;
                    case(Input.Keys.MINUS):
                        decreaseSimSpeed();
                        break;
                }

                return super.keyDown(keycode);
            }
        };
        //set input processor
        super.inputMultiplexer.addProcessor(inputAdapter);
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
        nextStateCalcCounter.setDuration(cycleDuration);
    }

    private void decreaseSimSpeed() {
        float currentSpeed = nextStateCalcCounter.getDuration();
        if (currentSpeed + DEFAULT_SPEED_DELTA <= MAX_CYCLE_DURATION)
            nextStateCalcCounter.setDuration(Math.round((currentSpeed + DEFAULT_SPEED_DELTA) * 10) / 10f); //round fix float precision
    }

    private void increaseSimSpeed() {
        float currentSpeed = nextStateCalcCounter.getDuration();
        if (currentSpeed - DEFAULT_SPEED_DELTA >= MIN_CYCLE_DURATION)
            nextStateCalcCounter.setDuration(Math.round((currentSpeed - DEFAULT_SPEED_DELTA) * 10) / 10f); //round fix float precision
    }
}
