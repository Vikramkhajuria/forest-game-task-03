package se.adlez.game;
import java.io.Serializable;

public interface Item extends Serializable {
    // no public on methods needed, interface methods are public implicitly
     String getDescription();
     String getGraphic();
}
