package com.xhy.xhyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterShenActivity extends AppCompatActivity {

    private Button button1;
    private TextView textView,textView2;
    Intent intent;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shen);

        textView = ((TextView) findViewById(R.id.rg_shen_account));
        textView2 = ((TextView) findViewById(R.id.rg_shen_reson));

        intent = getIntent();
        type = intent.getStringExtra("type");
        if ("1".equals(type)){
            String reson = intent.getStringExtra("reson");
            if ("审核未通过".equals(reson)){
                String result = intent.getStringExtra("result");
                textView2.setText(reson+"\n\n"+result);
            }else {
                textView2.setText(reson);
            }
        }else {
            textView2.setText("正在审核中 请等待...");
        }

        button1 = ((Button) findViewById(R.id.rg_shen_back));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterShenActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}
