package flashcards;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Environment env = new Environment();
        if (args.length > 1) {
            if (Objects.equals(args[0], "-export")) {
                if (!"".equals(args[1])) {
                    env.export = args[1];
                }
            } else if (Objects.equals(args[0], "-import")) {
                env.importCards(args[1]);
            }
            if (args.length > 3) {
                if (!args[0].equals(args[2])) {
                    if (Objects.equals(args[2], "-export")) {
                        if (!"".equals(args[3])) {
                            env.export = args[3];
                        }
                    } else if (Objects.equals(args[2], "-import")) {
                        env.importCards(args[3]);
                    }
                }
            }
        }

        while (!env.exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            env.logs.add("Input the action (add, remove, import, export, ask, exit):");
            String str = env.scanner.nextLine();
            switch (str) {
                case "add":
                    env.addNewCard();
                    break;
                case "remove":
                    env.removeCard();
                    break;
                case "import":
                    env.userImport();
                    break;
                case "export":
                    env.userSave();
                    break;
                case "ask":
                    env.ask();
                    break;
                case "exit":
                    env.exit();
                    System.out.println("Bye bye!");
                    env.logs.add("Bye bye!");
                    if (!"".equals(env.export)) {
                        env.exitExport();
                        System.out.println(env.cards.size() + " cards have been saved.");
                        env.logs.add(env.cards.size() + " cards have been saved.");
                    }
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
                    env.logs.add("Error: wrong action!");
                    break;
            }
        }
    }

    private static class Environment {
        Scanner scanner;
        ArrayList<Card> cards;
        ArrayList<String> logs;
        String export;
        private boolean exit;

        public Environment() {
            scanner = new Scanner(System.in);
            this.exit = false;
            this.cards = new ArrayList<>();
            this.logs = new ArrayList<>();
            this.export = "";
        }

        public void addNewCard() {
            System.out.println("The card:");
            logs.add("The card:");
            String key = scanner.nextLine();
            if (containsKey(key)) {
                System.out.println("The card \"" + key + "\" already exists.");
                logs.add("The card \"" + key + "\" already exists.");
            } else {
                System.out.println("The definition of the card:");
                logs.add("The definition of the card:");
                String value = scanner.nextLine();
                if (containsValue(value)) {
                    System.out.println("The definition \"" + value + "\" already exists.");
                    logs.add("The definition \"" + value + "\" already exists.");
                } else {
                    cards.add(new Card(key, value));
                    System.out.println("The pair (\"" + key + "\":\"" + value + "\") has been added.");
                    logs.add("The pair (\"" + key + "\":\"" + value + "\") has been added.");
                }
            }
        }

        public void removeCard() {
            System.out.println("The card:");
            logs.add("The card:");
            String key = scanner.nextLine();
            if (containsKey(key)) {
                cards.removeIf(o -> o.getKey().equals(key));
                System.out.println("The card has been removed.");
                logs.add("The card has been removed.");
            } else {
                System.out.println("Can't remove \"" + key + "\": there is no such card.");
                logs.add("Can't remove \"" + key + "\": there is no such card.");
            }
        }

        public void userImport() {
            System.out.println("File name:");
            logs.add("File name:");
            String filename = scanner.nextLine();
            importCards(filename);
        }

        public void importCards(String filename) {
            File file = new File(filename);
            if (file.exists()) {
                try {
                    Scanner scan = new Scanner(file);
                    if (scan.hasNext()) {
                        int n = Integer.parseInt(scan.nextLine());
                        String tmp0;
                        String tmp1;
                        String tmp2;
                        for (int i = 0; i < 2 * n; i++) {
                            if (scan.hasNext()) {
                                tmp0 = scan.nextLine();
                                if (scan.hasNext()) {
                                    tmp1 = scan.nextLine();
                                    if (scan.hasNext()) {
                                        tmp2 = scan.nextLine();
                                        cards.add(new Card(tmp0, tmp1, Integer.parseInt(tmp2)));
                                    }
                                }
                            }
                        }
                        System.out.println(n + " cards have been loaded.");
                        logs.add(n + " cards have been loaded.");
                    } else {
                        System.out.println("0 cards have been loaded.");
                        logs.add("0 cards have been loaded.");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("File not found.");
                logs.add("File not found.");
            }
        }

        public void exitExport() {
            exportCards(export);
        }

        public void userSave() {
            System.out.println("File name:");
            logs.add("File name:");
            String filename = scanner.nextLine();
            exportCards(filename);
            System.out.println(cards.size() + " cards have been saved.");
            logs.add(cards.size() + " cards have been saved.");
        }

        public void exportCards(String filename) {
            try {
                File file = new File(filename);
                if (file.exists() || file.createNewFile()) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                        writer.write(String.valueOf(cards.size()));
                        for (Card c : cards) {
                            writer.newLine();
                            writer.write(c.getKey());
                            writer.newLine();
                            writer.write(c.getDescription());
                            writer.newLine();
                            writer.write(Integer.toString(c.getMistakesCount()));
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
            logs.add("How many times to ask?");
            int num = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < num; i++) {
                int tmp = random.nextInt(cards.size());
                System.out.println("Print the definition of \"" + cards.get(tmp).getKey() + "\":");
                logs.add("Print the definition of \"" + cards.get(tmp).getKey() + "\":");
                String answer = scanner.nextLine();
                if (cards.get(tmp).description.equals(answer)) {
                    System.out.println("Correct!");
                    logs.add("Correct!");
                } else if (containsValue(answer)) {
                    cards.get(tmp).setMistakesCount(cards.get(tmp).getMistakesCount() + 1);
                    System.out.println("Wrong. The right answer is \"" + cards.get(tmp).getDescription() +
                        "\", but your definition is correct for \"" + cards.stream().filter(c -> c.getDescription().equals(answer)).findFirst().get().getKey() + "\".");
                    logs.add("Wrong. The right answer is \"" + cards.get(tmp).getDescription() +
                        "\", but your definition is correct for \"" + cards.stream().filter(c -> c.getDescription().equals(answer)).findFirst().get().getKey() + "\".");
                } else {
                    cards.get(tmp).setMistakesCount(cards.get(tmp).getMistakesCount() + 1);
                    System.out.println("Wrong. The right answer is \"" + cards.get(tmp).getDescription() + "\".");
                    logs.add("Wrong. The right answer is \"" + cards.get(tmp).getDescription() + "\".");
                }
            }
        }

        public void exit() {
            exit = true;
        }

        public void log() {
            System.out.println("File name:");
            logs.add("File name:");
            String filename = scanner.nextLine();
            try {
                File file = new File(filename);
                if (file.exists() || file.createNewFile()) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                        for (String log : logs) {
                            writer.write(log);
                            writer.newLine();
                        }
                        writer.close();
                        System.out.println("The log has been saved.");
                        logs.add("The log has been saved.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Error");
                    logs.add("Error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void hardestCard() {
            cards.sort(Collections.reverseOrder());
            int max = 0;
            if (cards.size() > 0) {
                max = Collections.max(cards).getMistakesCount();
            }
            if (max > 0) {
                ArrayList<Card> maxMistakesList = new ArrayList<>();
                for (Card c : cards) {
                    if (c.getMistakesCount() == max) {
                        maxMistakesList.add(c);
                    } else {
                        break;
                    }
                }
                if (maxMistakesList.size() > 1) {
                    System.out.print("The hardest card are ");
                    logs.add("The hardest card are ");
                    for (int i = 0; i < maxMistakesList.size() - 1; i++) {
                        System.out.print("\"" + maxMistakesList.get(0).getKey() + "\",");
                        logs.add("\"" + maxMistakesList.get(0).getKey() + "\",");
                    }
                    System.out.println("\"" + maxMistakesList.get(maxMistakesList.size() - 1).getKey() + "\". You have " + max + " errors answering them.");
                    logs.add("\"" + maxMistakesList.get(maxMistakesList.size() - 1).getKey() + "\". You have " + max + " errors answering them.");
                } else {
                    System.out.println("The hardest card is \"" + maxMistakesList.get(0).getKey() + "\". You have " + max + " errors answering it.");
                    logs.add("The hardest card is \"" + maxMistakesList.get(0).getKey() + "\". You have " + max + " errors answering it.");
                }
            } else {
                System.out.println("There are no cards with errors.");
                logs.add("There are no cards with errors.");
            }
        }

        public void resetStats() {
            for (Card c : cards) {
                c.setMistakesCount(0);
            }
            System.out.println("Card statistics has been reset.");
            logs.add("Card statistics has been reset.");
        }

        public boolean containsKey(String key) {
            for (Card c : cards) {
                if (c.key.equals(key)) {
                    return true;
                }
            }
            return false;
        }

        public boolean containsValue(String description) {
            for (Card c : cards) {
                if (c.description.equals(description)) {
                    return true;
                }
            }
            return false;
        }

        static class Card implements Comparable<Card> {
            private final String key;
            private final String description;
            private int mistakesCount;

            public Card(String key, String description) {
                this.key = key;
                this.description = description;
                this.mistakesCount = 0;
            }

            public Card(String key, String description, int mistakesCount) {
                this.key = key;
                this.description = description;
                this.mistakesCount = mistakesCount;
            }

            public String getKey() {
                return key;
            }

            public String getDescription() {
                return description;
            }

            public int getMistakesCount() {
                return mistakesCount;
            }

            public void setMistakesCount(int mistakesCount) {
                this.mistakesCount = mistakesCount;
            }

            @Override
            public int compareTo(Card other) {
                if (this.getMistakesCount() > other.getMistakesCount())
                    return 1;
                else if (this.getMistakesCount() == other.getMistakesCount())
                    return 0;
                return -1;
            }
        }
    }
}
