package tic_tac_toe;

import java.util.*;

public class TicTacToe {

    private static final char[][] gameBoard = {
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
    };

    private static final List<Integer> playerPositions = new ArrayList<>();
    private static final List<Integer> cpuPositions = new ArrayList<>();


    public static void main(String[] args) {

        playGame();
    }

    public static void playGame() {

        printGameBoard();
        String result;
        while (true) {
            int playerPos = playerMoves();
            placePiece(playerPos, "player");

            result = checkWinner();
            if (result.length() > 0) {
                printResult(result);
                printGameBoard();
                break;
            }

            int CPUPos = CPUmoves();
            placePiece(CPUPos, "cpu");

            //repetitive code but hard to avoid without complicating further
            result = checkWinner();
            if (result.length() > 0) {
                printResult(result);
                printGameBoard();
                break;
            }
            //awkward, but seems to be the best of all inoptimal places to print
            //a board again
            printGameBoard();
        }
    }

    private static void printResult(String result) {
        System.out.println(result);
    }

    private static void printGameBoard() {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static int playerMoves() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a your placement 1-9: ");
        int playerPos = scan.nextInt();
        while (playerPositions.contains(playerPos) || cpuPositions.contains(playerPos)) {
            System.out.println("Position taken. Pick a free spot: ");
            playerPos = scan.nextInt();
        }
        return playerPos;
    }

    private static int CPUmoves() {

        Random random = new Random();
        int cpuPos;
        do {
            //setting range between 1 and 9 (without the +1) it is 0 - 8
            cpuPos = random.nextInt(9) + 1;
        }
        while (playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos));
        return cpuPos;
    }

    private static void placePiece(int pos, String user) {

        char placement;
        if (user.equalsIgnoreCase("Player")) {
            placement = 'O';
            playerPositions.add(pos);
        } else {
            placement = 'X';
            cpuPositions.add(pos);
        }

        switch (pos) {
            case 1:
                gameBoard[0][0] = placement;
                break;
            case 2:
                gameBoard[0][2] = placement;
                break;
            case 3:
                gameBoard[0][4] = placement;
                break;
            case 4:
                gameBoard[2][0] = placement;
                break;
            case 5:
                gameBoard[2][2] = placement;
                break;
            case 6:
                gameBoard[2][4] = placement;
                break;
            case 7:
                gameBoard[4][0] = placement;
                break;
            case 8:
                gameBoard[4][2] = placement;
                break;
            case 9:
                gameBoard[4][4] = placement;
                break;
            default:
                break;
        }
    }

    private static String checkWinner() {

        //win conditions
        List<Integer> topRow = Arrays.asList(1, 2, 3);
        List<Integer> midRow = Arrays.asList(4, 5, 6);
        List<Integer> botRow = Arrays.asList(7, 8, 9);
        List<Integer> leftCol = Arrays.asList(1, 4, 7);
        List<Integer> midCol = Arrays.asList(2, 5, 8);
        List<Integer> rightCol = Arrays.asList(3, 6, 9);
        List<Integer> cross1 = Arrays.asList(1, 5, 9);
        List<Integer> cross2 = Arrays.asList(3, 5, 7);

        List<List<Integer>> winConditions = Arrays.asList(topRow, midRow, botRow, leftCol,
                midCol, rightCol, cross1, cross2);

        for (List<Integer> winCondition : winConditions) {
            if (playerPositions.containsAll(winCondition)) {
                return "You win!";
            } else if (cpuPositions.containsAll(winCondition)) {
                return "CPU wins";
            } else if (playerPositions.size() + cpuPositions.size() == 9) {
                return "It's a tie";
            }
        }
        return "";
    }
}
