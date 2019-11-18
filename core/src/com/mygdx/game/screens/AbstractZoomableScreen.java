package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.mygdx.game.GameOfLive;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AbstractZoomableScreen extends AbstractGameScreen {

    private static final int CAM_MOVE_VERTICAL_AMOUNT = 50;
    private static final int CAM_MOVE_HORIZONTAL_AMOUNT = 50;
    private static final float ZOOM_DIVIDER = 30;
    BoundingBox[] border = new BoundingBox[4];

    private GameOfLive game;

    public AbstractZoomableScreen(GameOfLive game) {
        super(game);
        this.game = game;
        //def bounding boxes
        //TODO fix outer box to prevent cam from moving to far
        border[0] = new BoundingBox(new Vector3(-1,-1,0), new Vector3(-1, Gdx.graphics.getHeight()+1, 0));
        border[1] = new BoundingBox(new Vector3(-1,-1,0), new Vector3(Gdx.graphics.getWidth()+1, -1, 0));
        border[2] = new BoundingBox(new Vector3(Gdx.graphics.getWidth()+1,Gdx.graphics.getHeight()+1,0),
                new Vector3(Gdx.graphics.getWidth(), -1, 0));
        border[3] = new BoundingBox(new Vector3(Gdx.graphics.getWidth()+1,Gdx.graphics.getHeight()+1,0),
                new Vector3(-1, Gdx.graphics.getHeight(), 0));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //input handling
        Vector3 oldPlace = game.cam.position;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            game.cam.translate(0, CAM_MOVE_VERTICAL_AMOUNT * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            game.cam.translate(-CAM_MOVE_HORIZONTAL_AMOUNT * delta, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.cam.translate(0, -CAM_MOVE_VERTICAL_AMOUNT * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            game.cam.translate(CAM_MOVE_HORIZONTAL_AMOUNT * delta, 0);
        }
        //check boundry
        for (BoundingBox box : border) {
            if (game.cam.frustum.boundsInFrustum(box)) {
                game.cam.position.set(oldPlace);
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
        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean scrolled(int amount) {
                game.cam.zoom += amount / ZOOM_DIVIDER;
                //clamp zoom level
                game.cam.zoom = MathUtils.clamp(game.cam.zoom, 0.1f, Gdx.graphics.getWidth()/game.cam.viewportWidth);
                return super.scrolled(amount);
            }
        };

        switch (Gdx.app.getType()) {
            case Desktop:
                super.inputMultiplexer.addProcessor(inputAdapter);
                break;
            case Android:
                super.inputMultiplexer.addProcessor(new GestureDetector(new AndroidGestureListener()));
                break;
            default:
                Gdx.app.error("Error", "no zoom input processor specified", new NotImplementedException());
        }
    }

    @Override
    public void hide() {
        super.hide();
        game.cam.zoom = 1;
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

    //zoom gesture Detection
    private class AndroidGestureListener implements GestureDetector.GestureListener {

        public AndroidGestureListener() {
        }

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean tap(float x, float y, int count, int button) {
            return false;
        }

        @Override
        public boolean longPress(float x, float y) {
            return false;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            return false;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean zoom(float initialDistance, float distance) {
            game.cam.zoom += distance / ZOOM_DIVIDER;
            //clamp zoom level
            game.cam.zoom = MathUtils.clamp(game.cam.zoom, 0.1f, Gdx.graphics.getWidth()/game.cam.viewportWidth);
            return false;
        }

        @Override
        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            return false;
        }

        @Override
        public void pinchStop() {

        }
    }
}
