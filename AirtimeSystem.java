class MobileAccount {
    // CONSTANTS
    static final double RATE_PER_MINUTE = 30.0;
    static final double SMS_COST = 50.0;
    static final double MAX_TOPUP = 100_000.0;

    // INSTANCE FIELDS private for encapsulation
    private String ownerName;
    private String phoneNumber;
    private double balance;
    private boolean active;

    // STATIC FIELDS
    private static int totalAccountsCreated = 0;
    private static double totalMoneyLoaded = 0.0;

    // CONSTRUCTOR
    MobileAccount(String ownerName, String phoneNumber) {
        // use this to avoid shadowing
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.balance = 0.0;
        this.active = true;

        // increase counter when account is created
        totalAccountsCreated++;
    }

    // TOP UP METHOD
    boolean topUp(double amount) {
        // validate amount
        if (amount <= 0 || amount > MAX_TOPUP) {
            System.out.println("Top-up rejected: invalid amount.");
            return false;
        }

        // add money to balance
        balance += amount;

        // add to global total
        totalMoneyLoaded += amount;

        return true;
    }

    // MAKE CALL METHOD
    boolean makeCall(double minutes) {
        // check if active
        if (!active) {
            System.out.println("Call failed: account inactive.");
            return false;
        }

        double cost = minutes * RATE_PER_MINUTE;

        // check if enough balance
        if (balance < cost) {
            System.out.println("Call failed: insufficient balance.");
            return false;
        }

        // deduct cost
        balance -= cost;
        return true;
    }

    // SEND SMS METHOD
    boolean sendSms(int count) {
        // check if active
        if (!active) {
            System.out.println("SMS failed: account inactive.");
            return false;
        }

        double cost = count * SMS_COST;

        // check if enough balance
        if (balance < cost) {
            System.out.println("SMS failed: insufficient balance.");
            return false;
        }

        // deduct cost
        balance -= cost;
        return true;
    }

    // ACTIVATE and DEACTIVATE
    void deactivate() {
        active = false;
    }

    void activate() {
        active = true;
    }

    // GETTERS
    double getBalance() {
        return balance;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    String getOwnerName() {
        return ownerName;
    }

    boolean isActive() {
        return active;
    }

    // STATIC GETTERS
    static int getTotalAccountsCreated() {
        return totalAccountsCreated;
    }

    static double getTotalMoneyLoaded() {
        return totalMoneyLoaded;
    }

    // PRINT STATEMENT
    void printStatement() {
        System.out.printf("[%s] %-20s  Balance: %10.2f TZS  %s%n",
                phoneNumber, ownerName, balance,
                active ? "ACTIVE" : "INACTIVE");
    }
}

// DRIVER CLASS
public class AirtimeSystem {

    public static void main(String[] args) {

        // create accounts
        MobileAccount acc1 = new MobileAccount("Amina Hassan",  "0712-345-678");
        MobileAccount acc2 = new MobileAccount("Baraka Juma",   "0755-987-654");
        MobileAccount acc3 = new MobileAccount("Neema Salehe",  "0623-111-222");

        // top ups
        acc1.topUp(10_000);
        acc2.topUp(5_000);
        acc3.topUp(20_000);

        // calls and sms
        acc1.makeCall(50);
        acc2.makeCall(200);
        acc1.sendSms(5);
        acc3.sendSms(10);

        // invalid top ups
        acc1.topUp(-500);
        acc1.topUp(150_000);

        // print report
        System.out.println("================================================================");
        System.out.println("              CS 234 — AIRTIME ACCOUNT REPORT");
        System.out.println("================================================================");

        MobileAccount[] accounts = {acc1, acc2, acc3};

        for (MobileAccount acc : accounts) {
            acc.printStatement();
        }

        System.out.println("================================================================");
        System.out.printf("Total Accounts Created  :  %d%n",
                MobileAccount.getTotalAccountsCreated());
        System.out.printf("Total Money Loaded      :  %.2f TZS%n",
                MobileAccount.getTotalMoneyLoaded());
        System.out.println("================================================================");
    }
}