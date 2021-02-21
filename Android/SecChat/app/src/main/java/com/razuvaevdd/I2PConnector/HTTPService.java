package com.razuvaevdd.I2PConnector;

import android.content.Context;

import com.razuvaevdd.Objects.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Этот класс реализует HTTP клиент.
 * @author Razuvaev Daniil
 **/
public class HTTPService {
    Account myAccount;
    private int errorStack = 0;
    private static final int MAX_ERROR_STACK = 20;
    private String internalException = null;
    private Context context;

    public HTTPService(Context context, Account myAccount) {
        this.myAccount = myAccount;
        this.context = context;
    }

    public ArrayList<Message> getNewMessages() {
        ArrayList<Message> messages = new ArrayList();
        System.out.println("[INFO] HTTPService: Получение сообщений...");
        try {
            URL url = new URL("http://secchatphpservice.000webhostapp.com/api.php");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            Map<String,String> arguments = new HashMap<>();
            arguments.put("account", myAccount.destination);
            arguments.put("command", "pull");
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }

            InputStreamReader isReader = new InputStreamReader(http.getInputStream());
            BufferedReader reader = new BufferedReader(isReader);

            String res = reader.lines().collect(Collectors.joining());

            JSONArray jsonArray = new JSONArray(res);


            for (int i = 0; i<jsonArray.length(); i++) {
                //System.out.println("[DEBUG] JSONARRAY("+i+"):"+jsonArray.get(i));
                String s[]; s = jsonArray.get(i).toString().split("<<SYSTEM_X>>");
                messages.add(new Message(
                                new Account("From", s[1]),
                                new Account("To", myAccount.destination),
                                s[2],
                                TypeOfMessage.values()[Integer.parseInt(s[0])],
                                s[3],
                                s[4]
                        )
                );
            }
        } catch (Exception e) {
            internalException = e.getMessage();
        }
        if(errorStack > MAX_ERROR_STACK){
            System.err.println("[UNCRITICAL ERROR] HTTPService: getNewMessages(): "+internalException);
            System.err.println("[WARN] HTTPService: getNewMessages(): Список сообщений возвращает пустой список.");
            return messages;
        }
        errorStack++;
        if(haveNewMessages())
            messages = getNewMessages();
        errorStack = 0;
        return messages;
    }

    public void SendMsg(Message msg) {
        System.out.println("[INFO] HTTPService: Отправка сообщения...");
        try{
            String msgStr = msg.type.ordinal()+"<<SYSTEM_X>>" +myAccount.destination
                    +"<<SYSTEM_X>>" + msg.message +"<<SYSTEM_X>>" + msg.hashOfRoom + "<<SYSTEM_X>>"+ msg.time;

            URL url = new URL("http://secchatphpservice.000webhostapp.com/api.php");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            Map<String,String> arguments = new HashMap<>();
            arguments.put("account", msg.to.destination);
            arguments.put("message", msgStr);
            arguments.put("command", "push");
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            InputStreamReader isReader = new InputStreamReader(http.getInputStream());
            BufferedReader reader = new BufferedReader(isReader);

            String res = reader.lines().collect(Collectors.joining());
            System.out.println("[INFO] HTTPService: Сообщение успешно отправлено."+res);
            return;
        } catch (Exception e) {
            internalException = e.getMessage();
        }
        if(errorStack > MAX_ERROR_STACK){
            System.err.println("[UNCRITICAL ERROR] HTTPService: SendMsg(Message): "+internalException);
            System.err.println("[WARN] HTTPService: SendMsg(Message): Сообщение не отправлено.");
            return;
        }
        errorStack++;
        SendMsg(msg);
        errorStack = 0;
    }

    public boolean haveNewMessages() {
        System.out.println("[INFO] HTTPService: Проверка наличия сообщений...");
        try {
            URL url = new URL("http://secchatphpservice.000webhostapp.com/api.php");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            Map<String,String> arguments = new HashMap<>();
            arguments.put("account", myAccount.destination);
            arguments.put("command", "check");
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }


            InputStreamReader isReader = new InputStreamReader(http.getInputStream());
            BufferedReader reader = new BufferedReader(isReader);

            String res = reader.lines().collect(Collectors.joining());

            if(res.equals("true")){
                return true;
            } else if(res.equals("false")){
                return false;
            } else {
                throw new Exception("Сервер возвратил неожиданный ответ: \""+res+"\"");
            }

        }catch (Exception e) {
            internalException = e.getMessage();
        }
        if(errorStack > MAX_ERROR_STACK){
            System.err.println("[UNCRITICAL ERROR] HTTPService: haveNewMessages(): "+internalException);
            System.err.println("[WARN] HTTPService: haveNewMessages(): Возвращаем false.");
            return false;
        }
        errorStack++;
        boolean res = haveNewMessages();
        errorStack = 0;
        return res;
    }
}
