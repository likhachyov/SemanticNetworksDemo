package gui;

import models.IModel;
import models.Knot;
import utils.ScreenConverter;
import utils.ScreenPoint;
import utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

    private ArrayList<IModel> models = new ArrayList<>();
    private ScreenConverter screenConverter;
    Vector2 lastClickPoint;
    private IModel focusedModel;

    public DrawPanel(int realWidth, int realHeight, int width, int height) {
        screenConverter = new ScreenConverter(0, 0, realWidth, realHeight, width, height);
        models.add(new Knot(new Vector2(0, 0), 10, 10, Color.YELLOW));

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D big = bi.createGraphics();

        models.forEach(m -> m.draw(big, screenConverter));

        g.drawImage(bi, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (focusedModel != null){
            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (focusedModel == null) {
            ScreenPoint clickPoint = new ScreenPoint(e.getX(), e.getY());
            lastClickPoint = screenConverter.s2r(clickPoint);

            models.forEach(m -> {
                if (m.isOnModel(lastClickPoint)) {
                    m.setFocused(true);
                    focusedModel = m;
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
        if (focusedModel != null) {
            ScreenPoint dragPoint = new ScreenPoint(e.getX(), e.getY());
            Vector2 realDragPoint = screenConverter.s2r(dragPoint);

            Vector2 delta = lastClickPoint.mul(-1).add(realDragPoint);
            focusedModel.moveOn(delta);
            lastClickPoint = realDragPoint;

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
