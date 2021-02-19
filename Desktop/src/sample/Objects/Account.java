package sample.Objects;

public class Account {
    public String name;         // имя контакта
    public String destination;  // i2p адрес контакта (это как статичный ip в стеке tcp/ip)

    public Account() {
    }

    public Account(String name, String destination) {
        this.name = name;
        this.destination = destination;
    }
}
