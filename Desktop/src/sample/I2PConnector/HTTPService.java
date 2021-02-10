package sample.I2PConnector;

import sample.Objects.*;

//import java.net.*;
import java.util.ArrayList;

public class HTTPService {
    static Account myAccount;

    public HTTPService(Account myAccount) {
        this.myAccount = myAccount;
    }

    public static ArrayList<Message> getNewMessages(){
        return null;
    }

    public static void SendMsg(Message msg) throws Exception {

        System.err.println("[WARN] HTTPService: SendMsg: функционал еще не реализован. Сообщение не отправлено.");
//        String msgStr = msg.type.ordinal()+"<<SYSTEM_X>>" +msg.from.destination
//                +"<<SYSTEM_X>>" + msg.message +"<<SYSTEM_X>>" + msg.hashOfRoom;
//
//        URL url = new URL("https://secchatphpservice.000webhostapp.com/index.php");
    }

    public static boolean haveNewMessages() {
        System.err.println("[WARN] HTTPService: haveNewMessages: функционал еще не реализован. Возвращено false.");
        return false;
    }
}
