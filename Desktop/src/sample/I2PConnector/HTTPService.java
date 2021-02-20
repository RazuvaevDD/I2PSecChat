package sample.I2PConnector;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import sample.Objects.*;

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
    private static final int MAX_ERROR_STACK = 100;

    public HTTPService(Account myAccount) {
        this.myAccount = myAccount;
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

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            Iterator<String> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                String s[]; s = iterator.next().split("<<SYSTEM_X>>");
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
            //e.getMessage().contains(substring)
            System.err.println("[UNCRITICAL ERROR] HTTPService: Сообщения не получены. Причина: Внутренняя ошибка.");
            System.err.println("========СТЭК========");
            e.printStackTrace();
            System.err.println("====конец==стека====");
        }
        if(errorStack > MAX_ERROR_STACK){
            System.err.println("[ERROR] HTTPService: getNewMessages(): Ошибка не разрешена. " +
                    "Вероятно проблема с подключением к сети. " +
                    "Перепроверьте соединение и попробуйте позже.");
            System.err.println("[ERROR] HTTPService: getNewMessages(): Список сообщений возвращает пустой список.");
            errorStack = 0;
            return messages;
        }
        System.err.println("[WARN] HTTPService: getNewMessages(): Рекурсивный перезапуск получения сообщений...");
        errorStack++;
        System.err.println("[WARN] HTTPService: getNewMessages(): Необходима перепроверка на то что сообщения есть...");
        System.err.println("[WARN] HTTPService: getNewMessages(): Если после этого сообщения нет сообщения об ошибке, она была успешно разрешена.");
        if(haveNewMessages())
            messages = getNewMessages();
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
            System.out.println("[INFO] HTTPService: Сообщение успешно отправлено.");
        } catch (Exception e) {
            System.err.println("[UNCRITICAL ERROR] HTTPService: Сообщение не отправлено. Причина: Внутренняя ошибка.");
            System.err.println("========СТЭК========");
            e.printStackTrace();
            System.err.println("====конец==стека====");
        }
        if(errorStack > MAX_ERROR_STACK){
            System.err.println("[ERROR] HTTPService: SendMsg(Message): Ошибка не разрешена. " +
                    "Вероятно проблема с подключением к сети. " +
                    "Перепроверьте соединение и попробуйте позже.");
            System.err.println("[ERROR] HTTPService: SendMsg(Message): Сообщение не отправлено.");
            errorStack = 0;
            return;
        }
        System.err.println("[WARN] HTTPService: SendMsg(Message): Рекурсивный перезапуск отправки сообщения...");
        errorStack++;
        System.err.println("[WARN] HTTPService: SendMsg(Message): Если после этого сообщения нет сообщения об ошибке, она была успешно разрешена.");
        SendMsg(msg);
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
                throw new Exception("[UNCRITICAL ERROR] HTTPService: Сервер возвратил неожиданный ответ: \""+res+"\"");
            }

        }catch (Exception e) {
            System.err.println("[UNCRITICAL ERROR] HTTPService: haveNewMessages(): Проверка завершилась неудачно. Причина: Внутренняя ошибка.");
            System.err.println("========СТЭК========");
            e.printStackTrace();
            System.err.println("====конец==стека====");
        }
        if(errorStack > MAX_ERROR_STACK){
            System.err.println("[ERROR] HTTPService: haveNewMessages(): Ошибка не разрешена. " +
                    "Вероятно проблема с подключением к сети. " +
                    "Перепроверьте соединение и попробуйте позже.");
            System.err.println("[ERROR] HTTPService: haveNewMessages(): Возвращаем false.");
            errorStack = 0;
            return false;
        }
        System.err.println("[WARN] HTTPService: haveNewMessages(): Рекурсивный перезапуск проверки...");
        errorStack++;
        System.err.println("[WARN] HTTPService: haveNewMessages(): Если после этого сообщения нет сообщения об ошибке, она была успешно разрешена.");

        return haveNewMessages();
    }
}
