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
        /////Необходимо для доступа в интернет (не трогать!)/////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        /////////////////////////////////////////////////////////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text1);

        new I2PConnector(getApplicationContext(), TypeOfConnection.HTTPConnection);

        ////////////////Пример отправки сообщения////////////////
        I2PConnector.sendMessage(new Message(
                I2PConnector.getMyAccount(),
                I2PConnector.getMyAccount(),
                "testMsgFromClient",
                TypeOfMessage.StringMessage,
                "ВКакуюКомнату"));
        /////////////////////////////////////////////////////////

        /////////////////Пример приема сообщения/////////////////
        while (!(I2PConnector.haveNewMessages())) {
            try {
                textView.setText("[INFO] Main: Ждем пока что-то появится...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String firstMessageBody = I2PConnector.getNewMessages().get(0).message;
        /////////////////////////////////////////////////////////
        textView.setText("Получено сообщение: "+firstMessageBody);
    }
}
