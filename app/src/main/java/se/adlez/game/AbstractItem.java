package se.adlez.game;

public abstract class AbstractItem implements Item {

    private final String description;
    private final String graphic;

    public AbstractItem(String description, String graphic) {
        this.description = description;
        this.graphic = graphic;
    }

    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public String getGraphic() {
        return graphic;
    }
    @Override
    public String toString() {
        return description + " " + graphic;
    }

}
