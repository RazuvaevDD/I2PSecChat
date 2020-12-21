package sample.I2PConnector;

import sample.Objects.Account;
import sample.Objects.Message;

import java.util.ArrayList;

public class I2PConnector {

    private static boolean isWorking = false;

    private static I2PServer i2pServer = new I2PServer();
    private static I2PClient i2pClient = new I2PClient();

    public I2PConnector(){
        i2pServer.start();
        getMyAccount();
    }

    public static void sendMessage(Message msg){
        i2pClient.SendMsg(msg);
    }

    public static ArrayList<Message> getNewMessages(){
        return i2pServer.getNewMessages();
    }

    public static boolean haveNewMessages(){
        return i2pServer.haveNewMessages();
    }

    /*
    Возвращает аккаунт пользователя или зависает, ожидая включения сервера.
     */
    public static Account getMyAccount() {
        while(i2pServer.getMyDestination().isEmpty()){
            try {
                System.out.println("Ожидание включения сервера...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Account account = new Account("My Account", i2pServer.getMyDestination());
        return account;
    }
}

class StartServer extends Thread {

    StartServer(String name){
        super(name);
    }

    public void run(){

    }
}
