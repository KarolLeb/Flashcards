package flashcards;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Environment env = new Environment();
        env.setNumberOfCards(Integer.parseInt(scanner.nextLine()));
        for (int i = 0; i < env.getNumberOfCards(); i++) {
            env.cards.put(scanner.nextLine(), scanner.nextLine());
        }
        for (HashMap.Entry<String, String> c : env.cards.entrySet()) {
            System.out.println("Print the definition of \"" + c.getKey() + "\"");
            if (c.getValue().equals(scanner.nextLine())) {
                System.out.println("Correct answer");
            } else {
                System.out.println("Wrong answer. The correct one is \"" + c.getValue() + "\".");
            }
        }
    }

    private static class Environment {
        HashMap<String, String> cards;
        private int numberOfCards;

        public Environment() {
            this.numberOfCards = 0;
            this.cards = new HashMap<>();
        }

        public int getNumberOfCards() {
            return numberOfCards;
        }

        public void setNumberOfCards(int numberOfCards) {
            this.numberOfCards = numberOfCards;
        }
    }
}
