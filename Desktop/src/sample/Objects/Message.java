package sample.Objects;

public class Message {
    public Account from;
    public Account to;
    public String message;
    public TypeOfMessage type = TypeOfMessage.StringMessage;
    public String hashOfRoom = "RoomHash";

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
        if (hashOfRoom.isEmpty()){
            System.err.println("[CRITICAL ERROR]: Message object не может иметь совершенно пустой хэш комнаты!");
            System.exit(-1);
        }
        this.hashOfRoom = hashOfRoom;
    }
}
