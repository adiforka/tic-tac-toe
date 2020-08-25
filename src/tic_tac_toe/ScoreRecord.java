package tic_tac_toe;

import java.util.EnumMap;
import java.util.Map;

public class ScoreRecord {

    private final Map<GameResult, Integer> score;

    public ScoreRecord() {
        score = new EnumMap<>(GameResult.class);
    }

    public void addGameResult(GameResult gameResult) {
        score.compute(gameResult, (k, v) -> {
            var resultCount = score.get(k);
            return resultCount == null ? 1 : ++resultCount;
        });
    }

    public Map<GameResult, Integer> getScore() {
        return score;
    }
}
