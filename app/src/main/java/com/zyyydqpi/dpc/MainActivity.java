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
import com.zyyydqpi.dpc.calc.DPMD5;
import com.zyyydqpi.dpc.calc.IDynamicPasswd;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSetKeys;
    private TextView keyText;
    private TextView timeText;
    private String[] keys;
    private DPDecorater decorater;
    private IDynamicPasswd origin;
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(keys == null){
                return;
            }
            Message message = new Message();
            long date = new Date().getTime();
            date = date / 1000L / 30L * 30L;
            String key = decorater.getPasswd(keys, date);
            Bundle bundle = new Bundle();
            bundle.putLong("time", date);
            bundle.putString("key", key);
            message.setData(bundle);
            updateTimeHandler.sendMessage(message);

        }
    };

    Handler updateTimeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            long ti = bundle.getLong("time");
            String key = bundle.getString("key");
            ti = 30L - ti % 30L;
            String showTime = "Update the key in " + ti + " second";
            timeText.setText(showTime);
            String showKey = "Key : " + key;
            keyText.setText(showKey);
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
        timeText = (TextView) findViewById(R.id.time_txt_view);
        origin = new DPMD5();
        decorater = new DPDecorater(origin);
        timer.schedule(task, 0, 1000);
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
