package com.xhy.xhyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_cancel;
    private GridView gridview;
    private EditText editText;
    private TextView homesearch_txt;
    String sousuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        txt_cancel = ((TextView) findViewById(R.id.txt_cancel));
        editText = ((EditText) findViewById(R.id.editText));
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(editText, 0);
        homesearch_txt = ((TextView) findViewById(R.id.homesearch_txt));
        homesearch_txt.setOnClickListener(this);
        editText.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                finish();
                break;
            case R.id.homesearch_txt:
                sousuo = editText.getText().toString();
                if (TextUtils.isEmpty(sousuo)) {
                    Toast.makeText(HomeSearchActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(HomeSearchActivity.this, SearchActivity.class);
                    intent.putExtra("name", sousuo);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
