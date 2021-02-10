package sample.I2PConnector;

import sample.Objects.Account;
import sample.Objects.Message;

import java.util.ArrayList;

public class I2PConnector {

    private static TypeOfConnection connectionType = TypeOfConnection.I2PConnection;
    private static boolean isWorking = false;

    private static I2PServer i2pServer = new I2PServer();
    private static I2PClient i2pClient = new I2PClient();

    /*
    Устанавливает тип соединения, которое используется для работы модуля
     */
    public static void setConnectionType(TypeOfConnection connectionType){
        I2PConnector.connectionType = connectionType;
    }

    /*
    Запускает клиент/сервер и дополнительные сервисы
     */
    public I2PConnector(){
        i2pServer.start();
        Account myAccount = getMyAccount();
        new HTTPService(myAccount);
    }

    /*
    Отправляет сообщение
     */
    public static void sendMessage(Message msg){
        if(connectionType == TypeOfConnection.I2PConnection){
            Thread t = new Thread(new Runnable() {
                public void run() {
                    i2pClient.SendMsg(msg);
                }
            });
            t.start();
        }
        if(connectionType == TypeOfConnection.HTTPConnection){
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        HTTPService.SendMsg(msg);
                    } catch (Exception e) {
                        System.err.println("[WARN] HTTPService: Сообщение не доставлено. Причина: Внутренняя ошибка.");
                        System.err.println("========СТЭК========");
                        e.printStackTrace();
                        System.err.println("====конец==стека====");
                    }
                }
            });
            t.start();
        }
    }

    /*
    Получает новые сообщения
     */
    public static ArrayList<Message> getNewMessages(){
        if (connectionType == TypeOfConnection.I2PConnection)
            return i2pServer.getNewMessages();
        if (connectionType == TypeOfConnection.HTTPConnection)
            return HTTPService.getNewMessages();
        System.err.println("[WARN] I2PConnector: данный connectionType не поддерживается.");
        return null;
    }

    /*
    Проверяет, были ли получены новые сообщения
     */
    public static boolean haveNewMessages(){
        if (connectionType == TypeOfConnection.I2PConnection)
            return i2pServer.haveNewMessages();
        if (connectionType == TypeOfConnection.HTTPConnection)
            return HTTPService.haveNewMessages();
        System.err.println("[WARN] I2PConnector: данный connectionType не поддерживается.");
        return false;
    }

    /*
    Возвращает аккаунт пользователя или зависает, ожидая включения сервера.
     */
    public static Account getMyAccount() {
        while(i2pServer.getMyDestination().isEmpty()){
            try {
                System.out.println("[INFO] I2PConnector: Ожидание включения сервера...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Account account = new Account("My Account", i2pServer.getMyDestination());
        return account;
    }
}


