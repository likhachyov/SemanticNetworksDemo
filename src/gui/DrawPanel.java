package gui;

import models.Curve;
import models.IModel;
import models.Knot;
import utils.ScreenConverter;
import utils.ScreenPoint;
import utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private ArrayList<Knot> knots = new ArrayList<>();
    private ArrayList<Curve> curves = new ArrayList<>();

    private ScreenConverter screenConverter;
    private Vector2 lastClickPoint;
    private IModel focusedModel, inputedTextModel;
    private Knot resizedModel;
    // Для соединения кривой
    private Knot startKnot;
    private Knot endKnot;
    private boolean addCurveMode = false;

    public DrawPanel(int realWidth, int realHeight, int width, int height) {
        screenConverter = new ScreenConverter(0, 0, realWidth, realHeight, width, height);
//        models.add(new RectKnot(new Vector2(0, 0), 50, 50, Color.YELLOW));

        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D big = bi.createGraphics();

        knots.forEach(m -> m.draw(big, screenConverter));
        curves.forEach(m -> m.draw(big, screenConverter));

        g.drawImage(bi, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (focusedModel == null && !addCurveMode) {
            ScreenPoint clickPoint = new ScreenPoint(e.getX(), e.getY());
            lastClickPoint = screenConverter.s2r(clickPoint);

            knots.forEach(m -> {
                if (m.isOnModel(lastClickPoint)) {
                    m.setFocused(true);
                    focusedModel = m;
                }
            });

            repaint();
        }

        if (addCurveMode) {
            ScreenPoint clickPoint = new ScreenPoint(e.getX(), e.getY());
            lastClickPoint = screenConverter.s2r(clickPoint);

            knots.forEach(knot -> {
                if (knot.isOnModel(lastClickPoint)) {
                    if (startKnot == null) {
                        startKnot = knot;
                        knot.setFocused(true);
                        knot.setLastClickPoint(lastClickPoint);
                    } else if (endKnot == null && !startKnot.equals(knot)) {
                        endKnot = knot;
                        knot.setFocused(true);
                        knot.setLastClickPoint(lastClickPoint);
                    } else if (startKnot.equals(knot)) {
                        startKnot.setFocused(false);
                        startKnot = null;
                    } else if (endKnot.equals(knot)) {
                        endKnot.setFocused(false);
                        endKnot = null;
                    }
                }
            });

            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (focusedModel != null) {
            focusedModel.setFocused(false);
            focusedModel = null;

            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (resizedModel != null) {
            Vector2 dragPoint = screenConverter.s2r(new ScreenPoint(e.getX(), e.getY()));
            Vector2 delta = lastClickPoint.mul(-1).add(dragPoint);
            resizedModel.resizeOn(delta);
            lastClickPoint = dragPoint;

            repaint();
        } else if (focusedModel != null) {
            Vector2 dragPoint = screenConverter.s2r(new ScreenPoint(e.getX(), e.getY()));

            Vector2 delta = lastClickPoint.mul(-1).add(dragPoint);
            focusedModel.moveOn(delta);
            lastClickPoint = dragPoint;

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (focusedModel == null) {
            Vector2 mousePoint = screenConverter.s2r(new ScreenPoint(e.getX(), e.getY()));

            boolean curveFlag = false, breakFlag1 = false, breakFlag2 = false;
//       Проверяем кривые
            for (Curve curve :
                    curves) {
                if (curve.isOnModel(mousePoint)) {
                    curveFlag = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                    resizedModel = null;
                    inputedTextModel = curve;
                }
                break;
            }
//            Проверяем узлы
            for (Knot knot :
                    knots) {
                if (knot.isOnModel(mousePoint)) {
                    if (knot.isOnBorder(mousePoint)) {
                        breakFlag1 = true;
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        inputedTextModel = null;
                        resizedModel = knot;
                    } else {
                        breakFlag2 = true;
                        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                        resizedModel = null;
                        inputedTextModel = knot;
                    }
                    break;
                }
            }
            if (!breakFlag2) {
                inputedTextModel = null;
                if (!breakFlag1) {
                    resizedModel = null;
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        } else
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (inputedTextModel != null) {
            inputedTextModel.addText(String.valueOf(e.getKeyChar()));

            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setAddCurveMode(boolean addCurveMode) {
        this.addCurveMode = addCurveMode;
    }

    public ArrayList<Knot> getKnots() {
        return knots;
    }

    public ArrayList<Curve> getCurves() {
        return curves;
    }

    public Knot getStartKnot() {
        return startKnot;
    }

    public Knot getEndKnot() {
        return endKnot;
    }
}
