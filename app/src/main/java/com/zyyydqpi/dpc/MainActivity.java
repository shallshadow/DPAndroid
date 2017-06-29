package com.zyyydqpi.dpc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zyyydqpi.dpc.calc.DPDecorater;
import com.zyyydqpi.dpc.calc.DPOrigin;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSetKeys;
    private TextView keyText;
    private String[] keys;
    private DPDecorater decorater;
    private DPOrigin origin;
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(keys == null){
                return;
            }
            String key = decorater.getPasswd(keys);
            Message message = new Message();
            message.obj = key;
            handler.sendMessage(message);
        }
    };
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           keyText.setText(msg.obj.toString());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getkeys();

        btnSetKeys = (Button) findViewById(R.id.btn_setkeys);
        btnSetKeys.setOnClickListener(this);

        keyText = (TextView) findViewById(R.id.key_txt_view);
        origin = new DPOrigin();
        decorater = new DPDecorater(origin);
        timer.schedule(task, 1000, 1000 * 15);
    }

    private void getkeys(){
        try {
            String json = FileUntil.get(getApplicationContext(), ParamConsts.FILE_KEYS_NAME);
            if(json == null){
                return;
            }
            keys = json.split(ParamConsts.KEY_SEPARATOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getkeys();
        if(keys == null){
            return;
        }
        String key = decorater.getPasswd(keys);
        keyText.setText(key);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SetKeysActivity.class);
        MainActivity.this.startActivity(intent);
    }
}
