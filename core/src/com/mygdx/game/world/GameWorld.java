package com.mygdx.game.world;

public class GameWorld {

    private int height, width;
    private byte[][] liveArray;

    public GameWorld(int width, int height) {
        this.height = height;
        this.width = width;
        liveArray = new byte[width][height];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public byte[][] getLiveArray() {
        return liveArray;
    }

    public void setLiveArray(byte[][] liveArray) {
        this.liveArray = liveArray;
    }

    public void saveWorld() {
        //TODO world saving and file handling
    }

    public void loadWorld(String filePath) {
        //TODO loading world from file
    }
}
