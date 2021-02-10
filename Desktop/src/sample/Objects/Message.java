package sample.Objects;

public class Message {
    public Account from;
    public Account to;
    public String message;
    public TypeOfMessage type = TypeOfMessage.StringMessage;
    public String hashOfRoom = "";

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
