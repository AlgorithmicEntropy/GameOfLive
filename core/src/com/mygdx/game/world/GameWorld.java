package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.util.Settings;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

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

    //json deserialization constructor
    private GameWorld() {

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

    public void saveWorld() throws FileAlreadyExistsException {
        FileHandle file = Gdx.files.local(Settings.getInstance().getSaveDirectory() +"/" + name);
        if (file.exists()) {
            throw new FileAlreadyExistsException("save file already exists");
        }
        String worldString = serializeWorld();
        file.writeString(worldString, false);
    }

    public boolean saveIfExistsWorld() {
        FileHandle file = Gdx.files.local(Settings.getInstance().getSaveDirectory() + "/" + name);
        if (file.exists()) {
            String worldString = serializeWorld();
            file.writeString(worldString, false);
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String serializeWorld() {
        //json
        //TODO add custom world array serialisation and save compression (e.g. 10001 == 1301)
        Json json = new Json();
        String worldJson = json.toJson(this);

        if (Settings.getInstance().isDebugEnabled())
            System.out.println(json.prettyPrint(worldJson));

        return worldJson;
    }

    private static GameWorld deserializeWorld(String worldString) {
        Json json = new Json();
        return json.fromJson(GameWorld.class, worldString);
    }

    public static GameWorld loadWorldFromSaves(String name) throws FileNotFoundException {
        FileHandle file = Gdx.files.local(Settings.getInstance().getSaveDirectory() + "/" + name);
        if (!file.exists()) {
            throw new FileNotFoundException("save file not found");
        }
        String content = file.readString();
        //deserialize
        return deserializeWorld(content);
    }

    public void clear() {
        liveArray = new byte[width][height];
    }
}
