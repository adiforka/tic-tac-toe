package tic_tac_toe;
import java.util.*;

import static java.lang.System.*;

public class TicTacToe {

    private static char[][] gameboard = {
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
    };
    private static final List<Integer> playerPositions = new ArrayList<>();
    private static final List<Integer> cpuPositions = new ArrayList<>();


    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            out.printf("GAME NUMBER %d\n", i + 1);
            playGame();
        }
    }

    public static void playGame() {
        String result;
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
            if (result.length() > 0) {
                out.println(result);
                printGameBoard();
                cleanUp();
                break;
            }

            if (playerTurn) {
                printGameBoard();
                playerMoves();
            } else {
                cpuMoves();
            }
            playerTurn = !playerTurn;

            result = checkWinner();
            if (result.length() > 0) {
                out.println(result);
                printGameBoard();
                cleanUp();
                break;
            }
        }
    }

    private static void printGameBoard() {
        for (char[] row : gameboard) {
            for (char ch : row) {
                out.print(ch + " ");
            }
            out.println();
        }
        out.println();
    }

    private static boolean isPlayerTurn() {
        int val = new Random().nextInt(2);
        return val > 0;
    }

    private static void playerMoves() {
        Scanner scanner = new Scanner(in);
        out.println("Enter placement in a free position 1-9");
        int position = scanner.nextInt();
        while (playerPositions.contains(position) || cpuPositions.contains(position)) {
            out.println("Position taken. Enter placement in a free position 1-9");
            position = scanner.nextInt();
        }

        makePlacement(position, "Player");
    }

    private static void cpuMoves() {
        var random = new Random(1);
        int position;
        do {
            position = random.nextInt(10);
        } while (cpuPositions.contains(position) || playerPositions.contains(position));

        makePlacement(position, "CPU");
    }

    private static void makePlacement(int position, String user) {

        char placement;
        if (user.equalsIgnoreCase("Player")) {
            placement = '0';
            playerPositions.add(position);
        } else {
            placement = 'X';
            cpuPositions.add(position);
        }

        switch (position) {
            case 1 -> gameboard[0][0] = placement;
            case 2 -> gameboard[0][2] = placement;
            case 3 -> gameboard[0][4] = placement;
            case 4 -> gameboard[2][0] = placement;
            case 5 -> gameboard[2][2] = placement;
            case 6 -> gameboard[2][4] = placement;
            case 7 -> gameboard[4][0] = placement;
            case 8 -> gameboard[4][2] = placement;
            case 9 -> gameboard[4][4] = placement;
        }
    }

    private static String checkWinner() {
        List<List<Integer>> winningConditions = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9),
                Arrays.asList(1, 4, 7),
                Arrays.asList(2, 5, 8),
                Arrays.asList(3, 6, 9),
                Arrays.asList(1, 5, 9),
                Arrays.asList(3, 5, 7)
        );

        for (List<Integer> condition : winningConditions) {
            if (playerPositions.containsAll(condition)) {
                return "You win!";
            } else if (cpuPositions.containsAll(condition)) {
                return "Cpu wins!";
            } else if (cpuPositions.size() + playerPositions.size() == 9) {
                return "It's a tie!";
            }
        }
        return "";
    }

    private static void cleanUp() {
        restoreGameBoard();
        clearPositionLists();
    }

    private static void restoreGameBoard() {
        gameboard = new char[][]{
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
        };
    }

    private static void clearPositionLists() {
        playerPositions.clear();
        cpuPositions.clear();
    }
}
