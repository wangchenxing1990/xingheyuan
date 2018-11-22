package com.xhy.xhyapp.myfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhy.xhyapp.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Administrator on 2016/8/4.
 */
public class FindOrderProblemsFragment extends Fragment implements View.OnClickListener {

    // FindOrderProblemsFragment findOrderProblemsFragment=new FindOrderProblemsFragment();
    public ImageView iv_shenqing_back;
    public String string;
    public  TextView texta;
    public WebView webView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        string = getArguments().getString("key");
        return inflater.inflate(R.layout.fragment_findorderproblem, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_shenqing_back = (ImageView) view.findViewById(R.id.iv_shenqing_back);
        iv_shenqing_back.setOnClickListener(this);

        webView=(WebView) view.findViewById(R.id.webview);
        webView.loadUrl(string);
        //设置可自由缩放网页
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);

        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_shenqing_back:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.my_fuwuzhongxin_frameLayout,new CommonProblemFragment()).commit();
                break;
        }
    }
}

