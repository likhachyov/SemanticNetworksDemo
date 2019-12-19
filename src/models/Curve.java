package models;

import utils.ModelDragListener;
import utils.ScreenConverter;
import utils.ScreenPoint;
import utils.Vector2;

import java.awt.*;

public abstract class Curve implements IModel {

    protected Knot startKnot, endKnot;
    protected Vector2 startPoint, endPoint;
    protected Color color;

    public Curve(Knot start, Knot end, Color color) {
        this.color = color;
        this.startKnot = start;
        this.endKnot = end;
        start.addDragListener(v -> {
            startPoint = startPoint.add(v);
        });
        end.addDragListener(v -> {
            endPoint = endPoint.add(v);
        });
        startPoint = start.getLastClickPoint();
        endPoint = end.getLastClickPoint();
    }

    @Override
    public void draw(Graphics2D g, ScreenConverter sc) {
        ScreenPoint start = sc.r2s(startPoint);
        ScreenPoint end = sc.r2s(endPoint);
        g.setColor(color);
        g.drawLine(start.getI(), start.getJ(), end.getI(), end.getJ());
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

    @Override
    public void addText(String str) {

    }

    @Override
    public void addDragListener(ModelDragListener listener) {

    }
}
