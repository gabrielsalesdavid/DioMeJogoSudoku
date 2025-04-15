package br.com.ui.custom.screen;

import br.com.enums.GameStatus;
import br.com.service.BoardService;
import br.com.ui.custom.button.FinishGameButton;
import br.com.ui.custom.button.ResetButton;
import br.com.ui.custom.frame.MainFrame;
import br.com.ui.custom.panel.MainPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static javax.swing.JOptionPane.*;

@Getter
public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);
    private final BoardService boardService;
    private JButton checkGameButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {

        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen() {

        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addFinishGameButton(JPanel mainPanel) {

        finishGameButton = new FinishGameButton(e -> {

            if(boardService.gameIsFinished()) {

                JOptionPane.showMessageDialog(null, "Parabens você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {

                String message = "Seu jogo tem algum inconsistencia, ajuste e tente novamente!";
                JOptionPane.showMessageDialog(null, message);
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {

        checkGameButton = new FinishGameButton(e -> {
            boolean hasErrors = boardService.hasErrors();
            GameStatus gameStatus = boardService.getStatus();
            String message = switch(gameStatus) {
                case NON_STARTED -> "O jogo não foi iniciado!";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };

            message += hasErrors ? " e contem erros" : " e não contem erros";
            JOptionPane.showMessageDialog(null, message);
        });
        mainPanel.add(MainScreen.this.checkGameButton);
    }

    private void addResetButton(JPanel mainPanel) {

        resetButton = new ResetButton(e -> {
            int dialog = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
                    );

            if(dialog == 0) {

                boardService.reset();
            }
        });

        mainPanel.add(resetButton);
    }
}