package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;

public class Settings {

    private int uiTopPadding;
    private boolean isDebugEnabled;
    private Color backroundColor;
    private Color tileColor;

    public Settings() {
        //default values
        uiTopPadding = 50;
        isDebugEnabled = false;
        backroundColor = Color.BLUE;
        tileColor = Color.GREEN;
    }

    public int getUiTopPadding() {
        return uiTopPadding;
    }

    public void setUiTopPadding(int uiTopPadding) {
        this.uiTopPadding = uiTopPadding;
    }

    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        isDebugEnabled = debugEnabled;
    }

    public Color getBackroundColor() {
        return backroundColor;
    }

    public void setBackroundColor(Color backroundColor) {
        this.backroundColor = backroundColor;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }
}
