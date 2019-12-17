package models;

import utils.ScreenConverter;
import utils.Vector2;

import java.awt.*;

public class Knot implements IModel {

    private Vector2 center;

    private double width, height;

    private Color customerColor, focusColor;

    private boolean focused;

    public Knot(Vector2 center, double width, double height, Color color) {
        focusColor = Color.RED;
        this.center = center;
        this.width = width;
        this.height = height;
        this.customerColor = color;
    }

    @Override
    public void draw(Graphics2D g, ScreenConverter sc) {
        g.setColor(focused ? focusColor : customerColor);

        int screenX = sc.getScreenX(center.getX() - width / 2);
        int screenY = sc.getScreenY(center.getY() + height / 2);
        // Узнаём высоту и ширину в экранных координатах
        int screenHeight = sc.getScreenY(0) - sc.getScreenY(height);
        int screenWidth = sc.getScreenX(width) - sc.getScreenX(0);

        g.drawLine(sc.getScreenX(center.x), sc.getScreenY(center.y), sc.getScreenX(center.x), sc.getScreenY(center.y));
        g.drawRect(screenX, screenY, screenWidth, screenHeight);
    }

    @Override
    public boolean isOnModel(Vector2 point) {
        if (point.x >= center.x - width / 2 && point.x <= center.x + width / 2 &&
                point.y <= center.y + height / 2 && point.y >= center.y - height / 2)
            return point.x >= center.x - width / 2 && point.x <= center.x + width / 2 &&
                    point.y <= center.y + height / 2 && point.y >= center.y - height / 2;
        else
            return false;
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public void moveOn(Vector2 v) {
        center = center.add(v);
    }
}
