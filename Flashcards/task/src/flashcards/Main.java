package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Card card = new Card(scanner.nextLine(), scanner.nextLine());
        String answer = scanner.nextLine();
        if (answer.equals(card.getDefinition())) {
            System.out.println("right");
        } else {
            System.out.println("wrong");
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
}
