package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.GameOfLive;

public class PauseScreen extends ScreenAdapter {

    private GameOfLive game;
    private Stage stage;
    //ui elements
    private Label bannerLabel;
    private TextButton continueButton;
    private TextButton returnButton;
    //ui element positions
    private static final int bannerLabelY = Gdx.graphics.getHeight() - 20;
    private static final int bannerLabelX = (int)(Gdx.graphics.getWidth() * 0.5f);

    private static final int returnButtonX = (int)(Gdx.graphics.getWidth() * 0.5f) - 100;
    private static final int returnButtonY = (int)(Gdx.graphics.getHeight() * 0.3);

    private static final int continueButtonX = (int)(Gdx.graphics.getWidth() * 0.5f) + 100;
    private static final int continueButtonY = (int)(Gdx.graphics.getHeight() * 0.3);

    public PauseScreen(final GameOfLive game) {
        super();
        this.game = game;
        stage = new Stage();
        //create ui elements
        game.font.setColor(Color.WHITE);
        bannerLabel = new Label("Game Paused", game.skin);
        bannerLabel.setFontScale(2);
        bannerLabel.setPosition(bannerLabelX, bannerLabelY);

        continueButton = new TextButton("Resume", game.skin);
        continueButton.setPosition(continueButtonX, continueButtonY);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SimulationScreen(game));
                dispose();
            }
        });

        returnButton = new TextButton("Return to Main Menue", game.skin);
        returnButton.setPosition(returnButtonX, returnButtonY);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenueScreen(game));
                dispose();
            }
        });
        //add to stage
        stage.addActor(bannerLabel);
        stage.addActor(continueButton);
        stage.addActor(returnButton);
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
