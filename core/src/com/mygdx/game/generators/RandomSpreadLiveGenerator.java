package com.mygdx.game.generators;
import java.util.Random;

public class RandomSpreadLiveGenerator extends AbstractLiveGenerator {

    private static final String NAME = "Random Generator";

    private int fillPercentage;
    private int height, width;

    public RandomSpreadLiveGenerator(int width, int height, int fillPercentage) {
        super();
        this.fillPercentage = fillPercentage;
        this.height = height;
        this.width = width;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public byte[][] generateLiveArray() {
        Random rand = new Random();
        byte[][] array = new byte[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int randInt = rand.nextInt(100);
                if (randInt < fillPercentage) {
                    array[x][y] = 1;
                } else {
                    array[x][y] = 0;
                }
            }
        }

        return array;
    }
}
