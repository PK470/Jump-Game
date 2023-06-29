package flappyBird;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FlappyBird.flappybird.repaint(g);

            // Custom painting code
            // Use the Graphics object (g) to draw on the component
            // ...
    }
}

