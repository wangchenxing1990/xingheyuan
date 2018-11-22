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
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.view.PhotoPopubwindow;
import com.xhy.xhyapp.view.RegisterPopubWindow;

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

public class RegisterStoreActivity extends AppCompatActivity {
    int IMAGES;
    private ImageView image_touxiang;
    RegisterPopubWindow registerPopubWindow;
    PhotoPopubwindow photoPopubwindow;
    private Button kaidian_btn;
    String storemingcheng, storephone, storename;
    private EditText edit_shopmingcheng;
    private EditText edit_shopphone;
    private EditText edit_shopname;
    //添加的返回按钮
    private ImageView iv_kaidian_back;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Bitmap bitmap;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private byte[] bytesbitmap;
    boolean bitmapshangchuang = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store);

        image_touxiang = ((ImageView) findViewById(R.id.image_touxiang));
        edit_shopmingcheng = ((EditText) findViewById(R.id.edit_shopname));
        edit_shopphone = ((EditText) findViewById(R.id.edit_shopphone));
        edit_shopname = ((EditText) findViewById(R.id.edit_storename));

        iv_kaidian_back=(ImageView) findViewById(R.id.iv_kaidian_back);
        iv_kaidian_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        kaidian_btn = ((Button) findViewById(R.id.kaidian_btn));
        kaidian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        storemingcheng = edit_shopmingcheng.getText().toString().trim();
                        storephone = edit_shopphone.getText().toString().trim();
                        storename = edit_shopname.getText().toString().trim();
                        if (storemingcheng.length()<2||storephone.length()<7||storename.length()<2) {
                            Toast.makeText(RegisterStoreActivity.this, "请输入正确内容！", Toast.LENGTH_SHORT).show();
                        }else {
                            HttpUtils httpUtils = new HttpUtils();
                            httpUtils.send(HttpRequest.HttpMethod.POST,
                                    "http://139.196.234.104:8000/appapi/Merchant/OpenShop?merchantId=1&shopName=" + storemingcheng + "&realName=" + storename + "&tel=" + storephone,
                                    new RequestCallBack<String>() {
                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            String result = responseInfo.result;
                                            try {
                                                JSONObject json = new JSONObject(result);
                                                String state = json.getString("state");
                                                switch (state) {
                                                    case "0":
                                                        Toast.makeText(RegisterStoreActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    case "1":
                                                        Toast.makeText(RegisterStoreActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    case "100":
                                                        Toast.makeText(RegisterStoreActivity.this, "您已经有店了！", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(RegisterStoreActivity.this, StoreActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(HttpException error, String msg) {
                                            Toast.makeText(RegisterStoreActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                }).start();
            }
        });

        image_touxiang.setOnClickListener(new View.OnClickListener() {
            private Button btn_cancel;
            private Button btn_third;
            private Button btn_second;
            private Button btn_first;

            @Override
            public void onClick(View view) {
                registerPopubWindow = new RegisterPopubWindow(RegisterStoreActivity.this, itemOnclick, R.layout.register_popubwindow);
                registerPopubWindow.showAtLocation(RegisterStoreActivity.this.findViewById(R.id.relative_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                View view1 = registerPopubWindow.getView();
                btn_first = ((Button) view1.findViewById(R.id.btn_first));
                btn_second = ((Button) view1.findViewById(R.id.btn_second));
                btn_third = ((Button) view1.findViewById(R.id.btn_third));
                btn_cancel = ((Button) view1.findViewById(R.id.btn_cancel));

                btn_first.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        camera();
                        registerPopubWindow.dismiss();
                    }
                });
                btn_second.setOnClickListener(new View.OnClickListener() {
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

                        photoPopubwindow = new PhotoPopubwindow(RegisterStoreActivity.this, itemOnclick, R.layout.photo_popubwindow);
                        photoPopubwindow.showAtLocation(RegisterStoreActivity.this.findViewById(R.id.relative_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                        View view12 = photoPopubwindow.getView();

                        image01 = (ImageView) view12.findViewById(R.id.image01);
                        image02 = (ImageView) view12.findViewById(R.id.image02);
                        image03 = (ImageView) view12.findViewById(R.id.image03);
                        image04 = (ImageView) view12.findViewById(R.id.image04);
                        image05 = (ImageView) view12.findViewById(R.id.image05);
                        image06 = (ImageView) view12.findViewById(R.id.image06);

                        btn_sure = (Button) view12.findViewById(R.id.btn_sure001);
                        btn_cancel = (Button) view12.findViewById(R.id.btn_cancel);

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
                                        image_touxiang.setImageResource(R.drawable.tou01_hui);
                                        uploadbitmap(zhuan(R.drawable.tou01_hui));
                                        break;
                                    case 2:
                                        image_touxiang.setImageResource(R.drawable.tou02_hui);
                                        uploadbitmap( zhuan(R.drawable.tou02_hui));
                                        break;
                                    case 3:
                                        image_touxiang.setImageResource(R.drawable.tou03_hui);
                                        uploadbitmap(zhuan(R.drawable.tou03_hui));
                                        break;
                                    case 4:
                                        image_touxiang.setImageResource(R.drawable.tou04_hui);
                                        uploadbitmap(zhuan(R.drawable.tou04_hui));
                                        break;
                                    case 5:
                                        image_touxiang.setImageResource(R.drawable.tou05_hui);
                                        uploadbitmap(zhuan(R.drawable.tou05_hui));
                                        break;
                                    case 6:
                                        image_touxiang.setImageResource(R.drawable.tou06_hui);
                                        uploadbitmap( zhuan(R.drawable.tou06_hui));
                                        break;
                                    default:
                                        break;
                                }
                                photoPopubwindow.dismiss();
                                registerPopubWindow.dismiss();
                            }
                        });
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                photoPopubwindow.dismiss();
                                registerPopubWindow.dismiss();
                            }
                        });
                    }
                });
                btn_third.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gallery();
                        registerPopubWindow.dismiss();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        registerPopubWindow.dismiss();
                    }
                });
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
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(RegisterStoreActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.image_touxiang.setImageBitmap(bitmap);
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

    View.OnClickListener itemOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            registerPopubWindow.dismiss();
        }
    };
}