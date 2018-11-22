package com.xhy.xhyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class XieyiActivity extends AppCompatActivity {

    private Button button1,button2;
    private int agr = 0;//0同意
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xieyi);

        button1 = (Button) findViewById(R.id.rg_xieyi_tijiao1);
        button2 = (Button) findViewById(R.id.rg_xieyi_tijiao2);


        final Intent intent = new Intent(XieyiActivity.this,RegisterShenActivity.class);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type","2");
                startActivity(intent);
                finish();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agr == 0){
                    startActivity(new Intent(XieyiActivity.this,RegisterShenActivity.class));
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agr == 1){
                    button2.setBackgroundResource(R.drawable.yuanyuan_04);
                    button1.setBackgroundResource(R.drawable.shezhi_tuichudenglu);
                    agr = 0;
                }else {
                    button2.setBackgroundResource(R.drawable.zhuceshouji_22);
                    button1.setBackgroundResource(R.drawable.noagree);
                    agr = 1;
                }

            }
        });
    }
}
