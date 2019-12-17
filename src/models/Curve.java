package models;

import utils.ScreenConverter;
import utils.Vector2;

import java.awt.*;

public class Curve implements IModel {

    @Override
    public void draw(Graphics2D g, ScreenConverter sc) {

    }

    @Override
    public boolean isOnModel(Vector2 v) {
        return false;
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public void moveOn(Vector2 v) {

    }
}
