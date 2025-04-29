
import java.util.*;

// Custom Exception
class InsufficientFundsException extends Exception {
    private static final long serialVersionUID = 1L; // Added to remove warning

    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Base Account Class
abstract class Account {
    protected String accountNumber;
    protected String accountHolderName;
    protected double balance;
    protected List<String> miniStatement = new ArrayList<>();

    public Account(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        miniStatement.add("Account created with balance: ₹" + initialBalance);
    }

    public void deposit(double amount) {
        balance += amount;
        miniStatement.add("Deposited: ₹" + amount + " | New Balance: ₹" + balance);
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (balance >= amount) {
            balance -= amount;
            miniStatement.add("Withdrawn: ₹" + amount + " | New Balance: ₹" + balance);
        } else {
            throw new InsufficientFundsException("Insufficient balance! Available: ₹" + balance);
        }
    }

    public double checkBalance() {
        return balance;
    }

    public void printMiniStatement() {
        System.out.println("\nMini Statement for " + accountHolderName + " (" + accountNumber + "):");
        for (String entry : miniStatement) {
            System.out.println(entry);
        }
    }

    public abstract String getAccountType();
}

// SavingAccount Class
class SavingAccount extends Account {
    public SavingAccount(String accountNumber, String accountHolderName, double initialBalance) {
        super(accountNumber, accountHolderName, initialBalance);
    }

    @Override
    public String getAccountType() {
        return "Saving Account";
    }
}

// CurrentAccount Class
class CurrentAccount extends Account {
    public CurrentAccount(String accountNumber, String accountHolderName, double initialBalance) {
        super(accountNumber, accountHolderName, initialBalance);
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }
}

// Bank Class to manage accounts
class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public void createAccount(String type, String accountNumber, String holderName, double initialBalance) {
        Account account = null;
        if (type.equalsIgnoreCase("saving")) {
            account = new SavingAccount(accountNumber, holderName, initialBalance);
        } else if (type.equalsIgnoreCase("current")) {
            account = new CurrentAccount(accountNumber, holderName, initialBalance);
        }
        if (account != null) {
            accounts.put(accountNumber, account);
            System.out.println(type + " account created successfully!");
        } else {
            System.out.println("Invalid account type!");
        }
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

// Main Class
public class BankAccountSimulation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();
        
        while (true) {
            System.out.println("\n1. Create Account\n2. Deposit\n3. Withdraw\n4. Check Balance\n5. Mini Statement\n6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter account type (Saving/Current): ");
                    String type = sc.nextLine();
                    System.out.print("Enter account number: ");
                    String accNum = sc.nextLine();
                    System.out.print("Enter holder name: ");
                    String holderName = sc.nextLine();
                    System.out.print("Enter initial balance: ");
                    double balance = sc.nextDouble();
                    bank.createAccount(type, accNum, holderName, balance);
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    accNum = sc.nextLine();
                    Account acc = bank.getAccount(accNum);
                    if (acc != null) {
                        System.out.print("Enter amount to deposit: ");
                        double amount = sc.nextDouble();
                        acc.deposit(amount);
                        System.out.println("Amount deposited successfully!");
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    accNum = sc.nextLine();
                    acc = bank.getAccount(accNum);
                    if (acc != null) {
                        System.out.print("Enter amount to withdraw: ");
                        double amount = sc.nextDouble();
                        try {
                            acc.withdraw(amount);
                            System.out.println("Amount withdrawn successfully!");
                        } catch (InsufficientFundsException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter account number: ");
                    accNum = sc.nextLine();
                    acc = bank.getAccount(accNum);
                    if (acc != null) {
                        System.out.println("Available Balance: ₹" + acc.checkBalance());
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 5:
                    System.out.print("Enter account number: ");
                    accNum = sc.nextLine();
                    acc = bank.getAccount(accNum);
                    if (acc != null) {
                        acc.printMiniStatement();
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 6:
                    System.out.println("Thank you for using Bank Account Simulation!");
                    sc.close();
                    System.exit(0);
                    break; // Added to remove fall-through warning

                default:
                    System.out.println("Invalid choice! Try again.");
                    break;
            }
        }
    }
}
