package GUI;

import javax.swing.*;

public class GUI {

    private JFrame frame;
    private int width = 600;
    private int height = 600;

    public GUI() {
        frame = new JFrame();
    }

    public void setupGUI() {

        // Setup basic frame attributes
        frame.setSize(width, height);
        frame.setTitle("API Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



















    }
}
