package com.mygdx.game.screens.generatorScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.generators.RandomSpreadLiveGenerator;
import com.mygdx.game.screens.SimulationScreen;
import com.mygdx.game.util.Settings;

import static java.lang.Integer.parseInt;

public class RandomGenScreen extends AbstractGenScreen {
    private TextField fillFactorField;

    public RandomGenScreen(GameOfLive game, int worldHeight, int worldWidth) {
        super(game, worldHeight, worldWidth);
        //ui elements
        Label fillLabel = new Label("Fill Percentage:", game.skin);
        fillFactorField = new TextField("30", game.skin);
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
        table.padTop(Settings.getUiTopPadding());
        //TODO add banner/description
        table.add(fillLabel).padBottom(20);
        table.add(fillFactorField).padBottom(20);
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
    public void show() {
        super.show();
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
    public void drawFPS() {
        super.drawFPS();
    }

    @Override
    void generateWorld() {
        try {
            int percentage = parseInt(fillFactorField.getText());

            RandomSpreadLiveGenerator generator = new RandomSpreadLiveGenerator(worldWidth, worldHeight, percentage);
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
        }
    }
}
