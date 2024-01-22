public class TransactionHistory {
    private StringBuilder history;
    private static int nextAccountId = 1;

    public TransactionHistory() {
        this.history = new StringBuilder();
    }

    public void addTransaction(String transaction) {
        history.append(transaction).append("\n");
    }

    public String getHistory() {
        return history.toString();
    }

    public static int getNextAccountId() {
        return nextAccountId++;
    }
}
