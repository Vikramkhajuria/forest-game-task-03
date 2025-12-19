package se.adlez.game.model;

import java.util.HashMap;
import java.util.Map;

public class Forest {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private final Map<Position, Item> items = new HashMap<>();

    private boolean gameOver = false;
    private StringBuilder status = new StringBuilder();

    public Forest() {
        init();
    }

    public void init() {
        items.clear();
        gameOver = false;
        status = new StringBuilder();
    }

    public String getGamePlan() {
        StringBuilder sb = new StringBuilder();

        for (int y = 1; y <= HEIGHT; y++) {
            for (int x = 1; x <= WIDTH; x++) {
                Position pos = new Position(x, y);

                if (items.containsKey(pos)) {
                    sb.append(items.get(pos).getGraphic());
                } else {
                    sb.append("🟩");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

