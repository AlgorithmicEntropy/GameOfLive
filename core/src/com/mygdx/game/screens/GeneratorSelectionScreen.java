package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.screens.generatorScreens.RandomGenScreen;
import com.mygdx.game.screens.generatorScreens.SquareGenScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Settings;
import com.mygdx.game.world.GameWorld;

import static java.lang.Integer.parseInt;

public class GeneratorSelectionScreen extends AbstractGameScreen {

    //constants
    private static final String RANDOM_GEN_BUTTON_TEXT = "Random Spread Generator";
    private static final String CENTER_SQUARE_GEN_BUTTON_TEXT = "Square Generator";
    private static final String DEFAULT_GRID_DIMENSION = "1000";

    private boolean isDialogOpen = false;

    private GameOfLive game;
    private Stage stage;
    private Table table;
    private LiveEngine engine = LiveEngine.getInstance();

    //UI Elements
    private TextButton randomGeneratorButton;
    private TextButton centeredSquareGenButton;
    private TextButton customWorldGenButton;
    private TextField widthField;
    private TextField heightField;
    private Label heightLabel;
    private Label widthLabel;
    private Label topBanner;

    GeneratorSelectionScreen(final GameOfLive game) {
        //super
        super(game);

        this.game = game;
        stage = new Stage(game.viewport);
        table = new Table();

        //create buttons
        randomGeneratorButton = new TextButton(RANDOM_GEN_BUTTON_TEXT, game.skin);
        randomGeneratorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int[] dimensions = parseWorldDimensions();
                game.setScreen(new RandomGenScreen(game, dimensions[0], dimensions[1]));
            }
        });

        centeredSquareGenButton = new TextButton(CENTER_SQUARE_GEN_BUTTON_TEXT, game.skin);
        centeredSquareGenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int[] dimensions = parseWorldDimensions();
                game.setScreen(new SquareGenScreen(game, dimensions[0], dimensions[1]));
            }
        });

        customWorldGenButton = new TextButton("Custom", game.skin);
        customWorldGenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                customGeneration();
            }
        });

        widthField = new TextField(Integer.toString((int)game.cam.viewportWidth), game.skin);
        heightField = new TextField(Integer.toString((int)game.cam.viewportHeight), game.skin);
        widthLabel = new Label("World Width", game.skin);
        heightLabel = new Label("World Height", game.skin);
        topBanner = new Label("Choose a generator", game.skin);
        topBanner.setFontScale(4);

        //add elements to table
        table.align(Align.top);
        table.columnDefaults(3);
        table.padTop(Constants.UI_TOP_PADDING);
        //TODO proper banner screen label
        table.add(topBanner).spaceBottom(100).colspan(3);
        table.row();
        table.add(widthLabel).spaceBottom(20);
        table.add(widthField).spaceBottom(20);
        table.row();
        table.add(heightLabel);
        table.add(heightField);
        table.row();
        table.add(randomGeneratorButton).spaceTop(100);
        table.add(customWorldGenButton).spaceTop(100);
        table.add(centeredSquareGenButton).spaceTop(100).align(Align.bottom);
        table.setFillParent(true);
        //add to stage
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        //super
        super.render(delta);
        //change debug info
        table.setDebug(game.settings.isDebugEnabled());
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
                        game.setScreen(new MenuScreen(game));
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

    private void customGeneration() {
        try {
            final int width = parseInt(widthField.getText());
            final int height = parseInt(heightField.getText());

            //post runnable to draw on correct thread
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new WorldEditorScreen(game, new GameWorld(width, height)));
                }
            });
        } catch (NumberFormatException ex) {
            Dialog dialog = new Dialog("Invalid input", game.skin);
            dialog.button("OK");
            dialog.show(stage);
            Gdx.app.log("Exception", "parsing input");
        }
    }

    private int[] parseWorldDimensions() {
        int[] dimensions = new int[2];
        dimensions[0] = parseInt(heightField.getText());
        dimensions[1] = parseInt(widthField.getText());
        return dimensions;
    }

}
