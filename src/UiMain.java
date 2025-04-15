import br.com.ui.custom.MainPanel;
import br.com.ui.custom.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

public class UiMain {

    public static void main(String[] args) {

        Dimension dimension = new Dimension(600, 600);
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();

    }
}