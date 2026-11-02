# SplitTab: A Command-Line Bill Splitter

A simple Java command-line app that tracks shared expenses among friends and tells everyone exactly who owes whom.

## Project Goal

When my friends and I hang out, one person usually pays for dinner, an Uber, or groceries, and everyone promises to pay them back later. After a few outings it gets hard to remember who owes whom, especially when not everyone was at every event. SplitTab records each shared expense and calculates everyone's balance, so nobody has to do the math by hand.

## MVP Scope (Version 1.0)

The MVP includes the "Do" quadrant of my Eisenhower Matrix:

- Add friends' names to the group
- Log an expense: description, who paid, amount, and who was included
- Split each expense equally among the people included
- Show each person's running balance (owed vs. owes)
- Show settle-up payments (e.g., "Sam pays Alex $12.50")
- Save expenses to a file and load them on startup
- Simple text menu to choose actions

## Future Plans (Version 2.0+)


- Custom splits (exact amounts or percentages per person)
- Record a payback to clear a balance
- Edit or delete a logged expense
- Multiple separate groups (e.g., roommates vs. a trip)
- View expense history filtered by person or date

## Tech Stack

| Area | Choice |
|------|--------|
| Language | Java (JDK 17+) |
| Input | `java.util.Scanner` |
| Data structures | `ArrayList` (expenses), `HashMap` (balances by name) |
| File I/O | `BufferedReader`, `FileWriter` |
| Money formatting | `java.text.NumberFormat` |
| Dates | `java.time.LocalDate` |
| Testing | JUnit 5 |
| Data storage | `data/friends.txt` and `data/expenses.csv` (plain text, no database) |
| External data sources | None; all data comes from user input |


### Program structure

- `Main`: runs the text menu loop
- `Expense`: stores one expense (description, payer, amount, participants, date)
- `Ledger`: holds all expenses and calculates balances and settle-up payments
- `FileStorage`: loads and saves the data files

### Data file format

`expenses.csv` stores one expense per row (amount in cents, participants separated by `;`):

```
2026-11-01,Pizza,Alex,4500,Alex;Sam;Jordan
```

## Design Documents

- [Eisenhower Matrix](docs/eisenhower-matrix.pdf)
- [System Diagram](docs/system-diagram.jpg)

## Build Plan

Each milestone corresponds to a commit (or small set of commits):

1. Set up the repo, folder structure, and a `Main` that prints a welcome message
2. Build the menu loop with placeholder options
3. Add friends and log expenses (`Expense` class)
4. Calculate equal splits and balances (`Ledger` class)
5. Add the settle-up algorithm
6. Save and load data (`FileStorage` class)
7. Handle bad input without crashing
8. Write JUnit tests, polish output, and finalize this README

## Repository Structure

```
splittab/
├── src/        # Java source files
├── tests/      # JUnit tests
├── docs/       # Eisenhower Matrix and system diagram
├── data/       # friends.txt and expenses.csv (created at runtime)
└── README.md
```

## How to Run

*Coming soon.*

## Author

[Andi Feng] | Purdue University
