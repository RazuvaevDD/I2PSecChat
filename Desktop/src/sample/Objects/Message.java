package sample.Objects;

public class Message {
    public static Account from;
    public static Account to;
    public static String message;
    public static TypeOfMessage type = TypeOfMessage.StringMessage;
    public static String hashOfRoom = "";

    public Message() {
    }

    public Message(Account from, Account to, String message, TypeOfMessage type) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
    }

    public Message(Account from, Account to, String message, TypeOfMessage type, String hashOfRoom) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
        this.hashOfRoom = hashOfRoom;
    }
}
