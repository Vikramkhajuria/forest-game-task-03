package se.adlez.game.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Forest {
    private AbstractMoveableItem player;
    private AbstractMoveableItem hunter;
    private AbstractMoveableItem home;

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private final Map<Position, Item> items = new HashMap<>();
    private final Random rnd = new Random();

    private boolean gameOver = false;
    private StringBuilder status = new StringBuilder();

    public Forest() {
        init();
    }

    public void init() {
        items.clear();
        player = null;
        hunter = null;
        home = null;
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

        if (gameOver) {
            status.append("Game is over! Create a new forest (1).\n");
            return;
        }

        if (player == null || home == null || hunter == null) {
            status.append("Player/hunter/home not added. Use menu choice 6.\n");
            return;
        }

        Position playerPos = player.getPosition();
        Position newPos = new Position(playerPos);
        newPos.move(relative);

        // bounds check (1..10)
        if (!isInside(newPos)) {
            status.append("Player could not move!\n");
            return;
        }

        // player walks onto wolf => lose
        if (hunter.getPosition().equals(newPos)) {
            items.remove(playerPos);
            player.setPosition(newPos);
            items.put(newPos, player);

            status.append("Player walked onto the Wolf and disappeared, argh!\n");
            status.append("Game is over!\n");
            gameOver = true;
            return;
        }

        // reached home => win
        if (home.getPosition().equals(newPos)) {
            items.remove(playerPos);
            player.setPosition(newPos);
            items.put(newPos, player);

            status.append("Player reached home!\n");
            status.append("Game is over!\n");
            gameOver = true;
            return;
        }

        // blocked by other item (tree/rock/etc)
        if (items.containsKey(newPos)) {
            status.append("Player could not move!\n");
            return;
        }

        // move player (remove+re-add because Position is a key in the map)
        items.remove(playerPos);
        player.setPosition(newPos);
        items.put(newPos, player);

        status.append("Player moved successfully!\n");

        // hunter moves after player (only if game not ended)
        moveHunter();
    }

    // -------------------------
    // Hunter logic (Requirement 7)
    // -------------------------

    private void moveHunter() {
        if (gameOver) return;

        // 30% chance: hunter does not move (adds variety)
        if (rnd.nextInt(100) < 30) {
            status.append("Hunter is lurking and not moving...\n");
            return;
        }

        Position hunterPos = hunter.getPosition();
        Position playerPos = player.getPosition();

        // Simple "semi-smart": step closer in x or y
        Position step = chooseStepTowards(hunterPos, playerPos);

        Position newHunterPos = new Position(hunterPos);
        newHunterPos.move(step);

        if (!isInside(newHunterPos)) {
            status.append("Hunter is confused and hits the border...\n");
            return;
        }

        // If hunter moves onto player => lose
        if (newHunterPos.equals(playerPos)) {
            items.remove(hunterPos);
            hunter.setPosition(newHunterPos);
            items.put(newHunterPos, hunter);

            status.append("Hunter caught the player, argh!\n");
            status.append("Game is over!\n");
            gameOver = true;
            return;
        }

        // Hunter cannot walk into trees/rocks/home (you can change this rule if you want)
        if (items.containsKey(newHunterPos)) {
            status.append("Hunter is coming closer...\n");
            status.append("...but something blocks the way.\n");
            return;
        }

        // Move hunter
        items.remove(hunterPos);
        hunter.setPosition(newHunterPos);
        items.put(newHunterPos, hunter);

        status.append("Hunter is coming closer...\n");
    }

    private boolean isInside(Position p) {
        return p.getX() >= 1 && p.getX() <= WIDTH && p.getY() >= 1 && p.getY() <= HEIGHT;
    }

    private Position chooseStepTowards(Position from, Position to) {
        int dx = Integer.compare(to.getX(), from.getX()); // -1,0,1
        int dy = Integer.compare(to.getY(), from.getY()); // -1,0,1

        // Prefer moving in the axis with bigger distance
        int distX = Math.abs(to.getX() - from.getX());
        int distY = Math.abs(to.getY() - from.getY());

        if (distX > distY) {
            return new Position(dx, 0);
        } else if (distY > distX) {
            return new Position(0, dy);
        } else {
            // equal: randomly pick x or y
            return rnd.nextBoolean() ? new Position(dx, 0) : new Position(0, dy);
        }
    }
}


