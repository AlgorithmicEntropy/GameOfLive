package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.world.GameWorld;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class SavedWorldsSelectionScreen extends AbstractGameScreen {

    private GameOfLive game;

    private HashMap<String, GameWorld> gameWorldDictionary = new HashMap<>();
    //UI Elements
    private Stage stage;
    private Table table;

    private Label titelLabel;
    private ScrollPane scrollPane;
    private Stack stack;

    public SavedWorldsSelectionScreen(GameOfLive game) {
        super(game);
        this.game = game;
        //load saved worlds
        FileHandle[] savedFiles = Gdx.files.local(game.settings.getSaveDirectory()).list();
        for (FileHandle file : savedFiles) {
            try {
                GameWorld world = GameWorld.loadWorldFromSaves(file.name());
                gameWorldDictionary.put(world.getName(), world);
            } catch (FileNotFoundException ex) {
                Gdx.app.log("Exception", ex.getMessage());
            }
        }
        //create ui
        table = new Table();
        stage = new Stage();
        stack = new Stack();

        titelLabel = new Label("Select world to load", game.skin);
        titelLabel.setFontScale(2);

        //create world sub tables
        if (!gameWorldDictionary.isEmpty()) {
            for (final String worldName : gameWorldDictionary.keySet()) {
                Table subTable = new Table();
                Label label = new Label(worldName, game.skin);
                TextButton button = new TextButton("load", game.skin);
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        loadWorld(gameWorldDictionary.get(worldName));
                    }
                });
                //add to sub table
                subTable.align(Align.top);
                subTable.add(label).align(Align.left).padRight(10);
                subTable.add(button).align(Align.right);
                stack.addActor(subTable);
            }
        } else {
            Gdx.app.log("Warning", "no saves found");
        }

        //add to scroll pane
        scrollPane = new ScrollPane(stack);

        //set UI Layout Table
        table.align(Align.top);
        table.padTop(game.settings.getUiTopPadding());
        table.add(titelLabel).spaceBottom(50);
        table.row();
        //table.addActor(scrollPane);
        table.add(scrollPane);
        //set actor & fill
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

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
                        game.setScreen(new MenueScreen(game));
                        break;
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

    private void loadWorld(GameWorld world) {
        LiveEngine.getInstance().setWorld(world);
        game.setScreen(new SimulationScreen(game, this));
    }
}
