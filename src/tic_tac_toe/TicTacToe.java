package tic_tac_toe;

import java.util.*;

import static java.lang.System.*;
import static tic_tac_toe.GameResult.*;

public class TicTacToe {

    private static final Set<Set<Integer>> WINNING_SEQUENCES = Set.of(
            Set.of(1, 2, 3), Set.of(4, 5, 6), Set.of(7, 8, 9),
            Set.of(1, 4, 7), Set.of(2, 5, 8), Set.of(3, 6, 9),
            Set.of(1, 5, 9), Set.of(3, 5, 7)
    );

    private final Set<Integer> PLAYER_POSITIONS;
    private final Set<Integer> CPU_POSITIONS;
    private final char[][] GAMEBOARD;


    public TicTacToe() {
        GAMEBOARD = new char[][]{
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
        };
        PLAYER_POSITIONS = new HashSet<>();
        CPU_POSITIONS = new HashSet<>();
    }

    public GameResult playGame() {
        GameResult result;
        boolean playerTurn = isPlayerTurn();
        while (true) {
            if (playerTurn) {
                printGameBoard();
                playerMoves();
            } else {
                cpuMoves();
            }
            playerTurn = !playerTurn;

            result = checkWinner();
            if (!result.equals(NO_RESULT)) {
                out.println(result.toString());

                printGameBoard();
                clearPositionLists();
                return result;
            }

            if (playerTurn) {
                printGameBoard();
                playerMoves();
            } else {
                cpuMoves();
            }
            playerTurn = !playerTurn;

            result = checkWinner();
            if (!result.equals(NO_RESULT)) {
                out.println(result.toString());
                printGameBoard();
                clearPositionLists();
                return result;
            }
        }
    }

    private void printGameBoard() {
        for (char[] row : GAMEBOARD) {
            for (char ch : row) {
                out.print(ch + " ");
            }
            out.println();
        }
        out.println();
    }

    private boolean isPlayerTurn() {
        int val = new Random().nextInt(2);
        return val > 0;
    }

    private void playerMoves() {
        Scanner scanner = new Scanner(in);
        out.println("Enter placement in a free position 1-9");
        int position = scanner.nextInt();
        while (PLAYER_POSITIONS.contains(position) || CPU_POSITIONS.contains(position)) {
            out.println("Position taken. Enter placement in a free position 1-9");
            position = scanner.nextInt();
        }

        makePlacement(position, "Player");
    }

    private void cpuMoves() {
        var random = new Random(1);
        int position;
        do {
            position = random.nextInt(10);
        } while (CPU_POSITIONS.contains(position) || PLAYER_POSITIONS.contains(position));

        makePlacement(position, "CPU");
    }

    private void makePlacement(int position, String user) {
        char placement;
        if (user.equalsIgnoreCase("Player")) {
            placement = '0';
            PLAYER_POSITIONS.add(position);
        } else {
            placement = 'X';
            CPU_POSITIONS.add(position);
        }

        switch (position) {
            case 1 -> GAMEBOARD[0][0] = placement;
            case 2 -> GAMEBOARD[0][2] = placement;
            case 3 -> GAMEBOARD[0][4] = placement;
            case 4 -> GAMEBOARD[2][0] = placement;
            case 5 -> GAMEBOARD[2][2] = placement;
            case 6 -> GAMEBOARD[2][4] = placement;
            case 7 -> GAMEBOARD[4][0] = placement;
            case 8 -> GAMEBOARD[4][2] = placement;
            case 9 -> GAMEBOARD[4][4] = placement;
        }
    }

    private GameResult checkWinner() {
        return WINNING_SEQUENCES.stream().anyMatch(PLAYER_POSITIONS::containsAll) ?
                PLAYER_WINS : WINNING_SEQUENCES.stream().anyMatch(CPU_POSITIONS::containsAll) ?
                CPU_WINS : fieldsTaken() == 9 ?
                ITS_A_TIE : NO_RESULT;
    }

    private int fieldsTaken() {
        return CPU_POSITIONS.size() + PLAYER_POSITIONS.size();
    }

    private void clearPositionLists() {
        PLAYER_POSITIONS.clear();
        CPU_POSITIONS.clear();
    }
}
