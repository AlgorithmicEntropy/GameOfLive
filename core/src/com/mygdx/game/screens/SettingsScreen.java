package com.mygdx.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.ui.ColorPicker;
import com.mygdx.game.util.Constants;

public class SettingsScreen extends AbstractGameScreen {

    private Table table;
    private Stage stage;

    private CheckBox debugCheckBox;

    //TODO add settings screen
    private GameOfLive game;

    SettingsScreen(final GameOfLive game) {
        super(game);
        this.game = game;
        this.table = new Table();
        this.stage = new Stage(game.viewport);

        debugCheckBox = new CheckBox("Debug Mode", game.skin);
        debugCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.settings.setDebugEnabled(debugCheckBox.isChecked());
            }
        });

        //tile color
        TextButton cellColorButton = new TextButton("Select Cell Color", game.skin);
        cellColorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Color currentColor = game.settings.getTileColor();
                ColorPicker colorPicker= new ColorPicker("Select Color", game.skin, currentColor) {
                    protected void result(Object object) {
                        game.settings.setTileColor(getCurrentColor());
                    }
                };
                colorPicker.show(stage);
            }
        });

        //background color
        TextButton backgroundColorButton = new TextButton("Select Background Color", game.skin);
        backgroundColorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Color currentColor = game.settings.getBackgroundColor();
                ColorPicker colorPicker= new ColorPicker("Select Color", game.skin, currentColor) {
                    protected void result(Object object) {
                        game.settings.setBackgroundColor(getCurrentColor());
                    }
                };
                colorPicker.show(stage);
            }
        });

        //save games path
        Label savePathLabel = new Label("Saves path", game.skin);
        TextField savePathField = new TextField(game.settings.getSaveDirectory(), game.skin);

        table.columnDefaults(2);
        table.defaults().pad(10);
        table.align(Align.top);
        table.padTop(Constants.UI_TOP_PADDING);
        table.add(debugCheckBox)
                .colspan(2)
                .row();
        table.add(cellColorButton)
                .padTop(20)
                .row();
        table.add(backgroundColorButton)
                .padTop(20)
                .row();
        table.add(savePathLabel)
                .padTop(20);
        table.add(savePathField)
                .padTop(20)
                .row();
        table.setFillParent(true);
        //add to stage
        stage.addActor(table);

        //TODO implementation for save path settings
        //TODO button layout
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //debug
        table.setDebug(game.settings.isDebugEnabled());

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
                    case (Input.Keys.ESCAPE):
                        game.setScreen(new MenuScreen(game));
                        break;
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
