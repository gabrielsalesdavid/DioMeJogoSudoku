import br.com.model.Board;
import br.com.model.Space;

import java.util.Scanner;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner scn = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {

        final Map<String, String> positions = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0],
                        v -> v.split(";")[1]));
        int option = -1;

        while(true) {

            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = scn.nextInt();

            switch(option) {

                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção Invalida, selecione uma das opções do menu!");
            }
        }
    }

    private static void startGame(final Map<String, String> position) {

        if(nonNull(board)) {

            System.out.println("O jogo foi iniciado");
            return;
        }

        List<List<Space>> listSpaces = new ArrayList<>();
        for(int i = 0; i < BOARD_LIMIT; i++) {

            listSpaces.add(new ArrayList<>());
            for(int j = 0; j < BOARD_LIMIT; j++) {

                String positionConfig = position.get("%s, %s".formatted(i, j));
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

        board = new Board(listSpaces);
        System.out.println("O jogo está pronto para começar");
    }

    public static void inputNumber() {

        if(isNull(board)) {

            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o numero será inserido:");
        int col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o numero será inserido:");
        int row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o numero que vai entrar na posição [%s, %s]: \n", col, row);
        int value = runUntilGetValidNumber(1, 9);

        if(!board.changeValue(col, row, value)) {

            System.out.printf("A posição [%d, %d] tem um valor fixo \n", col, row);
        }
    }

    public static void removeNumber() {

        if(isNull(board)) {

            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o numero será inserido:");
        int col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o numero será inserido:");
        int row = runUntilGetValidNumber(0, 8);

        if(!board.clearValue(col, row)) {

            System.out.printf("A posição [%d, %d] tem um valor fixo \n", col, row);
        }
    }

    private static void showCurrentGame() {

        if(isNull(board)) {

            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        Object[] args = new Object[81];
        int argPos = 0;

        for(int i = 0; i < BOARD_LIMIT; i++) {

            for(List<Space> col : board.getSpace()) {

                args[argPos++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }

        System.out.println("Seu jogo da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    public static void showGameStatus() {

        if(isNull(board)) {

            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.printf("O jogo atualmente se encontra no status %s \n", board.getStatus().getLabel());

        if(board.hasErrors()) {

            System.out.println("O jogo contem erros");
        } else {

            System.out.println("O jogo não contem erros");
        }
    }

    public static void clearGame() {

        if(isNull(board)) {

            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Teria certeza que deseja limpar seu jogo e perder todo o seu progresso?");
        String confirm = scn.next();
        while(!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")) {

            System.out.println("Informe 'sim' ou 'não'");
            confirm = scn.next();
        }

        if(confirm.equalsIgnoreCase("sim")) {

            board.reset();
        }
    }

    public static void finishGame() {

        if(isNull(board)) {

            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        if(board.gameIsFinished()) {

            System.out.println("Parabens você concluiu o jogo!");
            showCurrentGame();
            board = null;
        } else if(board.hasErrors()) {

            System.out.println("Seu jogo contem erros, certifique seu board e ajuste-o!");
        } else {

            System.out.println("Você ainda precisa preencher algum espaço");
        }
    }

    public static int runUntilGetValidNumber(final int min, final int max) {

        int current = scn.nextInt();

        while(current < min || current > max) {

            System.out.printf("Informe um numero %s e %s \n", min, max);
            current = scn.nextInt();
        }

        return current;
    }
}