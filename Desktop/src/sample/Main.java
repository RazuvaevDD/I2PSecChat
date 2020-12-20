package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sample.Database.Database;
import sample.Encryptor.Encryptor;
import sample.Encryptor.LoginData;
import sample.Config.ConfigParser;
import sample.GUI_InfoShower.InfoShower;

import java.security.NoSuchAlgorithmException;
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
        InfoShower infoShower = new InfoShower();
        System.out.println(infoShower.getDate());
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

        launch(args);
    }
}
