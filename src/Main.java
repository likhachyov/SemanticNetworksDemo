import gui.DrawPanel;
import gui.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("SemanticNetworksDemo");
        mainFrame.setSize(800, 700);

        DrawPanel drawPanel = new DrawPanel(650, 700, 650, 700);
        drawPanel.setPreferredSize(new Dimension(650, 700));
        mainFrame.add(drawPanel, BorderLayout.CENTER);

        MenuPanel menuPanel = new MenuPanel(drawPanel);
        menuPanel.setPreferredSize(new Dimension(150, 700));
        mainFrame.add(menuPanel, BorderLayout.WEST);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
