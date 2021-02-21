package com.razuvaevdd.I2PConnector;

import android.content.Context;
import android.provider.Settings;

import com.razuvaevdd.Objects.*;
import java.util.ArrayList;
/**
 * Этот класс реализует API I2PConnector. Одна из его основных функций - это
 * соединение серверной части I2P, клиентской части I2P и HTTP клиента.
 * @author Razuvaev Daniil
 **/
public class I2PConnector {

    private static TypeOfConnection connectionType = TypeOfConnection.I2PConnection;

    private static I2PServer i2pServer = new I2PServer();
    private static I2PClient i2pClient = new I2PClient();
    private static HTTPService httpService;
    private static Context context;
    /*
    Устанавливает тип соединения, которое используется для работы модуля
     */
    public static void setConnectionType(TypeOfConnection connectionType){
        I2PConnector.connectionType = connectionType;
    }

    /*
    Запускает клиент/сервер и дополнительные сервисы
     */
    public I2PConnector(Context context){
        this.context = context;
        i2pServer.start();
        Account myAccount = getMyAccount();
        httpService = new HTTPService(context, myAccount);
    }

    /*
    Запускает клиент/сервер и дополнительные сервисы
     */
    public I2PConnector(Context context, TypeOfConnection connectionType){
        this.context = context;
        this.connectionType = connectionType;
        if(connectionType==TypeOfConnection.I2PConnection)
            i2pServer.start();
        Account myAccount = getMyAccount();
        httpService = new HTTPService(context, myAccount);
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
            httpService.SendMsg(msg);
        }
    }

    /*
    Получает новые сообщения
     */
    public static ArrayList<Message> getNewMessages(){
        if (connectionType == TypeOfConnection.I2PConnection)
            return i2pServer.getNewMessages();
        if (connectionType == TypeOfConnection.HTTPConnection)
            return httpService.getNewMessages();
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
            return httpService.haveNewMessages();
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
            System.out.println("[DEBUG] I2PConnector: SecureID="+Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
            return new Account("My Android Account HTTP", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        }
    }
}


