import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Entry point for SplitSmart, a command-line app that tracks shared
 * expenses among friends and shows who owes whom.
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Ledger ledger = new Ledger();
        System.out.println("Welcome to SplitSmart!");
        System.out.println("Track shared expenses and see who owes whom.");

        boolean running = true;
        while (running) {
            printMenu();
            // Read the whole line as text instead of using nextInt(), so typing
            // letters by mistake shows an error message instead of crashing.
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    addExpense(input, ledger);
                    break;
                case "2":
                    viewExpenses(ledger);
                    break;
                case "3":
                    showBalances(ledger);
                    break;
                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
        input.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== SplitSmart Menu =====");
        System.out.println("1. Add expense");
        System.out.println("2. View expenses");
        System.out.println("3. Show balances");
        System.out.println("4. Quit");
        System.out.print("Choose an option: ");
    }

    private static void addExpense(Scanner input, Ledger ledger) {
        System.out.print("What was it for? ");
        String description = input.nextLine().trim();
        if (description.isEmpty()) {
            System.out.println("Description can't be empty.");
            return;
        }

        System.out.print("Who paid? ");
        String payer = normalizeName(input.nextLine());
        if (payer.isEmpty()) {
            System.out.println("Payer name can't be empty.");
            return;
        }

        System.out.print("How much? $");
        long amountCents = parseDollarsToCents(input.nextLine());
        if (amountCents <= 0) {
            System.out.println("Amount must be a positive number, like 12.50.");
            return;
        }

        System.out.print("Who was included? (names separated by commas, include the payer if they shared it): ");
        List<String> participants = parseNames(input.nextLine());
        if (participants.isEmpty()) {
            System.out.println("At least one person must be included.");
            return;
        }

        Expense expense = new Expense(description, payer, amountCents, participants);
        ledger.addExpense(expense);
        System.out.println("Added: " + expense);
    }

    private static void viewExpenses(Ledger ledger) {
        List<Expense> expenses = ledger.getExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }
        System.out.println("All expenses:");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
        }
    }

    private static void showBalances(Ledger ledger) {
        if (ledger.getExpenses().isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }
        Map<String, Long> balances = ledger.calculateBalances();
        System.out.println("Balances:");
        for (Map.Entry<String, Long> entry : balances.entrySet()) {
            String name = entry.getKey();
            long cents = entry.getValue();
            if (cents > 0) {
                System.out.println(name + " is owed " + Expense.formatCents(cents));
            } else if (cents < 0) {
                System.out.println(name + " owes " + Expense.formatCents(-cents));
            } else {
                System.out.println(name + " is settled up");
            }
        }
    }

    // Returns -1 for anything that isn't a valid number, so the caller
    // only needs one check (amount <= 0) to catch both bad and negative input.
    private static long parseDollarsToCents(String text) {
        try {
            double dollars = Double.parseDouble(text.trim().replace("$", ""));
            // Round instead of casting: 19.99 * 100 is 1998.9999... as a double,
            // and casting to long would silently drop a cent.
            return Math.round(dollars * 100);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static List<String> parseNames(String line) {
        List<String> names = new ArrayList<>();
        for (String part : line.split(",")) {
            String name = normalizeName(part);
            // Skip blanks from extra commas, and skip repeats so nobody is charged twice.
            if (!name.isEmpty() && !names.contains(name)) {
                names.add(name);
            }
        }
        return names;
    }

    // Capitalizes names the same way every time so "alex" and "ALEX" count as one person.
    private static String normalizeName(String raw) {
        String name = raw.trim();
        if (name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}