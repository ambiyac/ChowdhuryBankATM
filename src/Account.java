public class Account {
    private String accountType;
    private double balance;
    private Costumer owner;

    public Account(String accountType, Costumer owner) {
        this.accountType = accountType;
        this.owner = owner;
        this.balance = 0.0;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public Costumer getOwner() {
        return owner;
    }
}
