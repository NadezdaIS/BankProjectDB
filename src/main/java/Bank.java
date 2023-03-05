import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import static java.lang.String.valueOf;
public class Bank {
    private static final Scanner scanner = new Scanner(System.in);
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static void signUp() {
        System.out.println("Enter you first name:");
        String name = scanner.nextLine();
        System.out.println("Enter you last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Choose your gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.println("3. Other/Do not want to disclose");
        System.out.println("Select an option:");
        int option = scanner.nextInt();
        String gender = null;
        switch (option) {
            case 1 -> gender = "MALE";
            case 2 -> gender = "FEMALE";
            case 3 -> gender = "OTHER";
            default -> {
                System.out.println("Invalid option. Try again.");
                signUp();
            }
        }
        BigInteger maxLimit = new BigInteger("9000000000000");
        BigInteger minLimit = new BigInteger("1000000000000");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randomNum = new Random();
        int length = maxLimit.bitLength();
        BigInteger accountNum = new BigInteger(length, randomNum);
        if (accountNum.compareTo(minLimit) < 0)
            accountNum = accountNum.add(minLimit);
        if (accountNum.compareTo(bigInteger) >= 0)
            accountNum = accountNum.mod(bigInteger).add(minLimit);
        double balance = 0.00;
        try {
            ps = DBConnection.getConnection().prepareStatement("INSERT INTO users(name, lastName, gender, password, accountNumber, balance) VALUES(?, ?, ?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setString(3, gender);
            ps.setString(4, password);
            ps.setString(5, valueOf(accountNum));
            ps.setDouble(6, balance);
            ps.execute();

            System.out.println("You account has been created, now you can log in.");
            Bank.login();
        } catch (SQLException e) {
            System.out.println("Error. Please try again.");
            System.out.println(e.getMessage());
        }
    }

    public static void login() {
        System.out.println("Enter name: ");
        String loginName = scanner.nextLine();
        System.out.println("Enter password:");
        String loginPassword = scanner.nextLine();
        try {
            ps = DBConnection.getConnection().prepareStatement("SELECT * FROM users WHERE name='" + loginName + "'");
            rs = ps.executeQuery();
            String userName, userPass;
            while (rs.next()) {
                userName = rs.getString("name");
                userPass = rs.getString("password");
                if (userName.equals(loginName) && userPass.equals(loginPassword)) {
                    System.out.println("Login successful");
                    if (userName.equals("admin")){
                        AdminMenu.menu();
                    }
                    UserMenu.menu();

                } else {
                    System.out.println("Username or password is not correct");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error. Please try again.");
        }
    }

    public User getAccountById() {
        System.out.println("Enter the id of the user: ");
        int id = scanner.nextInt();
        try {
            ps = DBConnection.getConnection().prepareStatement("SELECT * FROM users WHERE userId=" + id);
            rs = ps.executeQuery();
            rs.next();
            User user = new User();
            user.userId = rs.getInt("userId");
            user.name = rs.getString("name");
            user.lastName = rs.getString("lastName");
            user.gender = rs.getString("gender");
            user.accountNumber = BigInteger.valueOf(rs.getLong("accountNumber"));
            user.balance = rs.getDouble("balance");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error. Please try again.");
        }
        return null;
    }

    public static void makeDeposit() {
        System.out.println("Deposit amount: ");
        double deposit = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Please confirm your name: ");
        String name = scanner.nextLine();
        try {
            ps = DBConnection.getConnection().prepareStatement("SELECT * FROM users WHERE name='" + name + "'");
            rs = ps.executeQuery();
            double userBalance, newBalance;
            while (rs.next()){
                userBalance = rs.getDouble("balance");
                newBalance = userBalance + deposit;
                ps = DBConnection.getConnection().prepareStatement("UPDATE users SET balance ='" + newBalance + "' WHERE name ='" + name + "'");
                ps.execute();
                System.out.println("Deposited amount was added to your balance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error. Please try again.");
        }
    }
    public static void makeWithdrawal() {
        System.out.println("Withdrawal amount: ");
        double wd = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Please confirm your name: ");
        String name = scanner.nextLine();
        try {
            ps = DBConnection.getConnection().prepareStatement("SELECT * FROM users WHERE name='" + name + "'");
            rs = ps.executeQuery();
            double userBalance, newBalance;
            while (rs.next()){
                userBalance = rs.getDouble("balance");
                newBalance = userBalance - wd;
                ps = DBConnection.getConnection().prepareStatement("UPDATE users SET balance ='" + newBalance + "' WHERE name ='" + name + "'");
                ps.execute();
                System.out.println("Amount was withdrawn from your balance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error. Please try again.");
        }
    }
    public static void showBalance() {
        System.out.println("Please confirm your name: ");
        String name = scanner.nextLine();
        try {
            ps = DBConnection.getConnection().prepareStatement("SELECT * FROM users WHERE name='" + name + "'");
            rs = ps.executeQuery();
            double userBalance;
            while (rs.next()){
                userBalance = rs.getDouble("balance");
                System.out.println("Your balance is: " + userBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error. Please try again.");
        }
    }
    public static void replaceUSer() {
        System.out.println("Enter the id of the user you want to replace: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new name:");
        String newName = scanner.nextLine();
        System.out.println("Enter new last name:");
        String newLastName = scanner.nextLine();
        System.out.println("Choose gender:");
        System.out.println("1. Male");
        System.out.println("2. Female");
        System.out.println("3. Other/Do not want to disclose");
        System.out.println("Select an option:");
        int option = scanner.nextInt();
        String gender = null;
        double newBalance = 0.00;
        switch (option) {
            case 1 -> gender = "MALE";
            case 2 -> gender = "FEMALE";
            case 3 -> gender = "OTHER";
            default -> {
                System.out.println("Invalid option. Try again.");
                replaceUSer();
            }
        }
        try {
            ps = DBConnection.getConnection().prepareStatement("UPDATE users SET name = ?, lastName = ?, gender = ?, balance = ? WHERE userId ="+ id);
            ps.setString(1, newName);
            ps.setString(2, newLastName);
            ps.setString(3, gender);
            ps.setDouble(4, newBalance);
            ps.executeUpdate();
            System.out.println("User is replaced.");
        } catch (SQLException e) {
            System.out.println("Database Error");
        }

    }

}

