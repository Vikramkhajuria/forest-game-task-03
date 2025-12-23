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

    public void addItem(Item item, Position position) {
        items.put(position, item);
    }

    public boolean tryAddItem(Item item, Position position) {
        if (items.containsKey(position)) return false;
        items.put(position, item);
        return true;
    }

    public String listItems() {
        if (items.isEmpty()) return "(no items)\n";

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Position, Item> entry : items.entrySet()) {
            sb.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}

