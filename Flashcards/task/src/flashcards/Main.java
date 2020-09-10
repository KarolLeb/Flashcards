package flashcards;

import java.io.*;
import java.util.ArrayList;
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
                case "log":
                    env.log();
                    break;
                case "hardest card":
                    env.hardestCard();
                    break;
                case "reset stats":
                    env.resetStats();
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
        HashMap<String, String> revcards;
        HashMap<String, Integer> mistakes;
        private boolean exit;

        public Environment() {
            scanner = new Scanner(System.in);
            this.exit = false;
            this.cards = new HashMap<>();
            this.revcards = new HashMap<>();
            this.mistakes = new HashMap<>();
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
                    revcards.put(value, key);
                    System.out.println("The pair (\"" + key + "\":\"" + value + "\") has been added.");
                }
            }
        }

        public void removeCard() {
            System.out.println("The card:");
            String key = scanner.nextLine();
            if (cards.containsKey(key)) {
                if (revcards.remove(cards.get(key), key)) {
                    cards.remove(key, cards.get(key));
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
            if (file.exists()) {
                try {
                    Scanner scan = new Scanner(file);
                    if (scan.hasNext()) {
                        int n = Integer.parseInt(scan.nextLine());
                        String tmp0;
                        String tmp1;
                        for (int i = 0; i < 2 * n; i++) {
                            if (scan.hasNext()) {
                                tmp0 = scan.nextLine();
                                if (scan.hasNext()) {
                                    tmp1 = scan.nextLine();
                                    cards.put(tmp0, tmp1);
                                    revcards.put(tmp1, tmp0);
                                }
                            }
                        }
                        System.out.println(n + " cards have been loaded.");
                    } else {
                        System.out.println(" cards have been loaded.");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("File not found.");
            }
        }

        public void exportCards() {
            System.out.println("File name:");
            String filename = scanner.nextLine();
            try {
                File file = new File(filename);
                if (file.exists() || file.createNewFile()) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                        writer.write(String.valueOf(cards.size()));
                        for (String key : cards.keySet()) {
                            writer.newLine();
                            writer.write(key);
                            writer.newLine();
                            writer.write(cards.get(key));
                        }
                        writer.close();
                        System.out.println(cards.size() + " cards have been saved.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("0 cards have been saved.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void ask() {
            Random random = new Random();
            System.out.println("How many times to ask?");
            int num = Integer.parseInt(scanner.nextLine());
            ArrayList<String> keys = new ArrayList<>(cards.keySet());
            for (int i = 0; i < num; i++) {
                int tmp = random.nextInt(keys.size());
                System.out.println("Print the definition of \"" + keys.get(tmp) + "\":");
                String answer = scanner.nextLine();
                if (cards.get(keys.get(tmp)).equals(answer)) {
                    System.out.println("Correct!");
                } else if (cards.containsValue(answer)) {
                    System.out.println("Wrong. The right answer is \"" + cards.get(keys.get(tmp)) +
                        "\", but your definition is correct for \"" + revcards.get(answer) + "\".");
                } else {
                    System.out.println("Wrong. The right answer is \"" + cards.get(keys.get(tmp)) + "\".");
                }
            }
        }

        public void exit() {
            exit = true;
        }

        public void log() {

        }

        public void hardestCard() {
            System.out.println("There are no cards with errors.");
        }

        public void resetStats() {
            mistakes.replaceAll((s, v) -> 0);
        }
    }
}
