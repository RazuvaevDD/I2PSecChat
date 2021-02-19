package sample.Database;

import sample.I2PConnector.I2PConnector;
import sample.Objects.Message;

import java.util.List;

public class MessageProcessor {
    public static void listenForMessages() {
        Thread t = new Thread(() -> {
            while (!(I2PConnector.haveNewMessages())) {
                try {
                    System.out.println("[INFO] Main: Ждем пока что-то появится...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            List<Message> newMessages = I2PConnector.getNewMessages();
            for (Message message: newMessages) {
                Database.register_message(Database.getRoomIdbyHash(message.hashOfRoom), message.hashOfRoom, Database.get_id("user", message.from.name),
                        Database.get_id("user", message.to.name), message.message, message.time);
            }
        });
        t.start();
    }
}
