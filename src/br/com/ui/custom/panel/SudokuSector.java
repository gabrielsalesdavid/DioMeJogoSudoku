package br.com.ui.custom.panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SudokuSector extends JPanel {

    public SudokuSector() {

        Dimension dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(Color.black, 2, true));
        this.setVisible(true);
    }
}