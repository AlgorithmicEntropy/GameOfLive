package com.mygdx.game.world;

public class GameWorld {

    private String name;
    private int height, width;
    private byte[][] liveArray;

    public GameWorld(int width, int height, String name) {
        this.name = name;
        this.height = height;
        this.width = width;
        liveArray = new byte[width][height];
    }

    public GameWorld(int width, int height) {
        //deafult name
        this.name = "NewWorld";
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
        String worldString = serializeWorld();

    }

    public void loadWorld(String filePath) {
        //TODO loading world from file
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String serializeWorld() {
        StringBuilder stringBuilder = new StringBuilder();
        //add header
        stringBuilder.append(name);
        stringBuilder.append(':');
        //append array serialisation
        for (int x = 0; x < width;x++) {
            for (int y = 0; y < height; y++) {
                stringBuilder.append(liveArray[x][y]);
            }
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
