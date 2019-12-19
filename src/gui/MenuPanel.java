package gui;

import models.Curve;
import models.Knot;
import models.RectKnot;
import models.StraightLine;
import utils.Vector2;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private DrawPanel drawPanel;

    public MenuPanel(DrawPanel dp) {
        drawPanel = dp;
        JLabel knotLabel = new JLabel("Knot");
        JLabel colorLabel = new JLabel("Color");
        JButton addKnotButton = new JButton("Add");
        addKnotButton.setFocusable(false);
        JLabel widthLabel = new JLabel("Width");
        JLabel heightLabel = new JLabel("Height");
        JTextField knotWidthField = new JTextField(4);
        JTextField knotHeightField = new JTextField(4);
        JCheckBox checkBox = new JCheckBox("Add curve mode");
        JButton addCurveButton = new JButton("Add curve");
        add(knotLabel);
        add(colorLabel);
        add(widthLabel);
        add(knotWidthField);
        add(heightLabel);
        add(knotHeightField);
        add(addKnotButton);
        add(checkBox);
        add(addCurveButton);

        addKnotButton.addActionListener(e -> {
            Knot knot = new RectKnot(new Vector2(0,0), Double.parseDouble(knotWidthField.getText()),
                    Double.parseDouble(knotHeightField.getText()), Color.YELLOW);

            drawPanel.getKnots().add(knot);
            drawPanel.repaint();
            drawPanel.grabFocus();
        });

        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()){
                drawPanel.setAddCurveMode(true);
            }
            else {
                drawPanel.setAddCurveMode(false);
            }
        });

        addCurveButton.addActionListener(e -> {
            Knot start = drawPanel.getStartKnot();
            Knot end = drawPanel.getEndKnot();
            start.setFocused(false);
            end.setFocused(false);
            Curve line = new StraightLine(start, end, Color.RED);

            drawPanel.getCurves().add(line);
            drawPanel.repaint();
            drawPanel.grabFocus();
        });
    }
}
