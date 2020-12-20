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
        System.out.println(Utils.bytesToImagePath(null));
//        ConfigParser configParser = new ConfigParser();
//        System.out.println(configParser.getPassword());
//        Database db = new Database();
//        List<String> users = db.getUsersInRoom(1);


//        Database db = new Database();


//        LoginData data = new LoginData("Login", "Password");
//        try {
//            String key = Encryptor.generateKey(data);
//            System.out.println(key);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        I2PConnector i2pConnector = new I2PConnector();

        System.out.println("Отправляем сообщение");
        i2pConnector.sendMessage(new Message(
                i2pConnector.getMyAccount(),
                i2pConnector.getMyAccount(),
                "testMsgFromClient", TypeOfMessage.StringMessage));

        System.out.println("Ждем пока что-то появится");
        // зависаем пока нет сообщений
        while (!(i2pConnector.haveNewMessages())) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //получаем буфер писем (с отчисткой!)
        ArrayList<Message> messages = i2pConnector.getNewMessages();
        System.out.println(messages.get(0).type==TypeOfMessage.StringMessage?messages.get(0).message:"Не текст");

        launch(args);
    }
}
