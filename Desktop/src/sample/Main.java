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
        db.create_all_tables();
        db.print_all_tables();
//        db.drop_table();

//        db.add_user("Ivanov Ivan", "key1", "info1");
//        db.add_user("Petrov Petr", "key2", "info2");
//        db.add_user("Vasiliev Vasiliy", "key3", "info3");
//
//        db.add_room("chat1", 5, "chat_info1", "chat_key1");
//        db.add_room("chat2", 5, "chat_info2", "chat_key2");
//
////        db.add_user_to_room(0, 1);
        db.add_user_to_room(223, 7);
////
////        db.register_message(0, 1, "message_text", "19.12.2020");
//
////        db.delete_user(1);
////        db.delete_room(1);
////        db.change_user_info(5, "changed info");
        db.print_all_tables();

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
