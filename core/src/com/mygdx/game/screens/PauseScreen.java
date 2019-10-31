package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import javafx.scene.control.Tab;

public class PauseScreen extends AbstractGameScreen {

    private GameOfLive game;
    private ScreenAdapter simulationSourceScreen;
    private Stage stage;
    private Table menueTable;
    //ui elements
    private Label bannerLabel;
    private TextButton continueButton;
    private TextButton returnButton;
    private TextButton resetButton;

    public PauseScreen(final GameOfLive game, final ScreenAdapter simulationSourceScreen) {
        super(game);
        this.simulationSourceScreen = simulationSourceScreen;
        this.game = game;
        //ui
        stage = new Stage(game.viewport);
        menueTable = new Table();
        //create ui elements
        bannerLabel = new Label("Game Paused", game.skin);
        bannerLabel.setFontScale(4);

        continueButton = new TextButton("Resume", game.skin);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SimulationScreen(game, simulationSourceScreen));
                dispose();
            }
        });

        returnButton = new TextButton("Exit", game.skin);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (simulationSourceScreen == null) {
                    game.setScreen(new MenueScreen(game));
                } else  {
                    game.setScreen(simulationSourceScreen);
                }
                dispose();
            }
        });

        resetButton = new TextButton("Reset", game.skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!LiveEngine.getInstance().isCustomWorld()) {
                    LiveEngine.getInstance().regenerate();
                    game.setScreen(new SimulationScreen(game, simulationSourceScreen));
                    dispose();
                } else {
                    LiveEngine.getInstance().reloadWorld();
                    game.setScreen(new SimulationScreen(game, simulationSourceScreen));
                    dispose();
                }
            }
        });

        menueTable.align(Align.top);
        menueTable.padTop(game.settings.getUiTopPadding());
        menueTable.add(bannerLabel).spaceBottom(100);
        menueTable.row();
        menueTable.add(continueButton);
        menueTable.row();
        menueTable.add(returnButton).spaceTop(20);
        menueTable.row();
        menueTable.add(resetButton).spaceTop(20);
        menueTable.setFillParent(true);
        //add to stage
        stage.addActor(menueTable);
    }

    @Override
    public void render(float delta) {
        //super
        super.render(delta);
        //set debug
        menueTable.setDebug(game.settings.isDebugEnabled());
        //draw and update stage
        stage.draw();
        stage.act();
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
        //set input processor to stage
        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                switch (keycode) {
                    case (Input.Keys.ESCAPE):
                        game.setScreen(new SimulationScreen(game, simulationSourceScreen));
                }
                return super.keyDown(keycode);
            }
        };
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, inputAdapter);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void hide() {
        super.hide();
        //clear input processor
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
