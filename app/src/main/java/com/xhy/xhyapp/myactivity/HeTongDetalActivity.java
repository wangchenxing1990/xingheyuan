package com.xhy.xhyapp.myactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xhy.xhyapp.R;

public class HeTongDetalActivity extends AppCompatActivity {

    Intent inte;
    String url,name;
    WebView webView;
    RelativeLayout re;
    TextView textname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_he_tong_detal);

        inte = getIntent();
        url = inte.getStringExtra("url");
        name = inte.getStringExtra("name");
        webView = ((WebView) findViewById(R.id.my_ht_webView1));
        textname = ((TextView) findViewById(R.id.my_ht_detal_name));
        re = ((RelativeLayout) findViewById(R.id.my_ht_detal_back));

        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (url.length()>0){
            textname.setText(name);

            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                  view.loadUrl(url);
                    return true;
                };
            });

        }
    }
}
