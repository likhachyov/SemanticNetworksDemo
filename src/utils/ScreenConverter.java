package utils;

public class ScreenConverter {

    private double xr, yr, wr, hr;
    private int ws, hs, cx, cy;

    public ScreenConverter(double xr, double yr, double wr, double hr, int ws, int hs) {
        this.xr = xr;
        this.yr = yr;
        this.wr = wr;
        this.hr = hr;
        this.ws = ws;
        this.hs = hs;
        this.cx = (int) (xr + ws / 2);
        this.cy = (int) (yr + hs / 2);
    }

    private void calculateCenter() {
        cx = (int) (xr + ws / 2);
        cy = (int) (yr + hs / 2);
    }

    public double getXr() {
        return xr;
    }

    public void setXr(double xr) {
        this.xr = xr;
        calculateCenter();
    }

    public double getYr() {
        return yr;
    }

    public void setYr(double yr) {
        this.yr = yr;
        calculateCenter();
    }

    public double getWr() {
        return wr;
    }

    public void setWr(double wr) {
        this.wr = wr;
    }

    public double getHr() {
        return hr;
    }

    public void setHr(double hr) {
        this.hr = hr;
    }

    public int getWs() {
        return ws;
    }

    public void setWs(int ws) {
        this.ws = ws;
    }

    public int getHs() {
        return hs;
    }

    public void setHs(int hs) {
        this.hs = hs;
    }

    public int getCx() {
        return cx;
    }

    public int getCy() {
        return cy;
    }

    public ScreenPoint r2s(Vector2 p) {
        int i = (int) (cx + ws * p.getX() / wr);
        int j = (int) (cy - hs * p.getY() / hr);
        return new ScreenPoint(i, j);
    }

    public Vector2 s2r(ScreenPoint p) {
        double x = (p.getI() - cx) * wr / ws;
        double y = (cy - p.getJ()) * hr / hs;
        return new Vector2(x, y);
    }

    public int getScreenX(double realX) {
        return (int) (cx + ws * realX / wr);
    }

    public int getScreenY(double realY) {
        return (int) (cy - hs * realY / hr);
    }

    public int r2sDistanceH(double d) {
        return r2s(new Vector2(d, 0)).getI() - r2s(new Vector2(0, 0)).getI();
    }

    public int r2sDistanceV(double d) {
        return r2s(new Vector2(0, 0)).getJ() - r2s(new Vector2(0, d)).getJ();
    }
}
