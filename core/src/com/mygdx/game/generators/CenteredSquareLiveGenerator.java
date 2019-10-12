package com.mygdx.game.generators;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class CenteredSquareLiveGenerator extends AbstractLiveGenerator{

    private int width, height;
    private int radius;

    public CenteredSquareLiveGenerator(int width, int height, int radius) {

        if (radius > height || radius > width) {
            throw new IllegalArgumentException("radius can not be bigger than width or hight");
        }
        this.radius = radius;
        this.height = height;
        this.width = width;
    }

    @Override
    public byte[][] generateLiveArray() {
        byte[][] array = new byte[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x < radius/2+width/2 && x > (width-radius)/2 && y > (height-radius)/2 && y < radius/2+height/2) {
                    array[x][y] = 1;
                } else {
                    array[x][y] = 0;
                }
            }
        }
        return array;
    }
}
