import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;


public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private List<Text> messageHistory;

    public Client(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            messageHistory = new ArrayList<>(); // Initialize client message history
            System.out.println("Connected to server at " + serverAddress + ":" + port);
            new Thread(this::listenForMessages).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages() {
        try {
            String receivedMessage;
            while ((receivedMessage = in.readLine()) != null) {
                System.out.println("Received from server: " + receivedMessage);
                Text receivedText = new Text(receivedMessage, "Server", "Client");
                messageHistory.add(receivedText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToServer(String message) {
        out.println(message);
        out.flush();
        System.out.println("Sent to server: " + message);
        Text sentText = new Text(message, "Client", "Server");
        messageHistory.add(sentText);  // Stored
    }

    public void displayMessageHistory() {
        if (messageHistory.isEmpty()) {
            System.out.println("No messages in history.");
        } else {
            System.out.println("Client message history:");
            for (Text text : messageHistory) {
                System.out.println(text.getDetails());
            }
        }
    }

    public void deleteLastMessage() {
        if (!messageHistory.isEmpty()) {
            messageHistory.remove(messageHistory.size() - 1);  // Remove the last message
            System.out.println("Last message deleted.");
        } else {
            System.out.println("No messages to delete.");
        }
    }
}
