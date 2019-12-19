package models;

import utils.ModelDragListener;
import utils.ScreenConverter;
import utils.Vector2;

import java.awt.*;

public abstract class Knot implements IModel {

    protected Vector2 center;

    protected double width, height;

    protected Color customerColor, focusColor;

    protected boolean focused;

    protected String text;

    protected double stepToBorder = 3;

    protected boolean resizedBottom, resizedTop, resizedLeft, resizedRight;

    protected Vector2 lastClickPoint;

    protected ModelDragListener dragListener;


    public Knot(Vector2 center, double width, double height, Color color) {
        focusColor = Color.RED;
        this.center = center;
        this.width = width;
        this.height = height;
        this.customerColor = color;
    }

    public void resizeOn(Vector2 delta) {
        if (resizedBottom) {
            center.y += delta.y / 2;
            height -= delta.y;

            if (dragListener != null)
                dragListener.onDrag(new Vector2(0, delta.y / 2));
            recalcWidth();
        } else if (resizedTop) {
            center.y += delta.y / 2;
            height += delta.y;

            if (dragListener != null)
                dragListener.onDrag(new Vector2(0, delta.y / 2));
            recalcWidth();
        } else if (resizedLeft) {
            center.x += delta.x / 2;
            width -= delta.x;

            if (dragListener != null)
                dragListener.onDrag(new Vector2(delta.x / 2, 0));
            recalcHeight();
        } else if (resizedRight) {
            center.x += delta.x / 2;
            width += delta.x;

            if (dragListener != null)
                dragListener.onDrag(new Vector2(delta.x / 2, 0));
            recalcHeight();
        }
    }

    protected void recalcHeight() {
        if (text != null && text.length() * height / 2 >= width) {
            double delta = width - 1;
//            height = delta;

//            if (dragListener != null)
//                dragListener.onDrag(new Vector2(0, delta));
        }
    }

    protected void recalcWidth() {
        if (text != null && text.length() * height / 2 >= width) {
            double delta = height / 2;
            width += delta;

            if (dragListener != null)
                dragListener.onDrag(new Vector2(delta / 2, 0));
        }
    }

    public boolean isOnBorder(Vector2 point) {
        if (isOnBottomBorder(point)) {
            resizedBottom = true;
            resizedTop = false;
            resizedLeft = false;
            resizedRight = false;

            return true;
        } else if (isOnTopBorder(point)) {
            resizedBottom = false;
            resizedTop = true;
            resizedLeft = false;
            resizedRight = false;

            return true;
        } else if (isOnLeftBorder(point)) {
            resizedBottom = false;
            resizedTop = false;
            resizedLeft = true;
            resizedRight = false;

            return true;
        } else if (isOnRightBorder(point)) {
            resizedBottom = false;
            resizedTop = false;
            resizedLeft = false;
            resizedRight = true;

            return true;
        }
        return false;
    }

    protected abstract boolean isOnBottomBorder(Vector2 point);

    protected abstract boolean isOnTopBorder(Vector2 point);

    protected abstract boolean isOnLeftBorder(Vector2 point);

    protected abstract boolean isOnRightBorder(Vector2 point);

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
        g.setFont(new Font("TimesRoman", Font.PLAIN, screenHeight));
        if (text != null)
            g.drawString(text, sc.getScreenX(center.getX() - width / 2), sc.getScreenY(center.getY() - height / 2));

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

        if (dragListener != null)
            dragListener.onDrag(v);
    }

    @Override
    public void addText(String str) {
        if (text == null)
            text = str;
        else
            text = text.concat(str);
        recalcWidth();
    }

    @Override
    public void addDragListener(ModelDragListener listener) {
        dragListener = listener;
    }

    public Vector2 getLastClickPoint() {
        return lastClickPoint;
    }

    public void setLastClickPoint(Vector2 lastClickPoint) {
        this.lastClickPoint = lastClickPoint;
    }

}
