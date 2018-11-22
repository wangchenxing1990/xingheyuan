package com.xhy.xhyapp.storeactivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;
import com.xhy.xhyapp.MainActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.view.MyPhonePopuWindown;
import com.xhy.xhyapp.view.MySendPopuwindown;
import com.xhy.xhyapp.view.SystemPhoneWindown;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView iv_untreate_order;
    private TextView tv_com_control;
    private TextView tv_order_control;
    private TextView tv_shop_control;
    private ImageView imageView,click_home_main;

    private MySendPopuwindown myPopuwindownSend;
    private List<Integer> images = new ArrayList<Integer>();
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Bitmap bitmap;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private TextView shop_name_txt;
    private byte[] bytesbitmap;
    boolean bitmapshangchuang = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        //初始化view
        initView();
        //初始化数据
        initData();
        requestData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        iv_untreate_order = (ListView) findViewById(R.id.iv_untreate_order);
        //商品管理 订单管理 店铺管理
        tv_com_control = (TextView) findViewById(R.id.tv_com_control);
        tv_order_control = (TextView) findViewById(R.id.tv_order_control);
        tv_shop_control = (TextView) findViewById(R.id.tv_shop_control);
        click_home_main = (ImageView) findViewById(R.id.click_home_main);
        imageView=(ImageView) findViewById(R.id.imageView);
        shop_name_txt = ((TextView) findViewById(R.id.shop_name_txt));

        imageView.setOnClickListener(this);
        click_home_main.setOnClickListener(this);
        tv_com_control.setOnClickListener(this);
        tv_order_control.setOnClickListener(this);
        tv_shop_control.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        images.add(R.drawable.blueberry);
        images.add(R.drawable.cherry);
        images.add(R.drawable.honey_peach);
        images.add(R.drawable.blueberry);
        images.add(R.drawable.cherry);
        images.add(R.drawable.honey_peach);

        MyListAdapter adapter = new MyListAdapter();
        iv_untreate_order.setAdapter(adapter);
    }
    private void requestData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils httputils=new HttpUtils();
                httputils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Merchant/GetShop?merchantId=1",
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result=responseInfo.result;
                                try {
                                    JSONObject json=new JSONObject(result);
                                    String shopName=json.getString("shopName");
                                    String headPic=json.getString("headPic");
                                    shop_name_txt.setText(shopName);
                                    Picasso.with(StoreActivity.this).load(headPic).into(imageView);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {

                            }
                        });
            }
        }).start();
    }
    View.OnClickListener itemOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            myPhoneWindown.dismiss();
        }
    };
    /**
     * 管理跳转界面
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_home_main:
                Intent intent4=new Intent(this,MainActivity.class);
                startActivity(intent4);
                break;
            case R.id.tv_com_control:
                tv_com_control.setTextColor(0xff3fc199);
                tv_shop_control.setTextColor(0xff5a5a5a);
                tv_order_control.setTextColor(0xff5a5a5a);
                Intent intent = new Intent(StoreActivity.this, CommodityControlActivity.class);
                intent.putExtra("name", "0");
                startActivity(intent);
                break;
            case R.id.tv_order_control:
                tv_order_control.setTextColor(0xff3fc199);
                tv_com_control.setTextColor(0xff5a5a5a);
                tv_shop_control.setTextColor(0xff5a5a5a);
                Intent intent1 = new Intent(StoreActivity.this, CommodityControlActivity.class);
                intent1.putExtra("name", "1");
                startActivity(intent1);
                break;
            case R.id.tv_shop_control:
                tv_shop_control.setTextColor(0xff3fc199);
                tv_order_control.setTextColor(0xff5a5a5a);
                tv_com_control.setTextColor(0xff5a5a5a);
                Intent intent2 = new Intent(StoreActivity.this, CommodityControlActivity.class);
                intent2.putExtra("name", "2");
                startActivity(intent2);
                break;
            case R.id.imageView:
                disPlayPopuWindown();
                break;
        }
    }

    MyPhonePopuWindown myPhoneWindown;
    SystemPhoneWindown systemPhomneWindown;
    private int IMAGES;
    private void disPlayPopuWindown() {
        myPhoneWindown = new MyPhonePopuWindown(StoreActivity.this, itemOnclick, R.layout.phone_popuwindown);
        myPhoneWindown.showAtLocation(StoreActivity.this.findViewById(R.id.main_send), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        View phoneView = myPhoneWindown.getView();

        Button btn_take_phone = (Button) phoneView.findViewById(R.id.btn_take_phone);
        Button btn_system_select = (Button) phoneView.findViewById(R.id.btn_system_select);
        Button btn_phones_select = (Button) phoneView.findViewById(R.id.btn_phones_select);
        Button btn_cancel_select = (Button) phoneView.findViewById(R.id.btn_cancel_select);

        btn_take_phone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                camera();
                myPhoneWindown.dismiss();

            }
        });
        btn_system_select.setOnClickListener(new View.OnClickListener() {
            private ImageView image01;
            private ImageView image02;
            private ImageView image03;
            private ImageView image04;
            private ImageView image05;
            private ImageView image06;
            private Button btn_sure;
            private Button btn_cancel;

            @Override
            public void onClick(View view) {

                systemPhomneWindown = new SystemPhoneWindown(StoreActivity.this, itemOnclick, R.layout.display_sys_phone);
                systemPhomneWindown.showAtLocation(StoreActivity.this.findViewById(R.id.main_send), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                View view12 = systemPhomneWindown.getView();

                image01 = (ImageView) view12.findViewById(R.id.image001);
                image02 = (ImageView) view12.findViewById(R.id.image002);
                image03 = (ImageView) view12.findViewById(R.id.image003);
                image04 = (ImageView) view12.findViewById(R.id.image004);
                image05 = (ImageView) view12.findViewById(R.id.image005);
                image06 = (ImageView) view12.findViewById(R.id.image006);

                btn_sure = (Button) view12.findViewById(R.id.btn_sure0001);
                btn_cancel = (Button) view12.findViewById(R.id.btn_cancel01);

                image01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image01.setImageResource(R.drawable.tou01_liang);
                        image02.setImageResource(R.drawable.tou02_hui);
                        image03.setImageResource(R.drawable.tou03_hui);
                        image04.setImageResource(R.drawable.tou04_hui);
                        image05.setImageResource(R.drawable.tou05_hui);
                        image06.setImageResource(R.drawable.tou06_hui);
                        IMAGES = 1;
                    }
                });
                image02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image01.setImageResource(R.drawable.tou01_hui);
                        image02.setImageResource(R.drawable.tou02_liang);
                        image03.setImageResource(R.drawable.tou03_hui);
                        image04.setImageResource(R.drawable.tou04_hui);
                        image05.setImageResource(R.drawable.tou05_hui);
                        image06.setImageResource(R.drawable.tou06_hui);
                        IMAGES = 2;
                    }
                });
                image03.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image01.setImageResource(R.drawable.tou01_hui);
                        image02.setImageResource(R.drawable.tou02_hui);
                        image03.setImageResource(R.drawable.tou03_liang);
                        image04.setImageResource(R.drawable.tou04_hui);
                        image05.setImageResource(R.drawable.tou05_hui);
                        image06.setImageResource(R.drawable.tou06_hui);
                        IMAGES = 3;
                    }
                });
                image04.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image01.setImageResource(R.drawable.tou01_hui);
                        image02.setImageResource(R.drawable.tou02_hui);
                        image03.setImageResource(R.drawable.tou03_hui);
                        image04.setImageResource(R.drawable.tou04_liang);
                        image05.setImageResource(R.drawable.tou05_hui);
                        image06.setImageResource(R.drawable.tou06_hui);
                        IMAGES = 4;
                    }
                });
                image05.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image01.setImageResource(R.drawable.tou01_hui);
                        image02.setImageResource(R.drawable.tou02_hui);
                        image03.setImageResource(R.drawable.tou03_hui);
                        image04.setImageResource(R.drawable.tou04_hui);
                        image05.setImageResource(R.drawable.tou05_liang);
                        image06.setImageResource(R.drawable.tou06_hui);
                        IMAGES = 5;
                    }
                });
                image06.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image01.setImageResource(R.drawable.tou01_hui);
                        image02.setImageResource(R.drawable.tou02_hui);
                        image03.setImageResource(R.drawable.tou03_hui);
                        image04.setImageResource(R.drawable.tou04_hui);
                        image05.setImageResource(R.drawable.tou05_hui);
                        image06.setImageResource(R.drawable.tou06_liang);
                        IMAGES = 6;
                    }
                });
                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (IMAGES) {
                            case 1:
                                imageView.setImageResource(R.drawable.tou01_hui);
                                uploadbitmap(zhuan(R.drawable.tou01_hui));
                                break;
                            case 2:
                                imageView.setImageResource(R.drawable.tou02_hui);
                                uploadbitmap( zhuan(R.drawable.tou02_hui));
                                break;
                            case 3:
                                imageView.setImageResource(R.drawable.tou03_hui);
                                uploadbitmap(zhuan(R.drawable.tou03_hui));
                                break;
                            case 4:
                                imageView.setImageResource(R.drawable.tou04_hui);
                                uploadbitmap(zhuan(R.drawable.tou04_hui));
                                break;
                            case 5:
                                imageView.setImageResource(R.drawable.tou05_hui);
                                uploadbitmap(zhuan(R.drawable.tou05_hui));
                                break;
                            case 6:
                                imageView.setImageResource(R.drawable.tou06_hui);
                                uploadbitmap( zhuan(R.drawable.tou06_hui));
                                break;
                            default:
                                break;
                        }
                        systemPhomneWindown.dismiss();
                        myPhoneWindown.dismiss();
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myPhoneWindown.dismiss();
                    }
                });
            }
        });
        btn_phones_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();
                myPhoneWindown.dismiss();
            }
        });
        btn_cancel_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPhoneWindown.dismiss();
            }
        });
    }

    private Bitmap zhuan(int id) {
        Resources res = getResources();
        Drawable drawable = res.getDrawable(id);
        BitmapDrawable bd = ((BitmapDrawable) drawable);
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    /*
    * 从相机获取
    */
    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(),PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        // startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(StoreActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.imageView.setImageBitmap(bitmap);
                //上传头像
                uploadbitmap(bitmap);
                boolean delete = tempFile.delete();
                System.out.println("delete = " + delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadbitmap(Bitmap bitmap1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
        bytesbitmap = baos.toByteArray();
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                String url = "http://139.196.234.104:8000/Ashx/merchantUpImg.ashx";
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("con-lenght",""+bytesbitmap.length);
                httpPost.addHeader("merchantId","1");
                httpPost.addHeader("extension",".jpg");
                httpPost.addHeader("pType","2");
                httpPost.addHeader("shopId","1");
                ByteArrayEntity se = new ByteArrayEntity(bytesbitmap);
                httpPost.setEntity(se);
                try {
                    HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                    String st = EntityUtils.toString(httpResponse.getEntity());
                    JSONObject jsonObject = new JSONObject(st);
                    String state = jsonObject.getString("state");
                    if ("0".equals(state)){
                        bitmapshangchuang = true;
                        Toast.makeText(getBaseContext(),"门店头像上传成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getBaseContext(),"门店头像上传失败！",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 剪切图片
     *
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    /*
         * 从相册获取
         */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = getLayoutInflater().inflate(R.layout.order_item, null);
                holder.iv = (ImageView) view.findViewById(R.id.iv_order);
                holder.tv_sure_send = (TextView) view.findViewById(R.id.tv_sure_send);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.iv.setImageResource(images.get(position));
            holder.tv_sure_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myPopuwindownSend = new MySendPopuwindown(StoreActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myPopuwindownSend.dismiss();
                        }
                    }, R.layout.send_my_popu);

                    myPopuwindownSend.showAtLocation(StoreActivity.this.findViewById(R.id.main_send), Gravity.CENTER, 0, 0);

                    View mView = myPopuwindownSend.getView();
                    TextView tv_my_cancel = (TextView) mView.findViewById(R.id.tv_my_cancel);
                    TextView tv_my_sure = (TextView) mView.findViewById(R.id.tv_my_sure);

                    tv_my_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myPopuwindownSend.dismiss();
                        }
                    });

                    tv_my_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myPopuwindownSend.dismiss();
                        }
                    });
                    // Toast.makeText(getApplication(),"已发货",Toast.LENGTH_SHORT).show();

                }

            });
            return view;
        }
    }

    class ViewHolder {
        public ImageView iv;
        public TextView tv_sure_send;
    }
}
