package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameOfLive;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.world.GameWorld;

public class MenueScreen extends AbstractGameScreen {

    private GameOfLive game;
    private Texture startNewSimulation;
    private static final float START_NEW_Y = Gdx.graphics.getHeight() * 0.8f;
    private float startNewX;
    //temp screen clicked vector
    private Vector3 clickCords = new Vector3();

    public MenueScreen(GameOfLive game)
    {
        super(game);

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
        //draw
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
