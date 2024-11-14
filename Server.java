import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Text> messageHistory = new ArrayList<>();
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isRunning;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started, waiting for clients...");
        clientSocket = serverSocket.accept();
        System.out.println("Client connected.");
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        isRunning = true;

        new Thread(this::listenForMessages).start();

        sendMessageToClient("Welcome to the server!");
    }


    private void listenForMessages() {
        try {
            String receivedMessage;

            while (isRunning) {
                if ((receivedMessage = in.readLine()) != null) {
                    System.out.println("Received message: " + receivedMessage);
                    Text receivedText = new Text(receivedMessage, "Client", "Server");
                    messageHistory.add(receivedText);
                    sendAcknowledgement();
                }
                // To Make Other Parts Work Too!
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error receiving message from client.");
            e.printStackTrace();
        }
    }

    private void sendAcknowledgement() {
        out.println("Parh Lia Ha.");
    }

    public void sendMessageToClient(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public void displayMessageHistory() {
        System.out.println("Server message history:");
        for (Text text : messageHistory) {
            System.out.println(text.getDetails());
        }
    }


    public void deleteLastMessage() {
        if (messageHistory.size() > 0) {
            messageHistory.remove(messageHistory.size() - 1);
            System.out.println("Last message deleted.");
        } else {
            System.out.println("No messages to delete.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            int port = 4999;
            Server server = new Server(port);

            while (server.isRunning) {
                // Show the options menu
                System.out.println("\nChoose an option:");
                System.out.println("1. Send Message to Client");
                System.out.println("2. Display Chat History");
                System.out.println("3. Delete Last Message");
                System.out.println("4. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter message to send to client: ");
                        String message = scanner.nextLine();
                        server.sendMessageToClient(message);
                        break;

                    case 2:
                        server.displayMessageHistory();
                        break;

                    case 3:
                        server.deleteLastMessage();
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        server.isRunning = false;
                        server.clientSocket.close();
                        server.serverSocket.close();
                        break;

                    default:
                        System.out.println("Invalid option. Please choose a valid number.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
