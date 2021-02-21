package sample.Database;

import sample.I2PConnector.I2PConnector;
import sample.Objects.Account;
import sample.Objects.Message;

import java.util.List;

public class MessageProcessor {
    public static void listenForMessages() {
        Thread t = new Thread(() -> {
            Account currentUser = I2PConnector.getMyAccount();
            int currentUserID = Database.get_id("user", currentUser.name);
            if (currentUserID == 0) {
                Database.add_user(currentUser.name, currentUser.destination, currentUser.destination, "", "");
            }

            System.out.println(currentUser.destination);

            while (true) {
                while (!(I2PConnector.haveNewMessages())) {
                    try {
                        System.out.println("[INFO] MessageProcessor: Ждем пока что-то появится...");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                List<Message> newMessages = I2PConnector.getNewMessages();
                for (Message message : newMessages) {
                    if (!message.from.destination.equals(currentUser.destination)) {
                        Database.register_message(Database.getRoomIdbyHash(message.hashOfRoom), message.hashOfRoom, Database.getIdByHash("user", message.from.destination),
                                Database.getIdByHash("user", message.to.destination), message.message, message.time);
                        I2PConnector.sendMessage(message);
                    }

                }
            }
        });
        t.start();
    }
}
