import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter 1 to start as Server or 2 to start as Client: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            //Port Pre-ASSIGNED Ha-
            new Server(4999);
        } else if (choice == 2) {
            System.out.print("Enter the server IP address: ");
            String serverAddress = scanner.nextLine();
            try {
                Client client = new Client(serverAddress, 4999);
                while (true) {
                    System.out.println("\n1. Send Message\n" +
                            "2. Display Chat History\n" +
                            "3. Delete Last Message\n" +
                            "4. Exit");
                    int clientChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (clientChoice) {
                        case 1:
                            System.out.print("Enter message to send to server: ");
                            String message = scanner.nextLine();
                            client.sendMessageToServer(message);
                            break;
                        case 2:
                            client.displayMessageHistory();
                            break;
                        case 3:
                            client.deleteLastMessage();
                            break;
                        case 4:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid option.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
    }
}
