package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        launch(args);
    }
}
