package br.com.enums;

import lombok.Getter;

@Getter
public enum GameStatus {

    NON_STARTED("Não iniciado"),
    INCOMPLETE("Incompleto"),
    COMPLETE("Completo");

    private final String label;

    GameStatus(final String label) {

        this.label = label;
    }
}