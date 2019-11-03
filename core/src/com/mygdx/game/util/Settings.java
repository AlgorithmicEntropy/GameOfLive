package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;

public class Settings {

    private static Settings instance;
    //static internal constants
    private static final int UI_TOP_PADDING = 50;
    private static final int UI_BUTTON_SIDE_PADDING = 20;
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

    public static int getUiTopPadding() {
        return UI_TOP_PADDING;
    }

    public static int getUiButtonSidePadding() {
        return UI_BUTTON_SIDE_PADDING;
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
