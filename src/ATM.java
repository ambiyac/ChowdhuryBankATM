import java.util.Scanner;
public class ATM {
    private Costumer customer;
    private Account savingsAccount;
    private Account checkingAccount;
    private TransactionHistory transactionHistory;
    private Scanner scanner;

    public ATM() {
        this.customer = null;
        this.savingsAccount = null;
        this.checkingAccount = null;
        this.transactionHistory = new TransactionHistory();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        welcomeUser();
        createCostumer();
        createAccounts();
        promptForPin();
        mainMenu();
        scanner.close();
    }

    private void welcomeUser() {
        System.out.println("Welcome to the ATM!");
    }

    private void createCostumer() {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Choose a PIN: ");
        int pin = scanner.nextInt();
        scanner.nextLine();
        customer = new Costumer(name, pin);
    }

    private void createAccounts() {
        savingsAccount = new Account("Savings", customer);
        checkingAccount = new Account("Checking", customer);
    }

    private void promptForPin() {
        System.out.println("Enter your PIN: ");
        int enteredPin = scanner.nextInt();
        scanner.nextLine();
        if (enteredPin == customer.getPin()) {
            System.out.println("PIN accepted. Welcome, " + customer.getName() + "!");
        } else if (enteredPin != customer.getPin()) {
            System.out.println("Incorrect PIN. Exiting...");
            System.exit(0);
        }
    }

    private void mainMenu() {
        boolean continueTransaction = true;
        while (continueTransaction) {
            displayMenuOptions();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    withdrawMoney();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    transferMoney();
                    break;
                case 4:
                    getAccountBalances();
                    break;
                case 5:
                    getTransactionHistory();
                    break;
                case 6:
                    changePin();
                    break;
                case 7:
                    exitATM();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again");
            }

            System.out.println("Do you want anything else (yes/no)");
            String continueChoice = scanner.nextLine().toLowerCase();
            if (!continueChoice.equals("yes")) {
                continueTransaction = false;
                exitATM();
            }
        }
    }

    private void displayMenuOptions() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Withdraw Money");
        System.out.println("2. Deposit Money");
        System.out.println("3. Transfer money between accounts");
        System.out.println("4. Get account balances");
        System.out.println("5 Get transaction history");
        System.out.println("6 Change PIN:");
        System.out.println("7. Exit");
        System.out.println("Enter your choice: ");
    }

    private void withdrawMoney() {
        System.out.print("Enter the amount to withdraw (must be a multiple of 5): $");
        double amountToWithdraw = scanner.nextDouble();
        scanner.nextLine();

        if (amountToWithdraw % 5 != 0) {
            System.out.println("Invalid amount. Amount must be a multiple of 5.");
            return;
        }
        if (amountToWithdraw > checkingAccount.getBalance()) {
            System.out.println("Insufficient funds!");
            return;
        }

        int numTwenties = 0;
        int numFives = 0;

        while (amountToWithdraw >= 20) {
            numTwenties++;
            amountToWithdraw -= 20;
        }
        while (amountToWithdraw >= 5) {
            numFives++;
            amountToWithdraw -= 5;
        }

        checkingAccount.updateBalance(-numTwenties * 20 - numFives * 5);
        transactionHistory.addTransaction("A" + String.format("%04d", TransactionHistory.getNextAccountId()) + ": Withdrawl of $" + (numTwenties * 20 + numFives * 5) + " from Checking");

        System.out.println("Withdrawl successful!");

    }


    private void depositMoney() {
        System.out.print("Enter the amount to deposit: $");
        double amountToDeposit = scanner.nextDouble();
        scanner.nextLine();

        checkingAccount.updateBalance(amountToDeposit);
        transactionHistory.addTransaction("A" + String.format("%04d", TransactionHistory.getNextAccountId()) + ": Deposit of $" + amountToDeposit + " to Checking");

        System.out.println("Deposit successful!");

    }

    private void transferMoney() {
        System.out.print("Enter the amount to transfer: $");
        double amountToTransfer = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Choose the FROM account (savings or checking): ");
        String fromAccount = scanner.nextLine().toLowerCase();

        if (fromAccount.equals("savings")) {
            if (amountToTransfer <= savingsAccount.getBalance()) {
                savingsAccount.updateBalance(-amountToTransfer);
                checkingAccount.updateBalance(amountToTransfer);
                transactionHistory.addTransaction("A" + String.format("%04d", TransactionHistory.getNextAccountId()) + ": Transfer of $" + amountToTransfer + " from Savings to Checking");

                System.out.println("Transfer successful!");
            } else {
                System.out.println("Insufficient funds in the Savings account!");
            }
        } else if (fromAccount.equals("checking")) {
            if (amountToTransfer <= checkingAccount.getBalance()) {
                checkingAccount.updateBalance(-amountToTransfer);
                savingsAccount.updateBalance(amountToTransfer);
                transactionHistory.addTransaction("A" + String.format("%04d", TransactionHistory.getNextAccountId()) + ": Transfer of $" + amountToTransfer + " from Checking to Savings");

                System.out.println("Transfer successful!");
            } else {
                System.out.println("Insufficient funds in the Checking account!");
            }
        } else {
            System.out.println("Invalid account. Please choose either savings or checking.");
        }
    }



    private void getAccountBalances() {
        System.out.println("Savings Acount: $" + savingsAccount.getBalance());
        System.out.println("Checking account: $" + checkingAccount.getBalance());
    }

    private void getTransactionHistory() {
        System.out.println("Transaction History: ");
        System.out.println(transactionHistory.getHistory());
    }

    private void changePin() {
        System.out.print("Enter your nre PIN: ");
        int newPin = scanner.nextInt();
        scanner.nextInt();
        customer.updatePin(newPin);
    }

    private void exitATM() {
        System.out.println("Thank you for being a customer. Goodbye!");
        System.exit(0);
    }

}


