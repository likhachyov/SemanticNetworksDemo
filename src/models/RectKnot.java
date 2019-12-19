package models;

import utils.Vector2;

import java.awt.*;

public class RectKnot extends Knot {

    public RectKnot(Vector2 center, double width, double height, Color color) {
        super(center, width, height, color);
    }

    @Override
    public boolean isOnBottomBorder(Vector2 point) {
        return point.y <= center.y - height / 2 + stepToBorder && point.y >= center.y - height / 2 - stepToBorder
                && point.x >= center.x - width / 2 && point.x <= center.x + width / 2;
    }

    @Override
    public boolean isOnTopBorder(Vector2 point) {
        return point.y <= center.y + height / 2 + stepToBorder && point.y >= center.y + height / 2 - stepToBorder
                && point.x >= center.x - width / 2 && point.x <= center.x + width / 2;
    }

    @Override
    public boolean isOnLeftBorder(Vector2 point) {
        return point.y <= center.y + height / 2 && point.y >= center.y - height / 2
                && point.x >= center.x - width / 2 - stepToBorder && point.x <= center.x - width / 2 + stepToBorder;
    }

    @Override
    public boolean isOnRightBorder(Vector2 point) {
        return point.y <= center.y + height / 2 && point.y >= center.y - height / 2
                && point.x >= center.x + width / 2 - stepToBorder && point.x <= center.x + width / 2 + stepToBorder;
    }
}
