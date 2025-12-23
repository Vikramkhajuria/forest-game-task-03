package se.adlez.game.model;

public abstract class AbstractMoveableItem extends AbstractItem implements Moveable {

    private Position position;

    public AbstractMoveableItem(String description, String graphic, Position position) {
        super(description, graphic);
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return getDescription() + " " + getGraphic() + " " + position;
    }
}
