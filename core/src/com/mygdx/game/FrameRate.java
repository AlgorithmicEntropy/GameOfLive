package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

public class FrameRate implements Disposable{
    long lastTimeCounted;
    private float sinceChange;
    private float frameRate;
    private BitmapFont font;
    private SpriteBatch batch;
    private OrthographicCamera cam;


    public FrameRate(GameOfLive game) {
        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;
        frameRate = Gdx.graphics.getFramesPerSecond();
        font = game.font;
        batch = game.batch;
        cam = game.cam;
    }

    public void resize(int screenWidth, int screenHeight) {
        cam = new OrthographicCamera(screenWidth, screenHeight);
        cam.translate(screenWidth / 2f, screenHeight / 2f);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }

    public void update() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();

        sinceChange += delta;
        if(sinceChange >= 1000) {
            sinceChange = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
    }

    public void render() {
        batch.begin();
        font.draw(batch, (int)frameRate + " fps", 3, Gdx.graphics.getHeight() - 3);
        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}