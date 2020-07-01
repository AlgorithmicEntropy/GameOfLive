package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class ColorPicker extends Dialog {

    private static final float DIALOG_HEIGHT = 500;
    private static final float DIALOG_WIDTH = 600;

    private ShapeRenderer shapeRenderer;

    private Slider sliderR;
    private Slider sliderG;
    private Slider sliderB;
    private Slider sliderA;
    private ColorField colorField;

    public ColorPicker(String title, Skin skin, Color startColor) {
        super(title, skin);

        this.shapeRenderer = new ShapeRenderer();
        this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        this.setPosition((float)Gdx.graphics.getWidth() / 2 - DIALOG_WIDTH/2,
                (float)Gdx.graphics.getHeight() / 2 - DIALOG_HEIGHT/2);

        //labels
        Label labelR = new Label("Red", skin);
        Label labelG = new Label("Green", skin);
        Label labelB = new Label("Blue", skin);
        Label labelA = new Label("Alpha", skin);
        Label previewLabel = new Label("Preview", skin);
        //number fields
        final TextField textFieldR = new TextField("", skin);
        final TextField textFieldG = new TextField("", skin);
        final TextField textFieldB = new TextField("", skin);
        final TextField textFieldA = new TextField("", skin);
        //slider change listener
        ChangeListener sliderChangeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float r = sliderR.getValue();
                float g = sliderG.getValue();
                float b = sliderB.getValue();
                float a = sliderA.getValue();

                textFieldR.setText(Float.toString(r));
                textFieldG.setText(Float.toString(g));
                textFieldB.setText(Float.toString(b));
                textFieldA.setText(Float.toString(a));

                colorField.setColor(new Color(r, g, b, a));
            }
        };
        //color sliders
        sliderR = new Slider(0, 1, 0.004f, false, skin);
        sliderR.setColor(Color.RED);
        sliderR.addListener(sliderChangeListener);

        sliderG = new Slider(0, 1, 0.004f, false, skin);
        sliderG.setColor(Color.GREEN);
        sliderG.addListener(sliderChangeListener);

        sliderB = new Slider(0, 1, 0.004f, false, skin);
        sliderB.setColor(Color.BLUE);
        sliderB.addListener(sliderChangeListener);

        sliderA = new Slider(0, 1, 0.004f, false, skin);
        sliderA.addListener(sliderChangeListener);

        //color field
        colorField = new ColorField(0,0,100,100, Color.BLACK);

        //save button
        TextButton saveButton = new TextButton("Save", skin);
        this.button(saveButton, new Color());

        //cancel button
        TextButton cancelButton = new TextButton("Cancel", skin);

        //save and exit on enter key
        this.key(Input.Keys.ENTER, new Color(
                sliderR.getValue(),
                sliderG.getValue(),
                sliderB.getValue(),
                sliderA.getValue()
        ));

        Table table = this.getContentTable();
        table.columnDefaults(3);
        table.defaults().pad(10);
        table.add(labelR);
        table.add(sliderR);
        table.add(textFieldR)
                .row();
        table.add(labelG);
        table.add(sliderG);
        table.add(textFieldG)
                .row();
        table.add(labelB);
        table.add(sliderB);
        table.add(textFieldB)
                .row();
        table.add(labelA);
        table.add(sliderA);
        table.add(textFieldA)
                .row();
        table.add(previewLabel);
        table.add(colorField)
                .row();

        if(startColor != null) {
            sliderR.setValue(startColor.r);
            sliderG.setValue(startColor.g);
            sliderB.setValue(startColor.b);
            sliderA.setValue(startColor.a);
        } else {
            //default
            sliderA.setValue(0.5f);
        }
    }

    protected Color getCurrentColor()
    {
        return new Color(
                sliderR.getValue(),
                sliderG.getValue(),
                sliderB.getValue(),
                sliderA.getValue()
        );
    }
}
