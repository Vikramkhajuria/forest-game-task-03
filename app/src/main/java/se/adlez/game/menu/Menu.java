package se.adlez.game.menu;

import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);

    public void start(){
        boolean running = true;
        menu();

        while(running){
            System.out.print("-----------------\n" +
                    " Enter your choice:\n");
            String choice = scanner.nextLine();

            switch (choice){
                case "1":
                    continue;
                case "2":
                    continue;
                case "3":
                    continue;
                case "4":
                    continue;
                case "5":
                    continue;
                case "6":
                    continue;
                case "7":
                    continue;
                case "m":
                    menu();
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
}
