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
import sample.Objects.Account;
import sample.Objects.Message;
import sample.Objects.TypeOfMessage;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/sample.fxml"));
        primaryStage.setTitle("I2PSecChat");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {

//        Database db = new Database();
//        List<String> users = db.getUsersInRoom(1);
//        System.out.println(users);

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
