package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.generators.CenteredSquareLiveGenerator;
import com.mygdx.game.generators.RandomSpreadLiveGenerator;

public class GeneratorSelectionScreen extends ScreenAdapter {

    //constants
    private static final String RANDOM_GEN_BUTTON_TEXT = "Random Spread Generator";
    private static final String CENTER_SQUARE_GEN_BUTTON_TEXT = "Square Generator";
    private static final String DEFAULT_GRID_DIMENSION = "100";

    private GameOfLive game;
    private Stage stage;
    private Table table;
    private LiveEngine engine = LiveEngine.getInstance();

    //UI Elements
    private TextButton randomGeneratorButton;
    private TextButton centeredSquareGenButton;
    private TextField widthField;
    private TextField heightField;
    private Label heightLabel;
    private Label widthLabel;
    private Label topBanner;

    public GeneratorSelectionScreen(final GameOfLive game) {
        this.game = game;
        stage = new Stage();
        table = new Table();

        //create buttons
        randomGeneratorButton = new TextButton(RANDOM_GEN_BUTTON_TEXT, game.skin);
        randomGeneratorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                randomGeneration();
            }
        });

        centeredSquareGenButton = new TextButton(CENTER_SQUARE_GEN_BUTTON_TEXT, game.skin);
        centeredSquareGenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                squareGenerator();
            }
        });

        widthField = new TextField(DEFAULT_GRID_DIMENSION, game.skin);
        heightField = new TextField(DEFAULT_GRID_DIMENSION, game.skin);
        widthLabel = new Label("World Width", game.skin);
        heightLabel = new Label("World Height", game.skin);
        topBanner = new Label("Choose a generator", game.skin);
        topBanner.setFontScale(GameOfLive.UI_SCALE);

        //add elements to table
        table.add(topBanner).spaceBottom(100);
        table.row();
        table.add(widthLabel).spaceBottom(20);
        table.add(widthField).spaceBottom(20);
        table.row();
        table.add(heightLabel);
        table.add(heightField);
        table.row();
        table.add(randomGeneratorButton).spaceTop(100);
        table.add(centeredSquareGenButton).spaceTop(100);
        table.setFillParent(true);
        //add to stage
        stage.addActor(table);
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
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case (Input.Keys.ESCAPE):
                        game.setScreen(new MenueScreen(game));
                        dispose();
                }
                return super.keyDown(keycode);
            }
        };

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(inputAdapter);
        Gdx.input.setInputProcessor(inputMultiplexer);
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
                    engine.generate(generator);
                    //post runnable to draw on correct thread
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new SimulationScreen(game));
                        }
                    });
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

    private void squareGenerator() {
        Input.TextInputListener listener = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                try {
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    int radius = Integer.parseInt(text);

                    CenteredSquareLiveGenerator generator = new CenteredSquareLiveGenerator(width, height, radius);
                    engine.generate(generator);
                    //post runnable to draw on correct thread
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new SimulationScreen(game));
                        }
                    });
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
        Gdx.input.getTextInput(listener, "Square Radius", "Enter radius", "");
    }
}
