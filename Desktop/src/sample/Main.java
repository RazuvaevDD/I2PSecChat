package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sample.Database.Database;
import sample.Database.MessageProcessor;
import sample.I2PConnector.I2PConnector;
import sample.I2PConnector.TypeOfConnection;
import sample.Objects.*;
import sample.Config.ConfigParser;
import sample.gui.MainFrameLogic;

import javax.xml.crypto.Data;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/fxml/LoginFrame.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("I2PSeсChat");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        new I2PConnector(TypeOfConnection.HTTPConnection); // теперь полностью независим от I2P при HTTP
//
//        System.out.println("[DEBUG] Main: "+I2PConnector.getMyAccount().name+
//                "; "+I2PConnector.getMyAccount().destination);
//
//        I2PConnector.sendMessage(new Message(
//                I2PConnector.getMyAccount(),    // отправитель
//                I2PConnector.getMyAccount(),    // получатель
//                "testMsgFromClient",    // сообщение
//                TypeOfMessage.StringMessage,    // тип сообщения
//                "ВКакуюКомнату"));   // хэш комнаты (для понимания в какую комнату идет сообщение,
//                                                //                  не обязателен для использования,
//                                                //                      НО! не может быть пустой строкой.)
//
//        // зависаем пока нет сообщений (HTTP подвиснет максимум на 5 сек из-за таймера)
//        while (!(I2PConnector.haveNewMessages())) {
//            try {
//                System.out.println("[INFO] Main: Ждем пока что-то появится...");
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(I2PConnector.getNewMessages().get(0).message);
//
//        Database.create_all_tables();
//        Database.add_user("Andrey Dolmatov", "private_key1", "public_key1", "", "");
//        Database.add_user("Elena Kurbatova", "private_key2", "public_key2", "", "");
//        Database.add_room("Facility", 5, "String info", "Stringaes_key", "hash1", "");
//        Database.add_room("Gay chat", 5, "String info2", "Stringaes_key2", "hash2", "");
//        Database.add_room("E-FGM", 5, "String info3", "Stringaes_key3", "hash3", "");
//        Database.add_user_to_room(1, 1);
//        Database.add_user_to_room(1, 2);
//        Database.add_user_to_room(2, 1);
//        Database.add_user_to_room(3, 2);
//        Database.add_new_contact(2, 1);
//        Database.add_user(I2PConnector.getMyAccount().name, "KEY", "I love k", "");
//        System.out.println(I2PConnector.getMyAccount().name);

        MessageProcessor.listenForMessages();

        launch(args);


    }
}
