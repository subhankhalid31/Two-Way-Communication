import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Text {
    private String content;
    private String sender;
    private String recipient;
    private String status;
    private String timestamp;

    public Text(String content, String sender, String recipient) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.status = "Read";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public void markAsRead() {
        this.status = "Read";
    }

    public String getStatus() {
        return status;
    }

    public String getDetails() {
        return "[" + timestamp + "] " + sender + " to " + recipient + ": " + content + " (" + status + ")";
    }
}
