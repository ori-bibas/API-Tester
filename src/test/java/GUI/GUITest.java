package GUI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GUITest {

    GUI gui = new GUI();

    @Test
    public void testFrameProperties() {
        Assertions.assertNotNull(gui.frame);
        Assertions.assertTrue(gui.frame.getHeight() == 800);
        Assertions.assertTrue(gui.frame.getWidth() == 780);
    }

}