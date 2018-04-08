package com.example.carlos.ipaddress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import static java.lang.Math.pow;
import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity {
    private EditText oct1,oct2,oct3,oct4,mask;
    private TextView netID,broadcast,class1,netPart,hostPart,firstIP,lastIP,nNet;
    private Button go;
    private int ip1,ip2,ip3,ip4,ipmask ;
    private String S_netID,S_broadcast,S_class1,S_netPart,S_hostPart,S_firstIP,S_lastIP,S_net;
    private LinearLayout all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oct1 = findViewById(R.id.oct1);
        oct2 = findViewById(R.id.oct2);
        oct3 = findViewById(R.id.oct3);
        oct4 = findViewById(R.id.oct4);
        mask = findViewById(R.id.mask);

        netID = findViewById(R.id.netID);
        broadcast = findViewById(R.id.broadcast);
        class1 = findViewById(R.id.class1);
        netPart = findViewById(R.id.netPart);
        hostPart = findViewById(R.id.hostPart);
        firstIP = findViewById(R.id.firstIP);
        lastIP = findViewById(R.id.lastIP);

        go = findViewById(R.id.go);
        all = findViewById(R.id.all);

        go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    if(oct1.getText().toString() == "" && oct2.getText().toString()==""&& oct3.getText().toString()==""&& oct4.getText().toString()=="" && mask.getText().toString() == ""){
                        throw new Exception();
                    }else{
                        ip1 = Integer.parseInt(oct1.getText().toString());
                        ip2 = Integer.parseInt(oct2.getText().toString());
                        ip3 = Integer.parseInt(oct3.getText().toString());
                        ip4 = Integer.parseInt(oct4.getText().toString());
                        ipmask = Integer.parseInt(mask.getText().toString());

                        CalcularDatos(ip1,ip2,ip3,ip4,ipmask);

                        all.setVisibility(View.VISIBLE);
                        netID.setText(S_netID);
                        broadcast.setText(S_broadcast);
                        class1.setText(S_class1);
                        netPart.setText(S_netPart);
                        hostPart.setText(S_hostPart);
                        firstIP.setText(S_firstIP);
                        lastIP.setText(S_lastIP);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        });
    }

    private void CalcularDatos(int ip1,int ip2,int ip3, int ip4,int ipmask){
        int mod;

        if(ip1 > 0 && ip1 < 127 && ipmask >=8 && ipmask < 32 && verificarDatos()){
            S_class1 = "A";
            mod = ipmask - 8;
            if(mod == 0){
                S_netPart = round(pow(2,8))+"";
                S_hostPart = round(pow(2,24))-2+"";
            }else{
                S_netPart = round(pow(2,mod))+"";
                S_hostPart = round(pow(2,32-ipmask))-2+"";
            }
        }else if(ip1 > 127 && ip1 < 191 && ipmask >=16 && ipmask < 32 && verificarDatos()){
            S_class1 = "B";
            mod = ipmask - 16;
            if(mod == 0){
                S_netPart = round(pow(2,14))+"";
                S_hostPart = round(pow(2,16))-2+"";
            }else{
                S_netPart = round(pow(2,mod))+"";
                S_hostPart = round(pow(2,32-ipmask))-2+"";
            }
        }else if(ip1 > 191 && ip1 < 223 && ipmask >=24 && ipmask < 32 && verificarDatos()){
            S_class1 = "C";
            mod = ipmask - 24;
            if(mod == 0){
                S_netPart = round(pow(2,21))+"";
                S_hostPart = round(pow(2,8))-2+"";
            }else{
                S_netPart = round(pow(2,mod))+"";
                S_hostPart = round(pow(2,32-ipmask))-2+"";
            }
        }else if(ip1 >223 && ip1 < 239 && verificarDatos()){
            S_class1 = "Restringida D";
            S_netID="-";
            S_broadcast="-";
            S_hostPart="-";
            S_netPart="-";
            S_firstIP="-";
            S_lastIP="-";
            return;
        }else if(ip1 >239 && ip1 < 256 && verificarDatos()){
            S_class1 = "Restringida E";
            S_netID="-";
            S_broadcast="-";
            S_hostPart="-";
            S_netPart="-";
            S_firstIP="-";
            S_lastIP="-";
            return;
        }else{
            S_class1 = "-";
            S_netID="-";
            S_broadcast="-";
            S_hostPart="-";
            S_netPart="-";
            S_firstIP="-";
            S_lastIP="-";
            return;
        }

        int[] A_mask = getSubmask(ipmask);
        int[] ipParts = {ip1,ip2,ip3,ip4};
        int[] maxIP = getMaxIP(ipParts,A_mask);
        int id1,id2,id3,id4;

        id1 = ip1 & A_mask[0];
        id2 = ip2 & A_mask[1];
        id3 = ip3 & A_mask[2];
        id4 = ip4 & A_mask[3];


        S_netID = id1+"."+id2+"."+id3+"."+id4;
        S_broadcast = maxIP[0]+"."+maxIP[1]+"."+maxIP[2]+"."+maxIP[3];

        if(ipmask!=31){
            S_firstIP = id1+"."+id2+"."+id3+"."+(id4+1);
            S_lastIP = maxIP[0]+"."+maxIP[1]+"."+maxIP[2]+"."+(maxIP[3]-1);
        }else{
            S_firstIP = S_netID;
            S_lastIP = S_broadcast;
        }



    }

    private boolean verificarDatos(){
        if(ipmask < 8 || ipmask > 31){
            return false;
        }else if(this.ip1 < 0 && this.ip1 > 255){
            return false;
        }else if(this.ip2 < 0 && this.ip2 > 255){
            return false;
        }else if(this.ip3 < 0 && this.ip3 > 255){
            return false;
        }else if(this.ip4 < 0 && this.ip4 > 255){
            return false;
        }else{
            return true;
        }
    }

    private int[] getSubmask(int mask){
        int[] A_mask = {0,0,0,0};

        int nOct = (int)mask/8;
        int octParcial = mask % 8;
        int posOct = nOct;

        for(int i = 0; i<nOct;i++){
            A_mask[i]=255;
        }
        for(int i=7;i>=8-octParcial;i--){
            A_mask[nOct]=(int)(A_mask[nOct]+pow(2,i));
        }
        return A_mask;

    }
    private int[] getMaxIP(int[] partes,int[] A_mask){
        int[] maxIP = partes;
        int posVacio = (ipmask/8);
        int residuo = ipmask %8;

        if(residuo != 0) {
            int count1 = partes[posVacio];
            int count2 = partes[posVacio] & A_mask[posVacio];
            int count3 = partes[posVacio] & A_mask[posVacio];

            while (count2 == count3) {
                count1++;
                count2 = count1 & A_mask[posVacio];
            }
            maxIP[posVacio]=count1-1;
        }else{
            maxIP[posVacio]=255;
        }

        for (int i = 3; i>posVacio;i--){
            maxIP[i]=255;
        }
        return  maxIP;
    }
}
