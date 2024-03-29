package com.mygdx.game;


import com.mygdx.game.generators.AbstractLiveGenerator;
import com.mygdx.game.world.GameWorld;

public class LiveEngine {

    private static LiveEngine instance;
    private AbstractLiveGenerator generator;
    private GameWorld world;
    private byte[][] liveArray;
    private int height, width;
    private long cycleCounter;

    private LiveEngine() {

    }

    public static LiveEngine getInstance() {
        if (instance == null)
            instance = new LiveEngine();
        return instance;
    }

    public void setArraySize(int x, int y) {
        width = x;
        height = y;
        liveArray = new byte[x][y];
    }

    public byte[][] getLiveArray() {
        return liveArray;
    }

    private void setLiveArray(byte[][] liveArray) {
        this.liveArray = liveArray;
        this.height = liveArray[0].length;
        this.width = liveArray.length;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void calculateNextState()
    {
        liveArray = evoStep(liveArray);
    }

    private byte[][] evoStep(byte[][] livearray)
    {
        //cycle counter
        cycleCounter++;
        //temp array
        byte[][] copyArray = new byte[width][height];

        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                int nb = getNeighbors(x, y);

                if(nb == 3)
                {
                    copyArray[x][y] = 1;
                    continue;
                }

                if(nb < 2)
                {
                    copyArray[x][y] = 0;
                    continue;
                }

                if(nb == 3 || nb == 2)
                {
                    copyArray[x][y] = livearray[x][y];
                    continue;
                }
                if(nb > 3)
                {
                    copyArray[x][y] = 0;
                    continue;
                }
            }
        }
        return copyArray;
    }


    private int getNeighbors(int x, int y) {
        int nb = 0;

        if(x != 0 && liveArray[x-1][y] == 1)  //y x-1
        {
            nb++;
        }
        if(y != 0 && liveArray[x][y-1] == 1)  //y-1 x
        {
            nb++;
        }
        if(x < width-1 && liveArray[x+1][y] == 1) //y x+1
        {
            nb++;
        }
        if(y < height-1 && liveArray[x][y+1] == 1) //y+1 x
        {
            nb++;
        }
        if(x != 0 && y != 0 && liveArray[x-1][y-1] == 1) //y-1 x-1
        {
            nb++;
        }
        if(y < height-1 && x < width-1 && liveArray[x+1][y+1] == 1) // y+1 x+1
        {
            nb++;
        }
        if(x != 0 && y < height-1 && liveArray[x-1][y+1] == 1) //y+1 x-1
        {
            nb++;
        }
        if(x < width-1 && y != 0 && liveArray[x+1][y-1] == 1) //y-1 x+1
        {
            nb++;
        }

        return nb;
    }

    public void regenerate() {
        setLiveArray(generator.generateLiveArray());
        cycleCounter = 0;
    }

    public void generate(AbstractLiveGenerator generator) {
        this.generator = generator;
        setLiveArray(generator.generateLiveArray());
        cycleCounter = 0;
    }

    public GameWorld getWorld() {
        return world;
    }

    public void setWorld(GameWorld world) {
        this.world = world;
        setLiveArray(world.getLiveArray());
    }

    public void reloadWorld() {
        liveArray = world.getLiveArray();
        cycleCounter = 0;
    }

    public boolean isCustomWorld() {
        if (generator == null)
            return true;
        return false;
    }

    public long getCycleCounter() {
        return cycleCounter;
    }
}
