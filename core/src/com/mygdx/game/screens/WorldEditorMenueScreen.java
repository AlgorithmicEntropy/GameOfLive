package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.world.GameWorld;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WorldEditorMenueScreen extends AbstractGameScreen {

    private GameOfLive game;
    private GameWorld world;
    //UI Elements
    private Stage stage;
    private Table table;

    private TextButton saveButton;
    private TextButton loadButton;
    private TextButton exitButton;
    private TextButton runSimulation;

    private Label titelLabel;

    public WorldEditorMenueScreen(GameOfLive game, final GameWorld world) {
        //super
        super(game);

        this.game = game;
        this.world = world;
        //create UI Elements
        table = new Table();
        stage = new Stage();

        titelLabel = new Label("Editor Menue Screen", game.skin);
        titelLabel.setFontScale(2);

        runSimulation = new TextButton("Run Simulation", game.skin);
        runSimulation.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runSimulation();
            }
        });

        saveButton = new TextButton("Save", game.skin);
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                save();
            }
        });

        loadButton = new TextButton("Load World", game.skin);
        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getFilePathAndLoadWorld();
            }
        });

        exitButton = new TextButton("Exit", game.skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exit();
            }
        });

        //set UI Layout Table
        table.align(Align.top);
        table.padTop(game.settings.getUiTopPadding());
        table.add(titelLabel).spaceBottom(50);
        table.row();
        table.add(runSimulation).spaceBottom(20);
        table.row();
        table.add(saveButton).spaceBottom(20);
        table.row();
        table.add(loadButton).spaceBottom(20);
        table.row();
        table.add(exitButton);
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        //super
        super.render(delta);
        //set debug
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
        //create multiplexer
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        //create input processor
        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case (Input.Keys.ESCAPE):
                        game.setScreen(new WorldEditorScreen(game, world));
                        break;
                }
                return super.keyDown(keycode);
            }
        };
        //add processors to multiplexer
        inputMultiplexer.addProcessor(inputAdapter);
        inputMultiplexer.addProcessor(stage);
        //set input processor
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

    private void getFilePathAndLoadWorld(){
        throw new NotImplementedException();
        //TODO add file handling
    }

    private void save() {
        if (world.getName().equals("NewWorld")) {
            saveWorldWithNewName();
        } else {
            world.saveWorld();
        }
    }

    private void exit() {
        Dialog dialog = new Dialog("Exit without saving ?", game.skin) {
            protected void result(Object obj) {
                if ((boolean)obj) {
                    game.setScreen(new MenueScreen(game));
                } else {
                    this.hide();
                     saveWorldWithNewName();
                }
            }
        };

        dialog.button("Yes", true);
        dialog.button("No", false);
        dialog.key(Input.Keys.ENTER, true);
        dialog.show(stage);
    }

    private void saveWorldWithNewName() {
        Input.TextInputListener inputListener = new Input.TextInputListener() {
            @Override
            public void input(String text) {
                world.setName(text);
                world.saveWorld();
            }

            @Override
            public void canceled() {
                exit();
            }
        };

        Gdx.input.getTextInput(inputListener, "Enter world name","", "");
    }

    private void runSimulation() {
        LiveEngine.getInstance().setWorld(world);
        game.setScreen(new SimulationScreen(game, this));
    }
}
