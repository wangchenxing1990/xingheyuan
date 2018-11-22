package com.xhy.xhyapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.bean.AddressBean;
import com.xhy.xhyapp.myactivity.EditAddActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class ShippingAddressManagementAdapter extends BaseAdapter {
    private List<AddressBean> list;
    private Context context;
    private int sign;

    public ShippingAddressManagementAdapter(Context context, List<AddressBean> list) {
        this.list = list;
        this.context = context;
    }

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.addaddress_item, null);
            holder.txt_name = ((TextView) view.findViewById(R.id.textView_name));
            holder.relativeLayout8 = ((RelativeLayout) view.findViewById(R.id.relativeLayout8));
            holder.relative_shanchu = ((RelativeLayout) view.findViewById(R.id.relative_shanchu));
            holder.txt_phone = ((TextView) view.findViewById(R.id.textView21));
            holder.txt_dizhi = ((TextView) view.findViewById(R.id.textView19));
            holder.txt_morendizhi = ((TextView) view.findViewById(R.id.textView20));
            holder.imageView5 = ((ImageView) view.findViewById(R.id.imageView5));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.txt_name.setText(list.get(i).getConsignee());
        holder.txt_phone.setText(list.get(i).getTel());
        holder.txt_dizhi.setText(list.get(i).getAddress());

        //默认地址
        holder.txt_morendizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sign % 2 == 0) {
                    holder.imageView5.setImageResource(R.drawable.addresslvseyuan);
                } else {
                    holder.imageView5.setImageResource(R.drawable.addresshuiseyuan);
                    Toast.makeText(context, "取消成功！", Toast.LENGTH_SHORT).show();
                }
                sign++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.send(HttpRequest.HttpMethod.POST,
                                "http://139.196.234.104:8000/appapi/Merchant/EditDefaultAddress?merchantId=1&addressid=" + list.get(i).getAddressId(),
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        String result = responseInfo.result;
                                        try {
                                            JSONObject json = new JSONObject(result);
                                            String state = json.getString("state");
                                            if ("0".equals(state)) {
                                                Toast.makeText(context, "操作成功！", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void onFailure(HttpException error, String msg) {

                                        Toast.makeText(context,"操作失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).start();
            }
        });

        //跳转编辑页面
        holder.relativeLayout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditAddActivity.class);

               context.startActivity(intent);
            }
        });
        //删除地址
        holder.relative_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(i);

            }
        });

        return view;
    }


    private void dialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确认删除吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.send(HttpRequest.HttpMethod.POST,
                                "http://139.196.234.104:8000/appapi/Merchant/DelMerchantAddress?merchantId=1&addressid=" + list.get(i).getAddressId(),
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        String result = responseInfo.result;

                                        try {
                                            JSONObject json = new JSONObject(result);
                                            String state = json.getString("state");
                                            System.out.println("---------001" + state);
                                            if ("0".equals(state)) {
                                                Toast.makeText(context, "删除数据成功！", Toast.LENGTH_SHORT).show();
                                                list.remove(i);
                                                notifyDataSetChanged();
                                            } else if ("1".equals(state)) {
                                                Toast.makeText(context, "操作失败！", Toast.LENGTH_SHORT).show();
                                                // dialog(i);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(HttpException error, String msg) {
                                    }
                                });
                    }
                }.start();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    class ViewHolder {
        private TextView txt_name, txt_phone, txt_dizhi, txt_morendizhi;
        ImageView imageView5;
        RelativeLayout relativeLayout8, relative_shanchu;
    }
}
