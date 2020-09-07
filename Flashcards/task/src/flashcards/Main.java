package flashcards;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment env = new Environment();

        while (!env.exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            String str = scanner.nextLine();
            switch (str) {
                case "add":
                    break;
                case "remove":
                    break;
                case "import":
                    break;
                case "export":
                    break;
                case "ask":
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    break;
                default:
                    System.out.println("Error: wrong action!");
                    break;
            }

        }

//        for (int i = 1; i <= env.getNumberOfCards(); i++) {
//            System.out.println("The card #" + i + ":");
//            env.addNewCard(i);
//        }

//        for (Map.Entry<String, String> c : env.cards.entrySet()) {
//            System.out.println("Print the definition of \"" + c.getKey() + "\"");
//            String answer = scanner.nextLine();
//            if (c.getValue().equals(answer)) {
//                System.out.println("Correct answer.");
//            } else if (env.cards.containsValue(answer)) {
//                System.out.println("Wrong answer. The correct one is \"" + c.getValue() + "\", you've just written the definition of \"" + env.revCards.get(answer) + "\".");
//            } else {
//                System.out.println("Wrong answer. The correct one is \"" + c.getValue() + "\".");
//            }
//        }

    }

    private static class Environment {
        LinkedHashMap<String, String> cards;
        LinkedHashMap<String, String> revCards;
        private int numberOfCards;
        private boolean exit;

        public Environment() {
            this.numberOfCards = 0;
            this.exit = false;
            this.cards = new LinkedHashMap<>();
            this.revCards = new LinkedHashMap<>();
        }

        public void addNewCard(int i) {
            Scanner scanner = new Scanner(System.in);
            String key = scanner.nextLine();
            if (cards.containsKey(key)) {
                System.out.println("The card \"" + key + "\" already exists. Try again:");
                addNewCard(i);
            } else {
                System.out.println("The definition of the card #" + i + ":");
                addDefinition(key);
            }
        }

        public void addDefinition(String key) {
            Scanner scanner = new Scanner(System.in);
            String value = scanner.nextLine();
            if (cards.containsValue(value)) {
                System.out.println("The definition \"" + value + "\" already exists. Try again:");
                addDefinition(key);
            } else {
                cards.put(key, value);
                revCards.put(value, key);
            }
        }

        public int getNumberOfCards() {
            return numberOfCards;
        }

        public void setNumberOfCards(int numberOfCards) {
            this.numberOfCards = numberOfCards;
        }

        public void exit() {
            exit = true;
        }
    }
}
