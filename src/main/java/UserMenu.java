import java.util.Scanner;

public class UserMenu {
    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to do?");
        System.out.println("1. Make a deposit ");
        System.out.println("2. Make a withdrawal ");
        System.out.println("3. Show your balance ");
        System.out.println("4. Log off");
        System.out.println("Select an option: ");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                Bank.makeDeposit();
                menu();
            case 2:
                Bank.makeWithdrawal();
                menu();
            case 3:
                Bank.showBalance();
                menu();
            case 4:
                WelcomeMenu.menu();
            default:
                System.out.println("Invalid option. Try again.");
                menu();
        }

    }
}
