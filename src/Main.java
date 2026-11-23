import java.util.Scanner;

/**
 * Entry point for SplitSmart, a command-line app that tracks shared
 * expenses among friends and shows who owes whom.
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
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
                    System.out.println("Add expense: coming soon.");
                    break;
                case "2":
                    System.out.println("View expenses: coming soon.");
                    break;
                case "3":
                    System.out.println("Show balances: coming soon.");
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
}