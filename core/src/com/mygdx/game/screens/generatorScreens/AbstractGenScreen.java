package com.mygdx.game.screens.generatorScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.screens.AbstractGameScreen;
import com.mygdx.game.screens.MenuScreen;

public abstract class AbstractGenScreen extends AbstractGameScreen {

    protected GameOfLive game;
    Table table;
    Stage stage;

    int worldHeight;
    int worldWidth;


    AbstractGenScreen(GameOfLive game, int worldHeight, int worldWidth) {
        super(game);
        this.game = game;
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;
        table = new Table();
        stage = new Stage(game.viewport);
    }

    @Override
    public void render(float delta) {
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

    @Override
    public void drawFPS() {
        //super.drawFPS();
        // hide fps
    }

    abstract void generateWorld();
}
