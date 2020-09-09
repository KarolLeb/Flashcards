package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Environment env = new Environment();

        while (!env.exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            String str = env.scanner.nextLine();
            switch (str) {
                case "add":
                    env.addNewCard();
                    break;
                case "remove":
                    env.removeCard();
                    break;
                case "import":
                    env.importCards();
                    break;
                case "export":
                    env.exportCards();
                    break;
                case "ask":
                    env.ask();
                    break;
                case "exit":
                    env.exit();
                    System.out.println("Bye bye!");
                    break;
                default:
                    System.out.println("Error: wrong action!");
                    break;
            }
        }
    }

    private static class Environment {
        Scanner scanner;
        HashMap<String, String> cards;
        private boolean exit;

        public Environment() {
            scanner = new Scanner(System.in);
            this.exit = false;
            this.cards = new HashMap<>();
        }

        public void addNewCard() {
            System.out.println("The card:");
            String key = scanner.nextLine();
            if (cards.containsKey(key)) {
                System.out.println("The card \"" + key + "\" already exists.");
            } else {
                System.out.println("The definition of the card:");
                String value = scanner.nextLine();
                if (cards.containsValue(value)) {
                    System.out.println("The definition \"" + value + "\" already exists.");
                } else {
                    cards.put(key, value);
                    System.out.println("The pair (\"" + key + "\":\"" + value + "\") has been added.");
                }
            }
        }

        public void removeCard() {
            System.out.println("The card:");
            String key = scanner.nextLine();
            if (cards.containsKey(key)) {
                if (cards.remove(key, cards.get(key))) {
                    System.out.println("The card has been removed.");
                } else {
                    System.out.println("Error: cannot remove card!");
                }
            } else {
                System.out.println("Can't remove \"" + key + "\": there is no such card.");
            }
        }

        public void importCards() {
            System.out.println("File name:");
            String filename = scanner.nextLine();
            File file = new File(filename);
            try {
                Scanner scan = new Scanner(file);
                int n = Integer.parseInt(scan.nextLine());
                for (int i = 0; i < n; i++) {
                    String[] tmp = scan.nextLine().split(" ");
                    cards.put(tmp[0], tmp[1]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        public void exportCards() {
            System.out.println("File name:");
            String filename = scanner.nextLine();
            try {
                File file = new File(filename);
                if (file.createNewFile()) {
                    try {
                        FileWriter writer = new FileWriter(filename);
                        System.out.println(cards.size());
                        for (String key : cards.keySet()) {
                            System.out.println(key + " " + cards.get(key));
                        }
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void ask() {
            Random random = new Random();
            System.out.println("How many times to ask?");
            int num = Integer.parseInt(scanner.nextLine());
            String[] keys = (String[]) cards.keySet().toArray();
            for (int i = 0; i < num; i++) {
                int tmp = random.nextInt(cards.size());
                System.out.println("Print the definition of \"" + keys[tmp] + "\":");

            }
        }

        public void exit() {
            exit = true;
        }
    }
}
