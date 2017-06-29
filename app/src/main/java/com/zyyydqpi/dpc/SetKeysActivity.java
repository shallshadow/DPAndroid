package com.zyyydqpi.dpc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class SetKeysActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView keyText;
    private Button nextBtn;
    private int click;
    private String[] keys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_keys);
        click = 0;
        keys = new String[3];

        keyText = (TextView) findViewById(R.id.key_txt);
        nextBtn = (Button) findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        keys[click] = keyText.getText().toString();
        click++;
        if(click == 2){
            nextBtn.setText("Finish");
        }
        if(click == 3){
            Context context = getApplicationContext();
            try {
                String key = StringUtils.join(this.keys, ParamConsts.KEY_SEPARATOR);
                FileUntil.save(context, ParamConsts.FILE_KEYS_NAME, key);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.finish();

        }
        keyText.setText("");
    }
}
