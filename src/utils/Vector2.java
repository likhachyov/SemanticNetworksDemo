package utils;

public class Vector2 {

    public double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.getX(), y + other.getY());
    }

    public Vector2 mul(double n) {
        return new Vector2(x * n, y * n);
    }

    public Vector2 normalized() {
        double d = Math.sqrt(x * x + y * y);
        if (Math.abs(d) < 1e-15) {
            return new Vector2(0, 0);
        }
        return new Vector2(x / d, y / d);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}
