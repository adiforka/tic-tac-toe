package tic_tac_toe;

import static java.lang.System.out;

public class TTTGameRunner {

    private static final String SERIES_SUMMARY = "--GAME SERIES SUMMARY--";

    public static void main(String[] args) {
        ScoreRecord scoreRecord = new ScoreRecord();
        for (int i = 0; i < 3; i++) {
            out.printf("GAME NUMBER %d\n", i + 1);
            GameResult result = new TicTacToe().playGame();
            scoreRecord.addGameResult(result);
        }

        out.println(SERIES_SUMMARY);
        scoreRecord.getScore().forEach((k, v) -> out.println(k + ": " + v));

    }
}
