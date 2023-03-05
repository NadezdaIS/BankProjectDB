import java.util.Scanner;

public class AdminMenu {
    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to do?");
        System.out.println("1. Replace user ");
        System.out.println("2. Get user information by ID ");
        System.out.println("3. Log off");
        System.out.println("Select an option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                Bank.replaceUSer();
                menu();
            case 2:
                Bank bank = new Bank();
                User user = bank.getAccountById();
                if (user != null) {
                    System.out.println("User ID: " + user.userId);
                    System.out.println("Name: " + user.name);
                    System.out.println("Last name: " + user.lastName);
                    System.out.println("Gender: " + user.gender);
                    System.out.println("Account number: " + user.accountNumber);
                    System.out.println("Balance: " + user.balance);
                } else {
                    System.out.println("No user found.");
                }
                menu();
            case 3:
                WelcomeMenu.menu();
            default:
                System.out.println("Invalid option. Try again.");
                menu();
        }

    }
}
