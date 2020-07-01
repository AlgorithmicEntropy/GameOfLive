package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;

public class Settings {

    private static Settings instance;

    private boolean isDebugEnabled;
    private Color backgroundColor;
    private Color tileColor;
    private String saveDirectory;

    public static Settings getInstance() {
        if (instance != null)
            return instance;
        return new Settings();
    }

    private Settings() {
        //default values
        isDebugEnabled = false;
        backgroundColor = Color.DARK_GRAY;
        tileColor = Color.CORAL;
        saveDirectory = "saves";
    }

    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        isDebugEnabled = debugEnabled;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }

    public String getSaveDirectory() {
        return saveDirectory;
    }
}
