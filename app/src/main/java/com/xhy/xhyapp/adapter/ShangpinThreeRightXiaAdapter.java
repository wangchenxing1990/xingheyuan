package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.ShangpinBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class ShangpinThreeRightXiaAdapter extends BaseAdapter{

    Context context;
    List<ShangpinBean> list;
    String pathShang="";
    String merchantId = "1";
    private Handler handler;

    public ShangpinThreeRightXiaAdapter(Context context, List<ShangpinBean> list,Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null ;
        if (view==null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.fragment_shangpin_three_right_xiajia,null);
            viewHolder.buttonSh = ((Button) view.findViewById(R.id.fragment_shangpin_three_Shang));
            viewHolder.xianame = ((TextView) view.findViewById(R.id.fragment_shangpin_three_XiName));
            viewHolder.image2 = ((ImageView) view.findViewById(R.id.fragment_shangpin_three_image2));
            view.setTag(viewHolder);
        }else {
            viewHolder = ((ViewHolder) view.getTag());
        }
        viewHolder.buttonSh.setOnClickListener(new ButtonSh(i));
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(viewHolder.image2, list.get(i).getThumbnailImg());
        viewHolder.xianame.setText(list.get(i).getGoodsName());
        return view;
    }

    class ButtonSh implements View.OnClickListener{
        private int a;

        public ButtonSh(int a) {
            this.a = a;
        }

        @Override
        public void onClick(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpUtils httpUtils = new HttpUtils();
                    pathShang = "http://139.196.234.104:8000/appapi/Goods/EditMerchantGoodsOn?merchantId="+merchantId+"&goodsId="+list.get(a).getGoodsId()+"&merchantGoodsId="+list.get(a).getMerchantGoodsId();
                    httpUtils.send(HttpRequest.HttpMethod.GET, pathShang, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                            System.out.println("--------------x------s------"+result);
                            try {
                                JSONObject json = new JSONObject(result);
                                if ("0".equals(json.getString("state"))){
                                    Toast.makeText(context,json.getString("msg"),Toast.LENGTH_SHORT).show();
                                    Message me = new Message();
                                    me.what = 2;
                                    me.arg1 = a;
                                    handler.sendMessage(me);
                                }else {
                                    Toast.makeText(context,json.getString("msg"),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(context,"上架失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        }
    }

    class ViewHolder{
        Button buttonSh;
        ImageView image2;
        TextView xianame;
    }
}

