package br.com.model;

import br.com.enums.GameStatus;

import java.util.Collection;
import java.util.List;

import static br.com.enums.GameStatus.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> listSpace;

    public Board(final List<List<Space>> listSpace) {

        this.listSpace = listSpace;
    }

    public List<List<Space>> getListSpace() {

        return listSpace;
    }

    public GameStatus getStatus() {

        if(listSpace
                .stream()
                .flatMap(Collection::stream)
                .noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {

            return NON_STARTED;
        }

        return listSpace
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
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

    public boolean gameIsFinish() {

        return !hasErrors() && getStatus().equals(COMPLETE);
    }
}