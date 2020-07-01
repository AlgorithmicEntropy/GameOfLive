package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Settings;

public class MenuScreen extends AbstractGameScreen {

    private GameOfLive game;
    private Table table;
    private Stage stage;

    public MenuScreen(GameOfLive game)
    {
        super(game);
        //set fields
        this.game = game;
        table = new Table();
        stage = new Stage(game.viewport);
        //init ui elements
        TextButton createNewButton = new TextButton("Create New", game.skin);
        createNewButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });
        TextButton loadButton = new TextButton("Load World", game.skin);
        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                load();
            }
        });
        TextButton loadAndEditButton = new TextButton("Load & Edit", game.skin);
        loadAndEditButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loadAndEdit();
            }
        });
        TextButton settingsButton = new TextButton("Settings", game.skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                openSettings();
            }
        });
        //TODO add banner texture /label
        //table layout
        table.columnDefaults(2);
        table.align(Align.center);
        table.padTop(Constants.UI_TOP_PADDING);
        table.padBottom(100); // banner
        table.row();
        table.add(createNewButton).padBottom(20).colspan(2);
        table.row();
        table.add(loadButton).padBottom(20).padRight(Constants.UI_BUTTON_SIDE_PADDING);
        table.add(loadAndEditButton).padBottom(20);
        table.row();
        table.add(settingsButton).colspan(2);
        table.row();
        table.setFillParent(true);
        //add to stage
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        //super
        super.render(delta);

        //debug info
        table.setDebug(game.settings.isDebugEnabled());
        //stage
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

        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case (Input.Keys.SPACE):
                        quickStart();
                        break;
                    case (Input.Keys.ESCAPE):
                        Gdx.app.exit();
                }
                return super.keyDown(keycode);
            }
        };

        inputMultiplexer.addProcessor(inputAdapter);
        inputMultiplexer.addProcessor(stage);
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

    private void quickStart() {
        /*
        AbstractLiveGenerator generator = new CenteredSquareLiveGenerator(100, 100, 40);
        LiveEngine engine = LiveEngine.getInstance();
        engine.generate(generator);
        game.setScreen(new SimulationScreen(game));
        */
        /*
        game.setScreen(new WorldEditorScreen(game, new GameWorld(10, 10)));
        */
        game.setScreen(new SavedWorldsSelectionScreen(game, true));
    }

    private void load() {
        game.setScreen(new SavedWorldsSelectionScreen(game, true));
    }

    private void play() {
        game.setScreen(new GeneratorSelectionScreen(game));
    }

    private void loadAndEdit() {
        game.setScreen(new SavedWorldsSelectionScreen(game, false));
    }

    private void openSettings() {
        game.setScreen(new SettingsScreen(game));
    }
}
