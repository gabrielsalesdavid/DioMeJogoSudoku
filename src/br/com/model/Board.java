package br.com.model;

import br.com.enums.GameStatus;

import java.util.Collection;
import java.util.List;

import static br.com.enums.GameStatus.COMPLETE;
import static br.com.enums.GameStatus.INCOMPLETE;
import static br.com.enums.GameStatus.NON_STARTED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> listSpace;

    public Board(final List<List<Space>> listSpace) {
        if (listSpace == null || listSpace.isEmpty()) {
            throw new IllegalArgumentException("A lista de espaços não pode ser nula ou vazia.");
        }
        this.listSpace = listSpace;
    }

    public List<List<Space>> getSpace() {

        return listSpace;
    }

    public GameStatus getStatus() {

        if(isNonStarted()) {

            return NON_STARTED;
        }

        return hasIncompleteSpaces() ? INCOMPLETE : COMPLETE;
    }

    private boolean isNonStarted() {

        return listSpace.stream()
                .flatMap(Collection::stream)
                .noneMatch(s -> !s.isFixed() && nonNull(s.getActual()));
    }

    private boolean hasIncompleteSpaces() {

        return listSpace.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> isNull(s.getActual()));
    }

    public boolean hasErrors() {

        if(getStatus() == NON_STARTED) {

            return false;
        }

        return listSpace
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual()
                        .equals(s.getExpected()));
    }

    private boolean isValidIndex(final int col, final int row) {

        return col >= 0 && col < listSpace.size()
                && row >= 0 && row < listSpace.get(col).size();
    }

    private boolean isSpaceFixed(final int col, final int row) {

        return listSpace.get(col).get(row).isFixed();
    }

    public boolean changeValue(final int col, final int row, final int value) {

        Space space = listSpace.get(col).get(row);
        if(space.isFixed()) {

            return false;
        }

        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row) {

        Space space = listSpace.get(col).get(row);
        if(space.isFixed()) {

            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset() {

        listSpace.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {

        return !hasErrors() && getStatus().equals(COMPLETE);
    }
}