package com.mygdx.game.generators;

public class CenteredSquareLiveGenerator extends AbstractLiveGenerator{

    private static final String NAME = "Square Generator";

    private int worldWidth, worldHeight, rectWidth, rectHeight;

    public CenteredSquareLiveGenerator(int worldWidth, int worldHeight, int rectWidth, int rectHeight) {

        if (rectHeight > worldHeight || rectWidth > worldWidth) {
            throw new IllegalArgumentException("radius can not be bigger than worldWidth or worldHeight");
        }
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;
        this.rectHeight = rectHeight;
        this.rectWidth = rectWidth;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public byte[][] generateLiveArray() {
        byte[][] array = new byte[worldWidth][worldHeight];
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                if (x < rectWidth/2+ worldWidth / 2 &&
                        x > (worldWidth - rectWidth)/2 &&
                        y > (worldHeight - rectHeight)/2 &&
                        y < rectHeight/2+ worldHeight /2) {

                    array[x][y] = 1;
                } else {
                    array[x][y] = 0;
                }
            }
        }
        return array;
    }
}
