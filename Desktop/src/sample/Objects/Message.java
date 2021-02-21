package sample.Objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Этот класс реализует объект "Сообщение".
 * Имеет собственную проверку корректности и упрощает работу с обработкой сообщений.
 * @author Razuvaev Daniil
 **/
public class Message {
    public Account from;
    public Account to;
    public String message = "default_message";
    public TypeOfMessage type = TypeOfMessage.StringMessage;
    public String hashOfRoom = "RoomHash";
    public String time;

    public Message() {
    }

    public Message(Account from, Account to, String message, TypeOfMessage type) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
        setCurrentTime();
        System.out.println("[INFO] Message: Message.time: автоматически установлено текущее время: "+time);
        validate();
    }

    public Message(Account from, Account to, String message, TypeOfMessage type, String hashOfRoom) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
        this.hashOfRoom = hashOfRoom;
        setCurrentTime();
        System.out.println("[INFO] Message: Message.time: автоматически установлено текущее время: "+time);
        validate();
    }

    public Message(Account from, Account to, String message, TypeOfMessage type, String hashOfRoom, String time) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
        this.hashOfRoom = hashOfRoom;
        this.time = time;
        validate();
    }

    private void setCurrentTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.time = myDateObj.format(myFormatObj);
    }

    private void validate(){
        if (this.message.isEmpty()){
            System.err.println("[UNCRITICAL ERROR] Message: Message.message не может быть пустым!");
            this.message = "default_message";
            System.err.println("[WARN] Message: Message.message заменен на дефолтное значение: \""+this.message+"\"");
        }
        if (this.hashOfRoom.isEmpty()){
            System.err.println("[UNCRITICAL ERROR] Message: Message.hashOfRoom не может быть пустым!");
            this.hashOfRoom = "RoomHash";
            System.err.println("[WARN] Message: Message.hashOfRoom заменен на дефолтное значение: \""+this.hashOfRoom+"\"");
        }
        if (this.time.isEmpty()){
            System.err.println("[UNCRITICAL ERROR] Message: Message.time не может быть пустым!");
            setCurrentTime();
            System.err.println("[WARN] Message: Message.time заменен на текущее время: \""+this.time+"\"");
        }
    }

    public boolean isValid() {
        if(this.message.isEmpty() || this.hashOfRoom.isEmpty() || this.time.isEmpty())
            return false;
        else
            return true;
    }
}
