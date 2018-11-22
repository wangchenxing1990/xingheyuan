package com.xhy.xhyapp.myfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.CommonProblemAdapter;
import com.xhy.xhyapp.myactivity.Servicecenter;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CommonProblemFragment extends Fragment implements View.OnClickListener {

    public Servicecenter servicecenter = null;
    public String changjianwenti = new String();
    public ImageView iv_dadianhua;
    public ImageView iv_shop_back;
    private ListView listview_commonproblem;
    private List<String> images = new ArrayList();
    public List<Servicecenter> list = new ArrayList<>();

    public CommonProblemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_commonproblem, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview_commonproblem = ((ListView) view.findViewById(R.id.listView_commonproblem));
        iv_shop_back = (ImageView) view.findViewById(R.id.iv_shop_back);
        iv_dadianhua = (ImageView) view.findViewById(R.id.iv_dadianhua);

        iv_dadianhua.setOnClickListener(this);
        iv_shop_back.setOnClickListener(this);

        //数据加载

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/other/GetServerCenterArticle?merchantId=1&pageSize=20&pageIndex=1", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //Toast.makeText(getActivity(),responseInfo.result,Toast.LENGTH_SHORT).show();
                //String resultq = responseInfo.result;

                try {
                    JSONObject jsonObject = new JSONObject(responseInfo.result);
                    JSONArray jsonArray = jsonObject.getJSONArray("articleList");
                    //System.out.print("========"+jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        servicecenter = new Servicecenter();
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        // System.out.print("========"+jsonObject1+"+++++++++");
                        servicecenter.setArticleTitle(jsonObject1.getString("articleTitle"));
                        servicecenter.setUrl(jsonObject1.getString("url"));
                        list.add(servicecenter);
                    }
                    //Toast.makeText(getActivity(),list.toString(),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                CommonProblemAdapter commonProblemAdapter = new CommonProblemAdapter(getActivity(), list);
                listview_commonproblem.setAdapter(commonProblemAdapter);

                listview_commonproblem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        FindOrderProblemsFragment findOrderProblemsFragment = new FindOrderProblemsFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("key", list.get(i).getUrl());
                        findOrderProblemsFragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.my_fuwuzhongxin_frameLayout, findOrderProblemsFragment).commit();
                    }


//                FindOrderProblemsFragment findOrderProblemsFragment=new FindOrderProblemsFragment();
//                //FragmentTransaction ft=getActivity().getFragmentManager().beginTransaction();
//                FragmentManager fm = findOrderProblemsFragment.getFragmentManager();
//                Log.e("世界真的那么大吗？","世界那么大来一场说走就走的旅行");
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.my_fuwuzhongxin_frameLayout,findOrderProblemsFragment);
//                ft.commit();


                });


//                JSONArray jsonObjs = new JSONObject().getJSONArray("articleList");
//                String s = "";
//                for(int i = 0; i < jsonObjs.length() ; i++){
//                    JSONObject jsonObj = (JSONObject)jsonObjs.get(i);
//                    int id = jsonObj.getInt("id");
//                    String name = jsonObj.getString("name");
//                    String gender = jsonObj.getString("gender");
//                    s +=  "ID号"+id + ", 姓名：" + name + ",性别：" + gender+ "\n" ;
//                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("--------------------错误");
                Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
            }
        });

        //CompleteAdapter completeAdapter=new CompleteAdapter(getActivity(),images);
//        listview_commonproblem.setAdapter(commonProblemAdapter);
//        initData();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_shop_back:
                getActivity().finish();
                //getActivity().getSupportFragmentManager().beginTransaction().remove(commonProblemFragment);
                break;


            case R.id.iv_dadianhua:

                //定义执行打电话的意图对象
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15311166621"));

                //执行意图
                startActivity(intent);

                //吐司的效果
                Toast.makeText(getActivity(), "正在给" + "" + "打电话", Toast.LENGTH_LONG).show();


                break;


        }


    }
}
