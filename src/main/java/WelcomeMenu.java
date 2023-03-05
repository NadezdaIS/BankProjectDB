import java.util.Scanner;
public class WelcomeMenu {
        public static void menu() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome! Choose one: ");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("Select an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> Bank.login();
                case 2 -> Bank.signUp();
                default -> {
                    System.out.println("Invalid option. Try again.");
                    menu();
                }
            }
        }
    }

