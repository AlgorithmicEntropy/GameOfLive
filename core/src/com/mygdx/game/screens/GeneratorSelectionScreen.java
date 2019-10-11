package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.generators.RandomSpreadLiveGenerator;

public class GeneratorSelectionScreen extends ScreenAdapter {

    //constants
    private static final String RANDOM_GEN_BUTTON_TEXT = "Random Spread Generator";
    private static final String DEFAULT_GRID_DIMENSION = "100";

    private GameOfLive game;
    private Stage stage;
    private LiveEngine engine = LiveEngine.getInstance();

    //UI Elements
    private TextButton randomGeneratorButton;
    private TextField widthField;
    private TextField heightField;
    //button locations
    private int randomGeneratorX;
    private int randomGeneratorY;

    private int heightFieldX, heightFieldY;
    private int widthFieldX, widthFieldY;
    //temp screen clicked vector
    private Vector3 clickCords = new Vector3();

    public GeneratorSelectionScreen(final GameOfLive game) {
        this.game = game;
        stage = new Stage();
        //calc button locations
        randomGeneratorY = (int)(Gdx.graphics.getHeight() * 0.2f);
        randomGeneratorX = (int)(Gdx.graphics.getWidth() * 0.25f);

        widthFieldX = (int)(Gdx.graphics.getWidth() * 0.5);
        widthFieldY = (int)(Gdx.graphics.getHeight() * 0.5) + 20;

        heightFieldX = (int)(Gdx.graphics.getWidth() * 0.5);
        heightFieldY = (int)(Gdx.graphics.getHeight() * 0.5) - 20;
        //create buttons
        randomGeneratorButton = new TextButton(RANDOM_GEN_BUTTON_TEXT, game.skin);
        randomGeneratorButton.setPosition(randomGeneratorX, randomGeneratorY);
        randomGeneratorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                randomGeneration();
            }
        });

        widthField = new TextField(DEFAULT_GRID_DIMENSION, game.skin);
        widthField.setPosition(widthFieldX, widthFieldY);

        heightField = new TextField(DEFAULT_GRID_DIMENSION, game.skin);
        heightField.setPosition(heightFieldX, heightFieldY);

        //add elements to stage
        stage.addActor(randomGeneratorButton);
        stage.addActor(widthField);
        stage.addActor(heightField);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //clear screen and set colour
        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update cam
        game.cam.update();
        game.batch.setProjectionMatrix(game.cam.combined);

        game.batch.setTransformMatrix(game.cam.view);
        game.batch.setProjectionMatrix(game.cam.projection);
        //draw and update stage
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
        //set input processor to stage
        Gdx.input.setInputProcessor(stage);
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

    private void randomGeneration() {
        Input.TextInputListener listener = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                try {
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    int percentage = Integer.parseInt(text);

                    RandomSpreadLiveGenerator generator = new RandomSpreadLiveGenerator(width,height,percentage);
                    engine.setLiveArray(generator.generateLiveArray());
                    game.setScreen(new SimulationScreen(game));
                } catch (NumberFormatException ex) {
                    Dialog dialog = new Dialog("Invalid input", game.skin);
                    dialog.button("OK");
                    dialog.show(stage);
                    Gdx.app.log("Exception", "parsing input");
                }
            }

            @Override
            public void canceled() {

            }
        };
        Gdx.input.getTextInput(listener, "Living Cells Percentage", "Enter percent of living cells", "");
    }
}
