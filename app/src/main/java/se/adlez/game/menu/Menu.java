package se.adlez.game.menu;

import se.adlez.game.model.Forest;
import java.util.Scanner;
import se.adlez.game.model.*;
import java.util.Random;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private Forest forest;

    public void start(){
        boolean running = true;
        menu();

        while(running){
            System.out.print("-----------------\n" +
                    " Enter your choice:\n");
            String choice = scanner.nextLine();

            switch (choice){
                case "1":
                     forest = new Forest();
                     System.out.println("Forest created");

                    break;
                case "2":
                    if (forest == null) {
                        System.out.println("Create a forest first (1).");
                    } else {
                        System.out.println(forest.getGamePlan());
                    }
                    break;
                case "3":
                    if (forest == null) {
                        System.out.println("Create a forest first (1).");
                    } else {
                        addItemFlow();
                    }
                    break;
                case "4":
                    if (forest == null) {
                        System.out.println("Create a forest first (1).");
                    } else {
                        System.out.print(forest.listItems());
                    }
                    break;
                case "5":
                    addRandomItems();
                    break;
                case "6":
                    if (forest == null) {
                        System.out.println("Create a forest first (1).");
                    } else {
                        forest.addPlayerItem(new Robot(new Position(1, 1)));
                        forest.addHunterItem(new Wolf(new Position(8, 5)));
                        forest.addHomeItem(new Castle(new Position(5, 8)));
                        System.out.println("Added player, hunter and home.");
                    }
                    break;
                case "7":

                case "m":
                    menu();
                    break;

                case "q":
                case "Q":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");

            }
        }


    }
    public void menu(){
        System.out.println(" -----------------\n" +
                "| 1) Create an empty forest\n" +
                "| 2) Print the forest\n" +
                "| 3) Add items (tree, rock) to the forest\n" +
                "| 4) List all items in the forest\n" +
                "| 5) Add 5 trees and 5 stones to the forest\n" +
                "| 6) Add player, hunter and the home\n" +
                "| 7) Play game\n" +
                "| m) Print menu\n" +
                "| qQ) Quit");

    }
    private void addItemFlow(){
        System.out.print("Add FirTree 🌲 (1) or Rock 🪨 (2): ");
        String itemChoice = scanner.nextLine();

        Item item;
        if  (itemChoice.equals("1")){
            item = new FirTree();
        } else if (itemChoice.equals("2")){
            item = new Rock();
        } else {
            System.out.println("Invalid choice");
            return;
        }

        System.out.print("Set position x y (separate by space):");
        String[] parts = scanner.nextLine().trim().split("\\s+");
        if (parts.length != 2) {
            System.out.println("Invalid position format. Example: 5 5");
            return;
        }

        int x, y;
        try {
            x = Integer.parseInt(parts[0]);
            y = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Position must be numbers. Example: 5 5");
            return;
        }

        Position pos = new Position(x, y);
        forest.addItem(item, pos);

        System.out.println("Added item to the forest: " + item + " " + pos);
    }

    private void addRandomItems() {
        if (forest == null) {
            System.out.println("Create a forest first (1).");
            return;
        }

        Random rnd = new Random();

        int rocks = 0;
        while (rocks < 5) {
            int x = rnd.nextInt(10) + 1;   // 1..10
            int y = rnd.nextInt(10) + 1;   // 1..10

            Position pos = new Position(x, y);
            Item item = new Rock();

            if (forest.tryAddItem(item, pos)) {
                System.out.println(item + " " + pos);
                rocks++;
            }
        }

        int trees = 0;
        while (trees < 5) {
            int x = rnd.nextInt(10) + 1;
            int y = rnd.nextInt(10) + 1;

            Position pos = new Position(x, y);
            Item item = new FirTree();

            if (forest.tryAddItem(item, pos)) {
                System.out.println(item + " " + pos);
                trees++;
            }
        }
    }

}

