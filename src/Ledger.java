import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Keeps track of every expense in the group, works out who owes what,
 * and saves/loads expenses to a file.
 */
public class Ledger {
    private final List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Returns each person's balance in cents: positive means they are owed
     * money, negative means they owe money. All balances always add up to zero.
     */
    public Map<String, Long> calculateBalances() {
        // TreeMap keeps names in alphabetical order, so the output is easy to read.
        Map<String, Long> balances = new TreeMap<>();

        for (Expense expense : expenses) {
            String payer = expense.getPayer();
            long amount = expense.getAmountCents();
            List<String> participants = expense.getParticipants();

            balances.put(payer, balances.getOrDefault(payer, 0L) + amount);

            long share = amount / participants.size();
            // Integer division drops leftover cents (1000 / 3 = 333), so the
            // remainder is handed out one cent at a time to the first people.
            // That way the shares always add back up to the exact total.
            long leftover = amount % participants.size();

            for (int i = 0; i < participants.size(); i++) {
                String person = participants.get(i);
                long owed = share;
                if (i < leftover) {
                    owed = owed + 1;
                }
                balances.put(person, balances.getOrDefault(person, 0L) - owed);
            }
        }
        return balances;
    }

    /**
     * Writes every expense to the file, one per line, replacing what was there.
     * Format: description,payer,amountCents,name1;name2;name3
     */
    public void saveToFile(String path) throws IOException {
        File file = new File(path);
        // Create the data folder if it's missing, so saving works on a fresh clone.
        file.getParentFile().mkdirs();

        // try-with-resources closes the file automatically, even if writing fails halfway.
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Expense expense : expenses) {
                writer.println(expense.getDescription() + ","
                        + expense.getPayer() + ","
                        + expense.getAmountCents() + ","
                        + String.join(";", expense.getParticipants()));
            }
        }
    }

    /**
     * Loads expenses from the file. Returns how many lines were skipped
     * because they couldn't be read, so Main can warn the user.
     */
    public int loadFromFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }

        int skipped = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Expense expense = parseLine(line);
                if (expense == null) {
                    skipped++;
                } else {
                    expenses.add(expense);
                }
            }
        }
        return skipped;
    }

    // Returns null for a bad line instead of throwing an error, so one damaged
    // line doesn't stop every other expense in the file from loading.
    private Expense parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            return null;
        }
        try {
            long amount = Long.parseLong(parts[2].trim());
            List<String> participants = new ArrayList<>(Arrays.asList(parts[3].split(";")));
            if (amount <= 0 || participants.isEmpty()) {
                return null;
            }
            return new Expense(parts[0], parts[1], amount, participants);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}