package se.adlez.game;

import se.adlez.game.Forest;

import java.io.*;

public class ForestToFile {

    public static void save(Forest forest, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(forest);
        } catch (IOException e) {
            throw new RuntimeException("Could not save forest to '" + fileName + "': " + e.getMessage(), e);
        }
    }

    public static Forest load(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = in.readObject();
            return (Forest) obj;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Could not load forest from '" + fileName + "': " + e.getMessage(), e);
        }
    }
}

