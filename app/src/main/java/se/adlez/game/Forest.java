package se.adlez.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.Serializable;

public class Forest implements Serializable {
    private static final long serialVersionUID = 1L;
    private AbstractMoveableItem player;
    private AbstractMoveableItem hunter;
    private AbstractMoveableItem home;

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private final Map<Position, Item> items = new HashMap<>();
    private transient Random rnd = new Random();

    private boolean gameOver = false;
    private StringBuilder status = new StringBuilder();

    private void ensureRnd() {
        if (rnd == null) rnd = new Random();
    }


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
    public Map<Position, Item> getItemsSnapshot() {
        return new HashMap<>(items);
    }
    public void movePlayer(Position relative) {
        ensureRnd();
        if (gameOver) return;
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


        if (!isInside(newPos)) {
            status.append("Player could not move!\n");
            return;
        }


        if (hunter.getPosition().equals(newPos)) {
            items.remove(playerPos);
            player.setPosition(newPos);
            items.put(newPos, player);

            status.append("Player walked onto the Wolf and disappeared, argh!\n");
            status.append("Game is over!\n");
            gameOver = true;
            return;
        }


        if (home.getPosition().equals(newPos)) {
            items.remove(playerPos);
            player.setPosition(newPos);
            items.put(newPos, player);

            status.append("Player reached home!\n");
            status.append("Game is over!\n");
            gameOver = true;
            return;
        }


        if (items.containsKey(newPos)) {
            status.append("Player could not move!\n");
            return;
        }


        items.remove(playerPos);
        player.setPosition(newPos);
        items.put(newPos, player);

        status.append("Player moved successfully!\n");


        moveHunter();
    }


    // Hunter logic (Requirement 7)


    private void moveHunter() {
        if (gameOver) return;


        if (rnd.nextInt(100) < 30) {
            status.append("Hunter is lurking and not moving...\n");
            return;
        }

        Position hunterPos = hunter.getPosition();
        Position playerPos = player.getPosition();


        Position step = chooseStepTowards(hunterPos, playerPos);

        Position newHunterPos = new Position(hunterPos);
        newHunterPos.move(step);

        if (!isInside(newHunterPos)) {
            status.append("Hunter is confused and hits the border...\n");
            return;
        }


        if (newHunterPos.equals(playerPos)) {
            items.remove(hunterPos);
            hunter.setPosition(newHunterPos);
            items.put(newHunterPos, hunter);

            status.append("Hunter caught the player, argh!\n");
            status.append("Game is over!\n");
            gameOver = true;
            return;
        }


        if (items.containsKey(newHunterPos)) {
            status.append("Hunter is coming closer...\n");
            status.append("...but something blocks the way.\n");
            return;
        }


        items.remove(hunterPos);
        hunter.setPosition(newHunterPos);
        items.put(newHunterPos, hunter);

        status.append("Hunter is coming closer...\n");
    }

    private boolean isInside(Position p) {
        return p.getX() >= 1 && p.getX() <= WIDTH && p.getY() >= 1 && p.getY() <= HEIGHT;
    }

    private Position chooseStepTowards(Position from, Position to) {
        int dx = Integer.compare(to.getX(), from.getX());
        int dy = Integer.compare(to.getY(), from.getY());


        int distX = Math.abs(to.getX() - from.getX());
        int distY = Math.abs(to.getY() - from.getY());

        if (distX > distY) {
            return new Position(dx, 0);
        } else if (distY > distX) {
            return new Position(0, dy);
        } else {

            return rnd.nextBoolean() ? new Position(dx, 0) : new Position(0, dy);
        }
    }
}


