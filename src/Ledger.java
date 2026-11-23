import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of every expense in the group.
 */
public class Ledger {
    private final List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}