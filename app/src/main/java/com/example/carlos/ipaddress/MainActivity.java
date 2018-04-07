package com.example.carlos.ipaddress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText oct1,oct2,oct3,oct4,mask;
    private TextView netID,broadcast,class1,netPart,hostPart,firstIP,lastIP;
    private Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oct1 = findViewById(R.id.oct1);
        oct2 = findViewById(R.id.oct2);
        oct3 = findViewById(R.id.oct3);
        oct4 = findViewById(R.id.oct4);

        netID = findViewById(R.id.netID);
        broadcast = findViewById(R.id.broadcast);
        class1 = findViewById(R.id.class1);
        netPart = findViewById(R.id.netPart);
        hostPart = findViewById(R.id.hostPart);
        firstIP = findViewById(R.id.firstIP);
        lastIP = findViewById(R.id.lastIP);

        go = findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                
            }
        });
    }
}
