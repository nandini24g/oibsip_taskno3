import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 // Class representing a bank account
 class Account {
    private String userId;
    private String pin;
    private double balance;
    public Account(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
    }
    public String getUserId() {
        return userId;
    }
    public String getPin() {
        return pin;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        balance += amount;
    }
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    public boolean transfer(Account targetAccount, double amount) {
        if (withdraw(amount)) {
            targetAccount.deposit(amount);
            return true;
        }
        return false;
    }
 }
 // Class representing a transaction
 class Transaction {
    private String type;
    private double amount;
    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }
    @Override
    public String toString() {
        return type + ": Rs." + amount;
    }
 }
 // Class managing transaction history
 class TransactionHistory {
    private List<Transaction> transactions;
    public TransactionHistory() {
        transactions = new ArrayList<>();
    }
    public void addTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount));
    }
    public void displayHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
 }
 // Class containing ATM services
 class ATMService {
    private Account account;
    private TransactionHistory transactionHistory;
    public ATMService(Account account) {
        this.account = account;
        this.transactionHistory = new TransactionHistory();
    }
    public void deposit(double amount) {
        account.deposit(amount);
        transactionHistory.addTransaction("Deposit", amount);
        System.out.println("Deposited: Rs." + amount);
    }
    public void withdraw(double amount) {
        if (account.withdraw(amount)) {
            transactionHistory.addTransaction("Withdraw", amount);
            System.out.println("Withdrawn: Rs." + amount);
        } else {
            System.out.println("Insufficient funds.");
        }
    }
    public void transfer(Account targetAccount, double amount) {
        if (account.transfer(targetAccount, amount)) {
            transactionHistory.addTransaction("Transfer", amount);
            System.out.println("Transferred: Rs." + amount);
        } else {
            System.out.println("Transfer failed. Insufficient funds.");
        }
    }
    public void displayBalance() {
        System.out.println("Current Balance: Rs." + account.getBalance());
    }
    public void displayTransactionHistory() {
        transactionHistory.displayHistory();
    }
 }
 // Main class to run the ATM application
 public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Create a sample account
 Account userAccount = new Account("Nandini", "1234", 1000.0);
        // Prompt for user ID and PIN
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        // 
Validate user credentials
        if (userId.equals(userAccount.getUserId()) && pin.equals(userAccount.getPin())) {
 ATMService atmService = new ATMService(userAccount);
            boolean exit = false;
            while (!exit) {
                System.out.println("\nATM Menu:");
                System.out.println("1. Withdraw");
                System.out.println("2. Deposit");
                System.out.println("3. Transfer");
                System.out.println("4. Transaction History");
                System.out.println("5. Check Balance");
                System.out.println("6. Quit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        atmService.withdraw(withdrawAmount);
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        atmService.deposit(depositAmount);
                        break;
                    case 3:
                        System .out.print("Enter target account ID: ");
                        String targetAccountId = scanner.next();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        // Create a sample target account
 Account targetAccount = new Account(targetAccountId, "1234", 500.0);
                        atmService.transfer(targetAccount, transferAmount);
                        break;
                    case 4:
                        atmService.displayTransactionHistory();
                        break;
                    case 5:
                        atmService.displayBalance();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            }
        } else {
            System.out.println("Invalid user ID or PIN. Please try again.");
        }
    }
 }