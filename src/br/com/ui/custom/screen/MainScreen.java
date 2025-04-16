package br.com.ui.custom.screen;

import br.com.enums.Event;
import br.com.enums.GameStatus;
import br.com.model.Space;
import br.com.service.BoardService;
import br.com.service.NotifierService;
import br.com.ui.custom.button.CheckGameStatusButton;
import br.com.ui.custom.button.FinishGameButton;
import br.com.ui.custom.button.ResetButton;
import br.com.ui.custom.frame.MainFrame;
import br.com.ui.custom.input.NumberText;
import br.com.ui.custom.panel.MainPanel;
import br.com.ui.custom.panel.SudokuSector;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.enums.Event.CLEAR_SPACE;
import static javax.swing.JOptionPane.*;

@Getter
public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);
    private final BoardService boardService;
    private final NotifierService notifierService;
    private JButton checkGameButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {

        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {

        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);

        for(int r = 0; r < 9; r+=3) {

            int endRow = r + 2;
            for(int c = 0; c < 9; c+=3) {

                int endCol = c + 2;
                List<Space> space = getSpacesFromSector(boardService.getSpace(), c, endCol, r, endRow);
                JPanel sector = generateSection(space);
                mainPanel.add(sector);
            }

        }
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces,
                                            final int initCol, final int endCol, final int initRow, final int endRow) {

        List<Space> spacesSector = new ArrayList<>();

        for(int r = initRow; r <= endRow; r++) {
            for(int c = initCol; c <= endCol; c++) {

                spacesSector.add(spaces.get(c).get(r));
            }
        }

        return spacesSector;
    }

    private JPanel generateSection(final List<Space> spaces) {

        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscriber(CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }

    private void addFinishGameButton(JPanel mainPanel) {

        finishGameButton = new FinishGameButton(e -> {

            if(boardService.gameIsFinished()) {

                showMessageDialog(null, "Parabens você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {

                String message = "Seu jogo tem algum inconsistencia, ajuste e tente novamente!";
                showMessageDialog(null, message);
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {

        checkGameButton = new CheckGameStatusButton(e -> {
            boolean hasErrors = boardService.hasErrors();
            GameStatus gameStatus = boardService.getStatus();
            String message = switch(gameStatus) {
                case NON_STARTED -> "O jogo não foi iniciado!";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };

            message += hasErrors ? " e contem erros" : " e não contem erros";
            showMessageDialog(null, message);
        });
        mainPanel.add(MainScreen.this.checkGameButton);
    }

    private void addResetButton(JPanel mainPanel) {

        resetButton = new ResetButton(e -> {
            int dialog = showConfirmDialog(null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
                    );

            if(dialog == 0) {

                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });

        mainPanel.add(resetButton);
    }
}