package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sample.Database.Database;
import sample.Encryptor.Encryptor;
import sample.Encryptor.LoginData;
import sample.I2PConnector.I2PConnector;
import sample.Objects.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import sample.Utils.Utils;

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
        new I2PConnector();
        Database.create_all_tables();
        Database.add_user(I2PConnector.getMyAccount().name, "KEY", "I love k", "");
        System.out.println(I2PConnector.getMyAccount().name);
        launch(args);
    }

//    public static void main(String[] args) {
//        I2PConnector i2pConnector = new I2PConnector();
////        System.out.println("Отправляем сообщение");
////        i2pConnector.sendMessage(new Message(
////                i2pConnector.getMyAccount(), //sender
////                i2pConnector.getMyAccount(), //customer
////                "testMsgFromClient", TypeOfMessage.StringMessage));
//        System.out.println("Ждем пока что-то появится");
//        // зависаем пока нет сообщений
////        while (!(i2pConnector.haveNewMessages())) {
////            try {
////                Thread.sleep(5000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//        //получаем буфер писем (с очисткой!)
////        ArrayList<Message> messages = i2pConnector.getNewMessages();
////        System.out.println(messages.get(0).type==TypeOfMessage.StringMessage?messages.get(0).message:"Не текст");
////
////        System.out.print(messages);
//
//        Database db = new Database();
//        db.create_all_tables();
//        db.add_user(i2pConnector.getMyAccount().name, "KEY", "I love dicks", "");
//        int account_id = db.get_id("user", i2pConnector.getMyAccount().name);
//        System.out.println(account_id);
//
//
//
//        launch(args);
//    }
}
