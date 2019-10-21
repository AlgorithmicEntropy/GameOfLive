package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FrameRate;
import com.mygdx.game.GameOfLive;
import com.mygdx.game.world.GameWorld;

public class WorldEditorScreen extends AbstractGameScreen {

    private GameOfLive game;
    private GameWorld world;
    private int height;
    private int width;
    private byte[][] worldArray;
    private int squareSize;

    public WorldEditorScreen(GameOfLive game, GameWorld world) {
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
        squareSize = squareHeight;
        //draw living cells to screen
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 1, 0,1);
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (worldArray[x][y] == 1)
                    game.shapeRenderer.rect(x*squareWidth, y*squareHeight, squareWidth, squareHeight);
            }
        }
        //end shape rendering
        game.shapeRenderer.end();
        //begin shape rendering
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(0, 0, 0,1);
        //render grid lines
        int verticalSpace = Gdx.graphics.getWidth() / width;
        for (int x = 0; x < width; x++) {
            game.shapeRenderer.line(0, x * verticalSpace, Gdx.graphics.getWidth(), x * verticalSpace);
        }
        int horizontalSpace = Gdx.graphics.getHeight() / height;
        for (int y = 0; y < width; y++) {
            game.shapeRenderer.line(y * verticalSpace, 0, y * verticalSpace, Gdx.graphics.getHeight());
        }
        //end shape rendering
        game.shapeRenderer.end();
        //click handeling
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.cam.unproject(vector);
            int cellX = (int)vector.x / squareSize;
            int cellY = (int)vector.y / squareSize;
            if (cellX >= 0 && cellX < width && cellY >= 0 && cellY < height) {
                worldArray[cellX][cellY] = 1;
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Vector3 vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.cam.unproject(vector);
            int cellX = (int)vector.x / squareSize;
            int cellY = (int)vector.y / squareSize;
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
                        game.setScreen(new WorldEditorMenueScreen(game, world));
                        break;
                }
                return super.keyDown(keycode);
            }
        };
        //set input processor
        Gdx.input.setInputProcessor(inputAdapter);
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
