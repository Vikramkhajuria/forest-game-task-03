package se.adlez.game;

import se.adlez.game.Forest;
import se.adlez.game.Item;
import se.adlez.game.Position;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ForestToJson {

    public static String toJson(Forest forest) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"WIDTH\": 10,\n");
        sb.append("  \"HEIGHT\": 10,\n");
        sb.append("  \"gameOver\": ").append(forest.isGameOver()).append(",\n");
        sb.append("  \"status\": \"").append(escape(forest.getStatus())).append("\",\n");

        sb.append("  \"items\": [\n");

        Map<Position, Item> items = forest.getItemsSnapshot();
        int i = 0;
        for (Map.Entry<Position, Item> e : items.entrySet()) {
            Position p = e.getKey();
            Item it = e.getValue();

            sb.append("    {\n");
            sb.append("      \"x\": ").append(p.getX()).append(",\n");
            sb.append("      \"y\": ").append(p.getY()).append(",\n");
            sb.append("      \"type\": \"").append(escape(it.getClass().getSimpleName())).append("\",\n");
            sb.append("      \"description\": \"").append(escape(it.getDescription())).append("\",\n");
            sb.append("      \"graphic\": \"").append(escape(it.getGraphic())).append("\"\n");
            sb.append("    }");

            i++;
            sb.append(i < items.size() ? ",\n" : "\n");
        }

        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }

    public static void saveAsJson(Forest forest, String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(toJson(forest));
        } catch (IOException e) {
            throw new RuntimeException("Could not save JSON to '" + fileName + "': " + e.getMessage(), e);
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

