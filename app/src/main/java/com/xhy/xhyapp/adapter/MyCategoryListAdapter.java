package com.xhy.xhyapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.CategoryList;
import com.xhy.xhyapp.content.ContentUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MyCategoryListAdapter extends BaseAdapter {
    private final Context context;
    private final List<CategoryList> cateListData;
    private Boolean flag = true;

    public Boolean getIsCheck() {
        return isCheck;
    }

    Boolean isCheck;

    private HashMap<Integer, Boolean> hashMap = new HashMap<Integer, Boolean>();
    private List<HashMap<Integer, Boolean>> list = new ArrayList<HashMap<Integer, Boolean>>();
    //private HashSet<Integer> hashSet=new HashSet();

    public MyCategoryListAdapter(Context context, List<CategoryList> cateListData) {
        this.context = context;
        this.cateListData = cateListData;

       for (int i = 0; i < cateListData.size(); i++) {
        if ("0".equals(cateListData.get(i).getCollectionId())) {
            hashMap.put(i, false);
        } else {
            hashMap.put(i, true);
        }
        list.add(hashMap);
      }

    }

    public void setIsCheck(HashMap<Integer, Boolean> hashMap) {

    }


    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public int getCount() {
        return cateListData.size();
    }

    @Override
    public Object getItem(int i) {
        return cateListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.list_activity, null);

            holder.iv_inport = (ImageView) view.findViewById(R.id.iv_inport);
            holder.tv_inport = (TextView) view.findViewById(R.id.tv_inport);
            holder.rl_image_start = (RelativeLayout) view.findViewById(R.id.rl_image_start);
            holder.checkbox_inport_start = (CheckBox) view.findViewById(R.id.checkbox_inport_start);
            // holder.checkbox_inport_start = (CheckBox) view.findViewById(R.id.checkbox_inport_start);
            holder.tv_inport_weight = (TextView) view.findViewById(R.id.tv_inport_weight);
            holder.tv_inport_stock = (TextView) view.findViewById(R.id.tv_inport_stock);
            holder.tv_inport_price = (TextView) view.findViewById(R.id.tv_inport_price);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.checkbox_inport_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(i).get(i)) {
                    hashMap.put(i, false);
                    list.add(hashMap);
                    cancelConllection(i);
                } else {
                    hashMap.put(i, true);
                    list.add(hashMap);
                    addConllection(i);
                }
            }
        });

        if (list.get(i).get(i)) {
            holder.checkbox_inport_start.setChecked(true);

        } else {
            holder.checkbox_inport_start.setChecked(false);

        }

        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(holder.iv_inport, cateListData.get(i).getThumbnailImg());
        holder.tv_inport.setText(cateListData.get(i).getGoodsName());
        holder.tv_inport_weight.setText(cateListData.get(i).getMinNum() + "件起批");
        holder.tv_inport_stock.setText("库存" + cateListData.get(i).getSumStock() + "吨");
        holder.tv_inport_price.setText("￥" + cateListData.get(i).getMinPrice());

        return view;
    }


//    public void setIsCheck(boolean isCheck) {
//        this.isCheck = isCheck;
//    }

    class ViewHolder {

        public ImageView iv_inport;
        public CheckBox checkbox_inport_start;
        public TextView tv_inport, tv_inport_weight, tv_inport_stock, tv_inport_price;
        private RelativeLayout rl_image_start;
    }


    private class ImageOclick implements View.OnClickListener {
        int i;

        public ImageOclick(int i) {
            this.i = i;
        }

        @Override
        public void onClick(View view) {


        }
    }

    /**
     * 添加收藏的商品
     *
     * @param
     */
    private void addConllection(final int i) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtils httpAdd = new HttpUtils();
                RequestParams requestParams = new RequestParams();
                requestParams.addQueryStringParameter("MerchantId", "1");
                requestParams.addQueryStringParameter("GoodsId", cateListData.get(i).getGoodsId());

                httpAdd.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.ADD_COLLECTION_URL, requestParams, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        String json = responseInfo.result;
                        System.out.print(json);
                        Toast.makeText(context, "已添加收藏", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        System.out.print(msg);
                        Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }) {
        }.start();
    }

    /**
     * 取消收藏商品
     */
    private void cancelConllection(final int i) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtils httpCancel = new HttpUtils();
                RequestParams requestParams = new RequestParams();
                requestParams.addQueryStringParameter("MerchantId", "1");
                requestParams.addQueryStringParameter("GoodsId", cateListData.get(i).getGoodsId());
                System.out.print("取消收藏的下标看见是你的疯狂邻居" + cateListData.get(i).getGoodsId());
                httpCancel.send(HttpRequest.HttpMethod.POST, ContentUrl.BASE_URL + ContentUrl.CANCEL_COLLECTION_URL, requestParams, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        String json = responseInfo.result;
                        System.out.print(json);
                        Toast.makeText(context, "已取消收藏", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        System.out.print(error);
                        Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }) {
        }.start();
    }
}