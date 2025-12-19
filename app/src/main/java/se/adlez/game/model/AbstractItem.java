package se.adlez.game.model;

public class AbstractItem {
    private String description;
    private String graphic;
    public AbstractItem(String description, String graphic) {
        this.description = description;
        this.graphic = graphic;
    }
    public String getDescription() {
        return description;
    }
    public String getGraphic() {
        return graphic;
    }

    @Override
    public String toString() {
        return description;
        // incomplete

    }
}
