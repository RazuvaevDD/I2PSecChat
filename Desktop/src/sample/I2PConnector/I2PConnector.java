package sample.I2PConnector;

import sample.Objects.Account;
import sample.Objects.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class I2PConnector {

    private static TypeOfConnection connectionType = TypeOfConnection.I2PConnection;

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
    Запускает клиент/сервер и дополнительные сервисы
     */
    public I2PConnector(TypeOfConnection connectionType){
        this.connectionType = connectionType;
        if(connectionType==TypeOfConnection.I2PConnection)
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
            HTTPService.SendMsg(msg);
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
        if(connectionType==TypeOfConnection.I2PConnection){
            while(i2pServer.getMyDestination().isEmpty()){
                try {
                    System.out.println("[INFO] I2PConnector: Ожидаем пока маршрутизатор строит туннели...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!i2pServer.getMyDestination().isEmpty()){
                    System.out.println("[INFO] I2PConnector: Туннель выделен!");
                }
            };
            Account account = new Account("My Account I2P", i2pServer.getMyDestination());
            return account;
        }
        else{
            if(isWindows()) {
                try {
                    String command = "wmic csproduct get UUID";
                    StringBuffer output = new StringBuffer();

                    Process SerNumProcess = Runtime.getRuntime().exec(command);
                    BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));

                    while (true) {
                        String line = "";
                        if (!((line = sNumReader.readLine()) != null)) break;

                        output.append(line + "\n");
                    }
                    String MachineID = output.toString().substring(output.indexOf("\n"), output.length()).trim();
                    ;
                    System.out.println(MachineID);
                    return new Account("My Windows Account HTTP", MachineID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(isUnix()){
                System.err.println("[CRITICAL_ERROR] I2PConnector: Не реализовано для Linux!");
                System.exit(-1);
            }
            System.err.println("[CRITICAL_ERROR] I2PConnector: Не обнаружен идентификатор устройства!");
            System.exit(-1);

            return null;
        }
    }

    private static boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        //windows
        return (os.indexOf( "win" ) >= 0);
    }

    private static boolean isMac(){
        String os = System.getProperty("os.name").toLowerCase();
        //Mac
        return (os.indexOf( "mac" ) >= 0);
    }

    private static boolean isUnix (){
        String os = System.getProperty("os.name").toLowerCase();
        //linux or unix
        return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
    }
    private static String getOSVerion() {
        String os = System.getProperty("os.version");
        return os;
    }
}


