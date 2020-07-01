package com.mygdx.game.screens.generatorScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.generators.CenteredSquareLiveGenerator;
import com.mygdx.game.screens.SimulationScreen;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Settings;

public class SquareGenScreen extends AbstractGenScreen {
    private TextField widthField;
    private TextField heightField;

    public SquareGenScreen(GameOfLive game, int worldHeight, int worldWidth) {
        super(game, worldHeight, worldWidth);
        //ui elements
        Label heightLabel = new Label("Height", game.skin);
        Label widthLabel = new Label("Width", game.skin);
        widthField = new TextField("10", game.skin);
        heightField = new TextField("10", game.skin);
        //buttons
        Button generateButton = new TextButton("Generate", game.skin);
        generateButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                generateWorld();
            }
        });
        //table layout
        table.columnDefaults(2);
        table.align(Align.top);
        table.padTop(Constants.UI_TOP_PADDING);
        //TODO add banner/description
        table.add(heightLabel).padBottom(20);
        table.add(heightField).padBottom(20);
        table.row();
        table.add(widthLabel).padBottom(20);
        table.add(widthField).padBottom(20);
        table.row();
        table.add(generateButton).padBottom(20);
        table.setFillParent(true);
        //add to stage
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    void generateWorld() {
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());

            if (width > worldWidth || height > worldHeight
            ) {
                throw new IllegalArgumentException();
            }

            CenteredSquareLiveGenerator generator = new CenteredSquareLiveGenerator(worldWidth, worldHeight, width, height);
            LiveEngine.getInstance().generate(generator);
            //post runnable to draw on correct thread
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new SimulationScreen(game, null));
                }
            });
        } catch (NumberFormatException ex) {
            Dialog dialog = new Dialog("Invalid input", game.skin);
            dialog.button("OK");
            dialog.show(stage);
            Gdx.app.log("Exception", "parsing input");
        } catch (IllegalArgumentException ex) {
            Dialog dialog = new Dialog("Invalid input", game.skin);
            dialog.button("OK");
            dialog.show(stage);
            Gdx.app.log("Exception", "illegal input parameters");
        }

    }
}
