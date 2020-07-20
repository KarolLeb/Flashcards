package flashcards;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Environment env = new Environment();
        Scanner scanner = new Scanner(System.in);
        env.setNumberOfCards(Integer.parseInt(scanner.nextLine()));
        for (int i = 0; i < env.getNumberOfCards(); i++) {
            env.list.add(new Card(scanner.nextLine(), scanner.nextLine()));
        }
        for (Card c : env.list) {
            System.out.println("Print the definition of \"" + c.getTerm() + "\"");
            if (c.getDefinition().equals(scanner.nextLine())) {
                System.out.println("Correct answer");
            } else {
                System.out.println("Wrong answer. The correct one is \"" + c.getDefinition() + "\".");
            }
        }
    }

    private static class Card {
        private String term;
        private String definition;

        public Card(String term, String definition) {
            this.term = term;
            this.definition = definition;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }
    }

    private static class Environment {
        ArrayList<Card> list;
        private int numberOfCards;

        public Environment() {
            this.numberOfCards = 0;
            this.list = new ArrayList<>();
        }

        public int getNumberOfCards() {
            return numberOfCards;
        }

        public void setNumberOfCards(int numberOfCards) {
            this.numberOfCards = numberOfCards;
        }
    }
}
