package com.razuvaevdd.I2PConnector;

import com.razuvaevdd.Objects.Account;

import com.razuvaevdd.Objects.Message;

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
        i2pServer.start();
        Account myAccount = getMyAccount();
        new HTTPService(myAccount);
    }

    /*
    Отправляет сообщение
     */
    public static void sendMessage(final Message msg){
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
        Account account = new Account("My Account", i2pServer.getMyDestination());
        return account;
    }
}


