package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameOfLive;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.LiveEngine;
import com.mygdx.game.generators.AbstractLiveGenerator;
import com.mygdx.game.generators.CenteredSquareLiveGenerator;
import com.mygdx.game.generators.RandomSpreadLiveGenerator;
import com.mygdx.game.world.GameWorld;

public class MenueScreen extends ScreenAdapter {

    private GameOfLive game;
    private Texture startNewSimulation;
    private static final float START_NEW_Y = Gdx.graphics.getHeight() * 0.8f;
    private float startNewX;
    //temp screen clicked vector
    private Vector3 clickCords = new Vector3();

    public MenueScreen(GameOfLive game)
    {
        super();
        this.game = game;
        //load textures
        startNewSimulation = new Texture("PlayButton.png");
        //calculate texture coordinates
        startNewX = Gdx.graphics.getWidth() * 0.5f - startNewSimulation.getWidth();
    }

    @Override
    public void render(float delta) {
        //super
        super.render(delta);
        //clear screen and set colour
        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update cam
        game.cam.update();
        game.batch.setProjectionMatrix(game.cam.combined);

        game.batch.setTransformMatrix(game.cam.view);
        game.batch.setProjectionMatrix(game.cam.projection);

        game.batch.begin();
        game.batch.draw(startNewSimulation, startNewX, START_NEW_Y );
        game.batch.end();

        //check button clicked
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            clickCords.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.cam.unproject(clickCords);
            int xClicked = (int) clickCords.x;
            int yClicked = (int) clickCords.y;
            //System.out.println(xClicked + "         " + yClicked);
            if (xClicked > startNewX && xClicked < startNewX + startNewSimulation.getWidth() && yClicked > START_NEW_Y && yClicked < START_NEW_Y + startNewSimulation.getHeight()) {
                game.setScreen(new GeneratorSelectionScreen(game));
            }
        }
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
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

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

    private void quickStart() {
        /*
        AbstractLiveGenerator generator = new CenteredSquareLiveGenerator(100, 100, 40);
        LiveEngine engine = LiveEngine.getInstance();
        engine.generate(generator);
        game.setScreen(new SimulationScreen(game));
        */
        game.setScreen(new WorldEditorScreen(game, new GameWorld(10, 10)));
    }
}
