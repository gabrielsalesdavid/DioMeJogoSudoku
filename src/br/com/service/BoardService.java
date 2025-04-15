package br.com.service;

import br.com.enums.GameStatus;
import br.com.model.Board;
import br.com.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService {

    private final static int BOARD_LIMIT = 0;

    private final Board board;

    public BoardService(final Map<String, String> gameConfig) {

        this.board = new Board(initBoard(gameConfig));
    }

    public boolean hasErrors() {

        return board.hasErrors();
    }

    public GameStatus getStatus() {

        return board.getStatus();
    }

    public boolean gameIsFinished() {

        return board.gameIsFinished();
    }

    public List<List<Space>> getSpace() {

        return this.board.getSpace();
    }

    public void reset() {

        board.reset();
    }

    private List<List<Space>> initBoard(Map<String, String> gameConfig) {

        List<List<Space>> listSpaces = new ArrayList<>();
        for(int i = 0; i < BOARD_LIMIT; i++) {

            listSpaces.add(new ArrayList<>());
            for(int j = 0; j < BOARD_LIMIT; j++) {

                String positionConfig = gameConfig.get("%s, %s".formatted(i, j));
                if (positionConfig != null) {

                    int expected = Integer.parseInt(positionConfig.split(",")[0]);
                    boolean fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                    Space currentSpace =  new Space(expected, fixed);
                    listSpaces.get(i).add(currentSpace);
                } else {

                    listSpaces.get(i).add(new Space(0, false));
                }
            }
        }

        return listSpaces;
    }
}