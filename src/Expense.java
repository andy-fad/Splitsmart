import java.util.List;

/**
 * One shared expense: what it was for, who paid, how much, and who it is split between.
 */
public class Expense {
    private final String description;
    private final String payer;
    // Money is stored as whole cents instead of a double, because doubles can't
    // represent values like 0.10 exactly and those tiny errors add up over many expenses.
    private final long amountCents;
    private final List<String> participants;

    public Expense(String description, String payer, long amountCents, List<String> participants) {
        this.description = description;
        this.payer = payer;
        this.amountCents = amountCents;
        this.participants = participants;
    }

    public String getDescription() {
        return description;
    }

    public String getPayer() {
        return payer;
    }

    public long getAmountCents() {
        return amountCents;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public static String formatCents(long cents) {
        return String.format("$%.2f", cents / 100.0);
    }

    @Override
    public String toString() {
        return description + ": " + payer + " paid " + formatCents(amountCents)
                + ", split between " + String.join(", ", participants);
    }
}