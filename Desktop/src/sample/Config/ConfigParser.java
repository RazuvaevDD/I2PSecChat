package sample.Config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


public class ConfigParser {
    /*private static String filePath = "";

    public ConfigParser() {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        filePath = currentDirectory + "\\src\\sample\\Config\\config.json";
    }*/

    public static String getPassword() {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String filePath = currentDirectory + "\\src\\sample\\Config\\config.json";
        String password = "";
        try {
            System.out.println(filePath);
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            password = (String) jsonObject.get("password");
        } catch (IOException | ParseException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return password;
    }
}
