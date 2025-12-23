package se.adlez.game.model;

import java.util.HashMap;
import java.util.Map;

public class Forest {
    private AbstractMoveableItem player;
    private AbstractMoveableItem hunter;
    private AbstractMoveableItem home;


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
    public void addPlayerItem(AbstractMoveableItem player) {
        this.player = player;
        items.put(player.getPosition(), player);
    }

    public void addHunterItem(AbstractMoveableItem hunter) {
        this.hunter = hunter;
        items.put(hunter.getPosition(), hunter);
    }

    public void addHomeItem(AbstractMoveableItem home) {
        this.home = home;
        items.put(home.getPosition(), home);
    }
    public boolean isGameOver() {
        return gameOver;
    }

    public String getStatus() {
        return status.toString();
    }
    public void movePlayer(Position relative) {
        status = new StringBuilder();

        if (player == null || home == null) {
            status.append("Player/home not added. Use menu choice 6.\n");
            return;
        }

        Position playerPos = player.getPosition();
        Position newPos = new Position(playerPos);   // you need copy-constructor (see below)
        newPos.move(relative);

        // bounds check (1..10)
        if (newPos.getX() < 1 || newPos.getX() > WIDTH || newPos.getY() < 1 || newPos.getY() > HEIGHT) {
            status.append("Player could not move!\n");
            return;
        }

        // reached home?
        if (home.getPosition().equals(newPos)) {
            items.remove(playerPos);
            player.setPosition(newPos);
            items.put(newPos, player);

            status.append("Player reached home!\nGame is over!\n");
            gameOver = true;
            return;
        }

        // blocked by an item?
        if (items.containsKey(newPos)) {
            status.append("Player could not move!\n");
            return;
        }

        // move (remove+re-add because Position is the key)
        items.remove(playerPos);
        player.setPosition(newPos);
        items.put(newPos, player);

        status.append("Player moved successfully!\n");
    }



}

