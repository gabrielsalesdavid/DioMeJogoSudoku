package br.com.ui.custom.input;

import br.com.enums.Event;
import br.com.model.Space;
import br.com.service.EventListener;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import static br.com.enums.Event.CLEAR_SPACE;
import static java.awt.Font.PLAIN;

@Getter
public class NumberText extends JTextField implements EventListener {

    private final Space space;

    public NumberText(final Space space) {

        this.space = space;
        Dimension dimension = new Dimension(50,50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());

        if(space.isFixed()) {

            this.setText(space.getActual().toString());
        }

        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                changeSpace();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                changeSpace();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                changeSpace();
            }

            private void changeSpace() {

                if(getText().isEmpty()) {

                    space.clearSpace();
                    return;
                }

                space.setActual(Integer.parseInt(getText()));
            }
        });
    }

    @Override
    public void update(Event eventType) {

        if(eventType.equals(CLEAR_SPACE) && (this.isEnabled())) {

            this.setText("");
        }
    }
}