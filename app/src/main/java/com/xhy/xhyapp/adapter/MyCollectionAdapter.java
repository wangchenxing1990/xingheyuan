package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.CollectionBean;
import com.xhy.xhyapp.content.ContentUrl;
import com.xhy.xhyapp.view.SwipeLayoutConllection;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class MyCollectionAdapter extends BaseAdapter {

    private final Context context;
    private final List<CollectionBean> collectionData;
    private SwipeLayoutConllection preLayout;

    public MyCollectionAdapter(Context context, List<CollectionBean> collectionData){
        this.context=context;
        this.collectionData=collectionData;
    }
    @Override
    public int getCount() {
        return collectionData.size();
    }

    @Override
    public Object getItem(int i) {
        return collectionData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.lv_collect_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_name.setText(collectionData.get(i).getGoodsName());
        holder.tv_weight.setText(collectionData.get(i).getStock());
        BitmapUtils bitmapUtils=new BitmapUtils(context);
        bitmapUtils.display(holder.fruit_image,collectionData.get(i).getThumbnailImg());

        holder.lin_item_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deteleCollectionProduct(i);
            }

        });

        holder.swipelayout.setOnSwipeStatuChangeListener(new SwipeLayoutConllection.OnSwipeStatuChangeListener() {
            @Override
            public void onOpen(SwipeLayoutConllection layout) {
                preLayout=  layout;
            }

            @Override
            public void onClose(SwipeLayoutConllection layout) {

            }

            @Override
            public void onSwiping(SwipeLayoutConllection layout) {

            }

            @Override
            public void onStartOpen(SwipeLayoutConllection layout) {
                //  当前打开 关闭上一个
                if (preLayout != null){
                    preLayout.close();
                }
            }

            @Override
            public void onStartClose(SwipeLayoutConllection layout) {

            }
        });

        return view;
    }

    public class ViewHolder {
        public View rootView;
        public LinearLayout lin_item_right;
        public TextView tv_name;
        public TextView  tv_weight;
        public SwipeLayoutConllection swipelayout;
        public ImageView fruit_image;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lin_item_right= (LinearLayout) rootView.findViewById(R.id.lin_item_right);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_fruit_name);
            this.tv_weight = (TextView) rootView.findViewById(R.id.tv_fruit_weight);
            this.fruit_image=(ImageView)rootView.findViewById(R.id.iv_fruit_image);
            this.swipelayout = (SwipeLayoutConllection) rootView.findViewById(R.id.swipelayout);
        }
    }

    /**
     * 删除收藏的商品
     */
    private void deteleCollectionProduct(final int i) {
        HttpUtils httpCancel = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addQueryStringParameter("MerchantId", "1");
        requestParams.addQueryStringParameter("GoodsId", collectionData.get(i).getGoodsId());
        System.out.print("取消收藏的下标看见是你的疯狂邻居" + collectionData.get(i).getGoodsId());
        httpCancel.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.CANCEL_COLLECTION_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String json = responseInfo.result;
                System.out.print(json);
                Toast.makeText(context, "已取消收藏", Toast.LENGTH_SHORT).show();
                collectionData.remove(i);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException error, String msg) {

                System.out.print(error);
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();

            }
        });


    }
}



