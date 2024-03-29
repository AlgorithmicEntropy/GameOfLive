package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.world.GameWorld;

public class WorldEditorScreen extends AbstractZoomableScreen {

    private GameOfLive game;
    private GameWorld world;
    private int height;
    private int width;
    private byte[][] worldArray;
    private int squareSize;

    //global vector to reduce gc pressure
    private Vector3 vector3 = new Vector3();

    WorldEditorScreen(GameOfLive game, GameWorld world) {
        super(game);
        this.game = game;
        this.world = world;
        this.height = world.getHeight();
        this.width = world.getWidth();
        worldArray = world.getLiveArray();
    }

    @Override
    public void render(float delta) {
        //super
        super.render(delta);
        //calculate cell dimensions
        int squareHeight = Gdx.graphics.getHeight() / height;
        int squareWidth = Gdx.graphics.getWidth() / width;
        squareSize = Math.min(squareHeight, squareWidth);
        //draw living cells to screen
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(game.settings.getTileColor());
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (worldArray[x][y] == 1)
                    game.shapeRenderer.rect(x*squareSize, y*squareSize, squareSize, squareSize);
            }
        }
        //end shape rendering
        game.shapeRenderer.end();
        //begin shape rendering
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(0, 0, 0,1);
        //render grid lines
        int worldHeight = (int)game.cam.viewportHeight;
        int worldWidth = (int)game.cam.viewportWidth;

        int verticalSpace = (worldWidth / width);
        int horizontalSpace = worldHeight / height;
        int squaredSpace = Math.min(verticalSpace, horizontalSpace);

        for (int x = 0; x < width; x++) {
            game.shapeRenderer.line(0, x * squaredSpace , squaredSpace*width, x * squaredSpace);
        }

        for (int y = 0; y < width+1; y++) {
            game.shapeRenderer.line(y * squaredSpace, 0, y * squaredSpace, squaredSpace*height);
        }
        //end shape rendering
        game.shapeRenderer.end();
        //click handling
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.cam.unproject(vector3);
            int cellX = (int)vector3.x / squareSize;
            int cellY = (int)vector3.y / squareSize;
            if (cellX >= 0 && cellX < width && cellY >= 0 && cellY < height) {
                worldArray[cellX][cellY] = 1;
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.cam.unproject(vector3);
            int cellX = (int)vector3.x / squareSize;
            int cellY = (int)vector3.y / squareSize;
            if (cellX >= 0 && cellX < width && cellY >= 0 && cellY < height) {
                worldArray[cellX][cellY] = 0;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
        //create input processor
        final InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case (Input.Keys.ESCAPE):
                        game.setScreen(new WorldEditorMenuScreen(game, world));
                        break;
                }
                return super.keyDown(keycode);
            }
        };
        //set input processor
        super.inputMultiplexer.addProcessor(inputAdapter);
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
