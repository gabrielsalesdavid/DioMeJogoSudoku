package br.com.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ResetButton extends JButton {

    public ResetButton(final ActionListener actionListener) {

        this.setText("Concluir");
        this.addActionListener(actionListener);
    }
}