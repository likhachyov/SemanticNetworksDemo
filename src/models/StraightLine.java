package models;

import utils.Vector2;

import java.awt.*;

public class StraightLine extends Curve {

    private double k, b;
    protected int stepTo = 3;

    public StraightLine(Knot start, Knot end, Color color) {
        super(start, end, color);

    }

    @Override
    public boolean isOnModel(Vector2 v) {
        //??
        // плюс ко всему, изменить для отрезка
        k = Math.abs(endPoint.y - startPoint.y) / (Math.abs(endPoint.x - startPoint.x));
        System.out.println("K " + k);
        b = startPoint.y - startPoint.x * k;
        System.out.println("B " + b);
        double newB = b;

        for (int i = -stepTo; i < stepTo; i++) {
            newB += i;
            if (v.y == v.x * k + newB)
                return true;
        }

        return false;
    }
}
