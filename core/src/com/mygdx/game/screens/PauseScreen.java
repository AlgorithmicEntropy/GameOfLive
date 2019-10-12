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
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import javafx.scene.control.Tab;

public class PauseScreen extends ScreenAdapter {

    private GameOfLive game;
    private Stage stage;
    private Table menueTable;
    //ui elements
    private Label bannerLabel;
    private TextButton continueButton;
    private TextButton returnButton;
    private TextButton resetButton;

    public PauseScreen(final GameOfLive game) {
        super();
        this.game = game;
        stage = new Stage(game.viewport);
        menueTable = new Table();
        //create ui elements
        bannerLabel = new Label("Game Paused", game.skin);
        bannerLabel.setFontScale(2*GameOfLive.UI_SCALE);

        continueButton = new TextButton("Resume", game.skin);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SimulationScreen(game));
                dispose();
            }
        });

        returnButton = new TextButton("Return to Main Menue", game.skin);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenueScreen(game));
                dispose();
            }
        });

        resetButton = new TextButton("Reset", game.skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LiveEngine.getInstance().regenerate();
                game.setScreen(new SimulationScreen(game));
                dispose();
            }
        });

        menueTable.setDebug(true);
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
                        game.setScreen(new SimulationScreen(game));
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
