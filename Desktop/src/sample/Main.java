package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sample.Database.Database;
import sample.Encryptor.Encryptor;
import sample.Encryptor.LoginData;

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

        Database db = new Database();
        List<String> users = db.getUsersInRoom(1);
//        db.add_user("test", "test2", "infffa", "");
//        db.add_room("test_room", 3, "infffa", "aes_key", "");
        System.out.println(db.getAllUsers());
        System.out.println(db.getAllRooms());
        System.out.println(db.get_id("user", "test"));
//        db.create_all_tables();
//        db.print_all_tables();
//        db.drop_table("user");
//        db.drop_table("room");
//        db.drop_table("rooms");
//        db.drop_table("message");
//        db.print_all_tables();

//        db.register_message(2, 3, "te222xt1", "2020-12-20 14:13:43.342");
//        db.register_message(2, 2, "tex222t2", "2020-12-20 14:14:43.342");
//        db.register_message(2, 3, "tex222t3", "2020-12-20 14:15:43.342");
//        db.register_message(2, 2, "tex222t4", "2020-12-20 14:16:43.342");

        List<List<String>> test = db.getMessagesInRoom(1);
//        System.out.println(test);
//        for (String[] strings : test) {
//            System.out.println(strings);
//        }

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
