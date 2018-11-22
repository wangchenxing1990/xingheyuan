package com.xhy.xhyapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.CategoryList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Fragment_shangpin_two extends Fragment{

    private Button sure;
    private ListView listView;
    Handler handler;
    OnButtonClick onButtonClick;
    TextView quanxuan;
    List<CategoryList> list,lists;
    String pathitem,itemcatch,caId;
    String[] s ;
    String Tpath,Thead,Titem,Texit;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shangpin_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = ((ListView) view.findViewById(R.id.fragment_shangpin_two_list));

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        ShangpinTwAdapter adapter = new ShangpinTwAdapter();
                        listView.setAdapter(adapter);
                        break;
                    case 1:
                        onButtonClick.onClick(sure);
                        break;
                    default:
                        break;
                }
            }
        };

        quanxuan = ((TextView) getActivity().findViewById(R.id.iv_shop_quanxuan));
        quanxuan.setTextColor(0xff49C49E);
        list = new ArrayList<>();
        lists = new ArrayList<>();

        if (getArguments()!=null){
            pathitem = getArguments().getString("pathitem");
            itemcatch = getArguments().getString("itemcatch");
            caId = getArguments().getString("caId");
            s = itemcatch.split(",");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpUtils httpUtils = new HttpUtils();
                    RequestParams requestParams = new RequestParams();
                    httpUtils.configCurrentHttpCacheExpiry(0);
                    requestParams.addQueryStringParameter("merchantId", "1");
                    requestParams.addQueryStringParameter("goodsCactoryId", caId);
                    requestParams.addQueryStringParameter("goodsName", "");
                    requestParams.addQueryStringParameter("pageSize", "2");
                    requestParams.addQueryStringParameter("pageIndex", "1");
                    requestParams.addQueryStringParameter("isRelease", "1  ");
                    httpUtils.send(HttpRequest.HttpMethod.POST,pathitem, requestParams, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String json = responseInfo.result;
                            JSONObject jsonObject = JSON.parseObject(json);
                            JSONArray jsonArray = jsonObject.getJSONArray("goodsList");
                            for (int i = 0; i < s.length; i++) {

                                CategoryList categoryList = new CategoryList();
                                JSONObject jsonObject1 = JSON.parseObject(String.valueOf(jsonArray.get(Integer.parseInt(s[i]))));
                                categoryList.setGoodsName(jsonObject1.getString("goodsName"));
                                categoryList.setThumbnailImg(jsonObject1.getString("thumbnailImg"));
                                categoryList.setSumStock(jsonObject1.getString("sumStock"));
                                categoryList.setMinNum(jsonObject1.getString("minNum"));
                                categoryList.setMinPrice(jsonObject1.getString("minPrice"));
                                categoryList.setGoodsId(jsonObject1.getString("goodsId"));
                                categoryList.setCollectionId(jsonObject1.getString("CollectionId"));
                                System.out.println("----"+jsonObject1.getString("goodsName")+"----"+jsonObject1.getString("minPrice")+"----"+jsonObject1.getString("sumStock")+"----"+jsonObject1.getString("goodsId"));
                                list.add(categoryList);
                                lists.add(categoryList);
                            }
                            Message me = new Message();
                            me.what = 0;
                            handler.sendMessage(me);
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(getContext(), "第二次请求数据失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        }

        sure = ((Button) view.findViewById(R.id.shangpin_two_sure));
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onButtonClick != null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Thead = "http://139.196.234.104:8000/appapi/Merchant/MerchantReleaseGoods?merchantId=1&json=[";
                            Texit = "]";
                            for (int x=0;x<list.size();x++){
                                if (list.size()==1){
                                    Titem = "{\"goodsId\":\""+list.get(x).getGoodsId()+"\",\"price\":\""+list.get(x).getMinPrice()+"\",\"stock\":\""+list.get(x).getSumStock()+"\"}";
                                }else {
                                    if (0==x){
                                        Titem = "{\"goodsId\":\""+list.get(x).getGoodsId()+"\",\"price\":\""+list.get(x).getMinPrice()+"\",\"stock\":\""+list.get(x).getSumStock()+"\"},";
                                    }else if ((list.size()-1)==x){
                                        Titem = Titem+"{\"goodsId\":\""+list.get(x).getGoodsId()+"\",\"price\":\""+list.get(x).getMinPrice()+"\",\"stock\":\""+list.get(x).getSumStock()+"\"}";
                                    }else {
                                        Titem = Titem+"{\"goodsId\":\""+list.get(x).getGoodsId()+"\",\"price\":\""+list.get(x).getMinPrice()+"\",\"stock\":\""+list.get(x).getSumStock()+"\"},";
                                    }
                                }
                            }
                            HttpUtils httpUtils = new HttpUtils();
                            RequestParams requestParams = new RequestParams();
                            httpUtils.configCurrentHttpCacheExpiry(0);
                            requestParams.addQueryStringParameter("merchantId", "1");
                            requestParams.addQueryStringParameter("json", "["+Titem+"]");
                            httpUtils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Merchant/MerchantReleaseGoods?",requestParams, new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String result = responseInfo.result;
                                    if (result.length()>0){
                                        JSONObject jsonObject = JSON.parseObject(result);
                                        if ("0".equals(jsonObject.getString("state"))){
                                            Toast.makeText(getContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                            Message message = new Message();
                                            message.what = 1;
                                            handler.sendMessage(message);
                                        }else {
                                            Toast.makeText(getContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {
                                    Toast.makeText(getContext(),"发布新商品失败！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }


    //button回调接口
    public interface OnButtonClick{
        public void onClick(View view);
    }
    public OnButtonClick getOnButtonClick(){
        return onButtonClick;
    }
    public void setOnButtonClick(OnButtonClick onButtonClick){
        this.onButtonClick = onButtonClick;
    }

    private class ShangpinTwAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null){
                viewHolder = new ViewHolder();
                view = View.inflate(getContext(), R.layout.fragment_shangpinguanli_two_item,null);
                viewHolder.imageView = ((ImageView) view.findViewById(R.id.shangpin_twoitem_image));
                viewHolder.name = ((TextView) view.findViewById(R.id.shangpin_twoitem_name));
                viewHolder.pplus = ((TextView) view.findViewById(R.id.fragment_shangpin_two_pplus));
                viewHolder.pjian = ((TextView) view.findViewById(R.id.fragment_shangpin_two_pjian));
                viewHolder.tpuls = ((TextView) view.findViewById(R.id.fragment_shangpin_two_tplus));
                viewHolder.tjian= ((TextView) view.findViewById(R.id.fragment_shangpin_two_tjian));
                viewHolder.t = ((EditText) view.findViewById(R.id.fragment_shangpin_two_t));
                viewHolder.p = ((EditText) view.findViewById(R.id.fragment_shangpin_two_p));
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)view.getTag();
            }
            final ViewHolder vvv = viewHolder;

            BitmapUtils bitmapUtils = new BitmapUtils(getContext());
            bitmapUtils.display(vvv.imageView, list.get(i).getThumbnailImg());
            vvv.name.setText(list.get(i).getGoodsName());
            vvv.t.setText(list.get(i).getSumStock());
            vvv.p.setText(list.get(i).getMinPrice());

            vvv.p.addTextChangedListener(new EditTextWatch(i){
                @Override
                public void afterTextChanged(Editable editable) {
                    String pri = vvv.p.getText().toString().trim();
                    CategoryList ccc = list.get(s);
                    ccc.setMinPrice(pri);
                    System.out.println("----aaaa--------"+list.size());
                    for (int h=0;h<list.size();h++){
                        lists.remove(0);
                        if (h==s){
                            lists.add(ccc);
                            System.out.println("--------h-"+ccc.getGoodsName());
                        }else {
                            System.out.println("--------h-"+list.get(h).getGoodsName());
                            lists.add(list.get(h));
                        }
                    }
                    for (int j=0;j<lists.size();j++){
                        list.remove(0);
                        list.add(lists.get(j));
                    }
                }
            });
            vvv.t.addTextChangedListener(new EditTextWatch(i){
                @Override
                public void afterTextChanged(Editable editable) {
                    String kg = vvv.t.getText().toString().trim();
                    CategoryList ccc = list.get(s);
                    ccc.setSumStock(kg);
                    for (int h=0;h<list.size();h++){
                        lists.remove(0);
                        if (h==s){
                            lists.add(ccc);
                        }else {
                            lists.add(list.get(h));
                        }
                    }
                    for (int j=0;j<lists.size();j++){
                        list.remove(0);
                        list.add(lists.get(j));
                    }
                }
            });
            viewHolder.pplus.setOnClickListener(new TextViewOnclick(i){
                @Override
                public void onClick(View view) {
                    String s = vvv.p.getText().toString().trim();
                    if (s.length()>0){
                        vvv.p.setText((Double.parseDouble(s)+1)+"");
                        CategoryList ccc = list.get(i);
                        ccc.setMinPrice((Double.parseDouble(s)+1)+"");
                        for (int h=0;h<list.size();h++){
                            lists.remove(0);
                            if (h==i){
                                lists.add(ccc);
                            }else {
                                lists.add(list.get(h));
                            }
                        }
                        for (int j=0;j<lists.size();j++){
                            list.remove(0);
                            list.add(lists.get(j));
                        }
                        //             list.get(i).setMinPrice((Double.parseDouble(s)+1)+"");
                        notifyDataSetChanged();
//                        vvv.p.setSelection(s.length(),s.length());
                    }else {
                        vvv.p.setText(0+"");
                        CategoryList ccc = list.get(i);
                        ccc.setMinPrice(0+"");
                        for (int h=0;h<list.size();h++){
                            lists.remove(0);
                            if (h==i){
                                lists.add(ccc);
                            }else {
                                lists.add(list.get(h));
                            }
                        }
                        for (int j=0;j<lists.size();j++){
                            list.remove(0);
                            list.add(lists.get(j));
                        }
                        notifyDataSetChanged();
//                        vvv.p.setSelection(s.length(),s.length());
                    }

                }
            });
            viewHolder.pjian.setOnClickListener(new TextViewOnclick(i){
                @Override
                public void onClick(View view) {
                    String s = vvv.p.getText().toString().trim();
                    if (Double.parseDouble(s)-1>0 && s.length()>0){
                        vvv.p.setText(Double.parseDouble(s)-1+"");
                        CategoryList ccc = list.get(i);
                        ccc.setMinPrice((Double.parseDouble(s)-1)+"");
                        for (int h=0;h<list.size();h++){
                            lists.remove(0);
                            if (h==i){
                                lists.add(ccc);
                            }else {
                                lists.add(list.get(h));
                            }
                        }
                        for (int j=0;j<lists.size();j++){
                            list.remove(0);
                            list.add(lists.get(j));
                        }
                        notifyDataSetChanged();
//                        vvv.p.setSelection(s.length(),s.length());
                    }else {
                        vvv.p.setText(0+"");
                        CategoryList ccc = list.get(i);
                        ccc.setMinPrice(0+"");
                        for (int h=0;h<list.size();h++){
                            lists.remove(0);
                            if (h==i){
                                lists.add(ccc);
                            }else {
                                lists.add(list.get(h));
                            }
                        }
                        for (int j=0;j<lists.size();j++){
                            list.remove(0);
                            list.add(lists.get(j));
                        }
                        notifyDataSetChanged();
//                        vvv.p.setSelection(s.length(),s.length());
                    }
                }
            });
            viewHolder.tpuls.setOnClickListener(new TextViewOnclick(i){
                @Override
                public void onClick(View view) {
                    String s = vvv.t.getText().toString().trim();
                    if (s.length()>0){
                        vvv.t.setText(Double.parseDouble(s)+1+"");
                        CategoryList ccc = list.get(i);
                        ccc.setSumStock((Double.parseDouble(s)+1)+"");
                        lists.clear();
                        for (int h=0;h<list.size();h++){
                            if (h==i){
                                lists.add(ccc);
                            }else {
                                lists.add(list.get(h));
                            }
                        }
                        list.clear();
                        for (int j=0;j<lists.size();j++){
                            list.add(lists.get(j));
                        }
                        notifyDataSetChanged();
//                        vvv.t.setSelection(s.length(),s.length());
                    }else {
                        vvv.t.setText(0+"");
                        CategoryList ccc = list.get(i);
                        ccc.setSumStock(0+"");
                        lists.clear();
                        for (int h=0;h<list.size();h++){
                            if (h==i){
                                lists.add(ccc);
                            }else {
                                lists.add(list.get(h));
                            }
                        }
                        list.clear();
                        for (int j=0;j<lists.size();j++){
                            list.add(lists.get(j));
                        }
                        notifyDataSetChanged();
//                        vvv.t.setSelection(s.length(),s.length());
                    }
                }
            });
            viewHolder.tjian.setOnClickListener(new TextViewOnclick(i){
                @Override
                public void onClick(View view) {
                    String kgs = vvv.t.getText().toString().trim();
                    if (Double.parseDouble(kgs)-1>0 && kgs.length()>0){
                        vvv.t.setText(Double.parseDouble(kgs)-1+"");
                        CategoryList ccc = list.get(i);
                        ccc.setSumStock((Double.parseDouble(kgs)-1)+"");
                        lists.clear();
                        for (int h=0;h<list.size();h++){
                            if (h==i){
                                lists.add(ccc);
                            }else {
                                lists.add(list.get(h));
                            }
                        }
                        list.clear();
                        for (int j=0;j<lists.size();j++){
                            list.add(lists.get(j));
                        }
                        notifyDataSetChanged();
//                        vvv.t.setSelection(s.length(),s.length());
                    }else {
                        vvv.t.setText(0+"");
                        CategoryList ccc = list.get(i);
                        ccc.setSumStock(0+"");
                        lists.clear();
                        for (int h=0;h<list.size();h++){
                            if (h==i){
                                lists.add(ccc);
                            }else {
                                lists.add(list.get(h));
                            }
                        }
                        list.clear();
                        for (int j=0;j<lists.size();j++){
                            list.add(lists.get(j));
                        }
                        notifyDataSetChanged();
//                        vvv.t.setSelection(s.length(),s.length());
                    }
                }
            });

            return view;
        }

        class TextViewOnclick implements View.OnClickListener {
            int i;

            public TextViewOnclick(int i) {
                this.i = i;
            }

            @Override
            public void onClick(View view) {

            }
        }


        class EditTextWatch implements TextWatcher{
            int s;

            public EditTextWatch(int s) {
                this.s = s;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        }

        private class ViewHolder {
            TextView tpuls,tjian,pplus,pjian,name;
            EditText t,p;
            ImageView imageView;

        }
    }
}