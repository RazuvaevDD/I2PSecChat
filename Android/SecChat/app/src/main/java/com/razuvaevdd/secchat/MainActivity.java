package com.razuvaevdd.secchat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.razuvaevdd.I2PConnector.I2PConnector;
import com.razuvaevdd.I2PConnector.TypeOfConnection;
import com.razuvaevdd.Objects.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text1);

        //нужно для инета, пока не удалять///
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //---------------------------///

            new I2PConnector(TypeOfConnection.I2PConnection);                // Можно юзать так
            //I2PConnector.setConnectionType(TypeOfConnection.HTTPConnection); // HTTP тут пока не работает, разбираюсь

            I2PConnector.sendMessage(new Message(
                    I2PConnector.getMyAccount(),    // отправитель
                    I2PConnector.getMyAccount(),    // получатель
                    "testMsgFromClient",    // сообщение
                    TypeOfMessage.StringMessage,    // тип сообщения
                    "ВКакуюКомнату"));   // хэш комнаты (для понимания в какую комнату идет сообщение,
            //                  не обязателен для использования,
            //                      НО! не может быть пустой строкой.)

            // зависаем пока нет сообщений (HTTP подвиснет максимум на 5 сек из-за таймера)
            while (!(I2PConnector.haveNewMessages())) {
                try {
                    textView.setText("[INFO] Main: Ждем пока что-то появится...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            textView.setText("[DEBUG] Main: получено сообщение: "+I2PConnector.getNewMessages().get(0).message);

        //The additional main method code comes here...
        }catch (Exception e){
            textView.setText(e.getMessage());
        }
    }
}
