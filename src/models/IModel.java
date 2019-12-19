package models;

import utils.ModelDragListener;
import utils.ScreenConverter;
import utils.Vector2;

import java.awt.*;

public interface IModel {

    void draw(Graphics2D g, ScreenConverter sc);

    boolean isOnModel(Vector2 v);

    boolean isFocused();

    void setFocused(boolean focused);

    void moveOn(Vector2 v);

    void addText(String str);

    void addDragListener(ModelDragListener listener);
}
