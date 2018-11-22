package com.xhy.xhyapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.RegisterPopubWindowZhuce;
import com.xhy.xhyapp.XieyiActivity;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends Fragment implements View.OnClickListener{

    private Intent intent ,intent1,intent2;
    private Button imageButton1 , imageButton2  , imageButton3 , button1 ;
    private int buttonId ,requwstId ;
    EditText companyname,adress,faname,fanameId,accounts,fanamenumber,yanzhengnumber,companyxianqing;
    String companynameString,adressString,fanameString,fanameIdString,accountsString,fanamenumberString,yanzhengnumberString,companyDetail;
    boolean phone1,phone2,phone3;
    private PopupWindow popupWindow;
    TextView yanzheng;
    TimeCount timeCount;
    private String userID;
    private String fileName;
    private byte[] bytesbitmap;
    String urlphone = "";

    public CompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageButton1 = ((Button) view.findViewById(R.id.rg_company_phone1));imageButton1.setOnClickListener(this);
        imageButton2 = ((Button) view.findViewById(R.id.rg_company_phone2));imageButton2.setOnClickListener(this);
        imageButton3 = ((Button) view.findViewById(R.id.rg_company_phone3));imageButton3.setOnClickListener(this);

        companyname = ((EditText) view.findViewById(R.id.rg_company_name));
        adress = ((EditText) view.findViewById(R.id.rg_company_address));
        faname = ((EditText) view.findViewById(R.id.rg_company_faname));
        fanameId = ((EditText) view.findViewById(R.id.rg_company_fanameId));
        fanamenumber = ((EditText) view.findViewById(R.id.rg_company_fanamenumber));
        accounts = ((EditText) view.findViewById(R.id.rg_company_accounts));
        yanzhengnumber = ((EditText) view.findViewById(R.id.rg_company_yanzhengnumber));
        companyxianqing = ((EditText) view.findViewById(R.id.companyxiangqing));

        button1 = ((Button) view.findViewById(R.id.rg_company_tijiao1));button1.setOnClickListener(this);
        yanzheng = ((TextView) view.findViewById(R.id.rg_company_yanzheng));yanzheng.setOnClickListener(this);

        intent = new Intent(getActivity(), XieyiActivity.class);
        intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
        intent2 = new Intent(Intent.ACTION_GET_CONTENT);

        timeCount = new TimeCount(60000,1000);
        urlphone =  "http://139.196.234.104:8000/Ashx/merchantUpImg.ashx";  // 上传图片的路径
        Intent it = getActivity().getIntent();
        Bundle bundle1 = it.getExtras();
        userID = bundle1.getString("userID");
        System.out.println("------------------"+userID);
    }


    //**
    // 回调功能
    // 图片压缩
    // 上传图片
    // 设为按钮背景
    // */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            Bitmap bitmap = null;
            if (requwstId == 1){
                File fi = new File(fileName);
                if (fi.exists()){
                    bitmap = getCompree(fileName);
                }
            }else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),data.getData());
                    System.out.println("---------------data-----"+data.getData());
                    if (bitmap!=null){
                        double maxSize = 400.00;
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                        byte[] b = baos.toByteArray();
                        double mid = b.length/1024;
                        if (mid>maxSize){
                            double i = mid/maxSize;
                            bitmap = zoomImage(bitmap,bitmap.getWidth()/Math.sqrt(i),
                                    bitmap.getHeight()/Math.sqrt(i));
                        }
//                        ByteArrayOutputStream ba = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,ba);
//                        byte[] bb = ba.toByteArray();
//                        double md = b.length/1024;
//                        System.out.println("--------md-----"+md);
                    }else {
                        System.out.println("---------------bitmap-----null");
                        bitmap = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
////                Uri uri = data.getData();
////                fileName = uri.getPath();
//
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                Cursor cursor = getContext().getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//            //    cursor.moveToFirst();
//                if (cursor!=null){
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    fileName = cursor.getString(columnIndex);
//                }else {
//                    Toast.makeText(getContext(),"图片获取失败，请拍照上传",Toast.LENGTH_SHORT).show();
//                }
//                cursor.close();
//
//                File file1 =new File(fileName);
//                if (file1.exists()){
//                    bitmap = getCompree(fileName);
//                }
            }

            if (buttonId == 1){         //上传营业执照
                if (bitmap!=null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    bytesbitmap = baos.toByteArray();
                    new Thread(){
                        @Override
                        public void run() {
                            Looper.prepare();
                            String url = "http://139.196.234.104:8000/Ashx/merchantUpImg.ashx";
                            HttpPost httpPost = new HttpPost(url);
                            httpPost.addHeader("con-lenght",""+bytesbitmap.length);
                            httpPost.addHeader("merchantId",userID);
                            httpPost.addHeader("extension",".jpg");
                            httpPost.addHeader("pType","3");
                            httpPost.addHeader("shopId","0");
                            ByteArrayEntity se = new ByteArrayEntity(bytesbitmap);
                            httpPost.setEntity(se);
                            try {
                                HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                                String st = EntityUtils.toString(httpResponse.getEntity());
                                System.out.println("--------------------------"+st);
                                JSONObject jsonObject = new JSONObject(st);
                                if ("0".equals(jsonObject.getString("state"))){
                                    phone1 = true;
                                    Toast.makeText(getContext(),"营业执照上传成功！",Toast.LENGTH_SHORT).show();
                                }else{
                                    phone1 = false;
                                    Toast.makeText(getContext(),"营业执照上传失败！",Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    imageButton1.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }else {
                    Toast.makeText(getContext(),"获取图片失败！",Toast.LENGTH_SHORT).show();
                }
                bitmap = null;
            }else if (buttonId == 2 ){
                if (bitmap!=null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    bytesbitmap = baos.toByteArray();
                    new Thread(){
                        @Override
                        public void run() {
                            Looper.prepare();
                            String url = "http://139.196.234.104:8000/Ashx/merchantUpImg.ashx";
                            HttpPost httpPost = new HttpPost(url);
                            httpPost.addHeader("con-lenght",""+bytesbitmap.length);
                            httpPost.addHeader("merchantId",userID);
                            System.out.println("------------------"+userID);
                            httpPost.addHeader("extension",".jpg");
                            httpPost.addHeader("pType","4");
                            httpPost.addHeader("shopId","0");
                            ByteArrayEntity se = new ByteArrayEntity(bytesbitmap);
                            httpPost.setEntity(se);
                            try {
                                HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                                String st = EntityUtils.toString(httpResponse.getEntity());
                                System.out.println("--------------------------"+st);
                                JSONObject jsonObject = new JSONObject(st);
                                if ("0".equals(jsonObject.getString("state"))){
                                    phone2 = true;
                                    Toast.makeText(getContext(),"身份证正面照上传成功！",Toast.LENGTH_SHORT).show();
                                }else{
                                    phone2 = false;
                                    Toast.makeText(getContext(),"身份证正面照上传失败！",Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    imageButton2.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }else {
                    Toast.makeText(getContext(),"获取图片失败！",Toast.LENGTH_SHORT).show();
                }
                bitmap = null;
            }else {
                if (bitmap!=null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    System.out.println("------------------"+bitmap.getRowBytes()*bitmap.getHeight());
                    bytesbitmap = baos.toByteArray();
                    new Thread(){
                        @Override
                        public void run() {
                            Looper.prepare();
                            String url = "http://139.196.234.104:8000/Ashx/merchantUpImg.ashx";
                            HttpPost httpPost = new HttpPost(url);
                            httpPost.addHeader("con-lenght",""+bytesbitmap.length);
                            System.out.println("--------------------"+bytesbitmap.length);
                            httpPost.addHeader("merchantId",userID);
                            System.out.println("------------------"+userID);
                            httpPost.addHeader("extension",".jpg");
                            httpPost.addHeader("pType","5");
                            httpPost.addHeader("shopId","0");
                            ByteArrayEntity se = new ByteArrayEntity(bytesbitmap);
                            httpPost.setEntity(se);
                            try {
                                HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                                String st = EntityUtils.toString(httpResponse.getEntity());
                                System.out.println("--------------------------"+st);
                                JSONObject jsonObject = new JSONObject(st);
                                if ("0".equals(jsonObject.getString("state"))){
                                    phone3 = true;
                                    Toast.makeText(getContext(),"身份证反面照上传成功！",Toast.LENGTH_SHORT).show();
                                }else{
                                    phone3 = false;
                                    Toast.makeText(getContext(),"身份证反面照上传失败！",Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    imageButton3.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }else {
                    Toast.makeText(getContext(),"获取图片失败！",Toast.LENGTH_SHORT).show();
                }
                bitmap = null;
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rg_company_tijiao1:
                //*
                //此处联网进行资料提交，同时进行预判断
                // */
                companynameString = companyname.getText().toString().trim();
                adressString = adress.getText().toString().trim();
                fanameString = faname.getText().toString().trim();
                fanameIdString = fanameId.getText().toString().trim();
                fanamenumberString = fanamenumber.getText().toString().trim();
                accountsString = accounts.getText().toString().trim();
                yanzhengnumberString = yanzhengnumber.getText().toString().trim();
                companyDetail = companyxianqing.getText().toString().trim();
                System.out.println("-----------------"+phone1+"-------"+phone2+"--------------"+phone3);
                if (phone1&&phone2&&phone3){
                    if(companynameString.length()>0 && adressString.length()>0 && fanameString.length()>0 && fanameIdString.length()>0 && fanamenumberString.length()>0 &&
                            yanzhengnumberString.length()>0 && accountsString.length()>0 && companyDetail.length()>0){
                        System.out.println("-----------"+userID);
                        String path1 = "http://139.196.234.104:8000/appapi/Merchant/PerfectInfoCompany?companyName="+companynameString+"&companyAddress="+adressString+
                                "&name="+fanameString+"&idCard="+fanameIdString+"&bankAcccount="+accountsString+"&mob="+fanamenumberString+"&number="+yanzhengnumberString+"&companyInfo="+
                                companyDetail+"&merchantId="+userID;
                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.send(HttpRequest.HttpMethod.POST, path1, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String s1 = responseInfo.result;
                                try {
                                    JSONObject json = new JSONObject(s1);
                                    if ("0".equals(json.getString("state"))){
                                        Toast.makeText(getContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        getActivity().finish();
                                    }else {
                                        Toast.makeText(getContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Toast.makeText(getContext(),"上传失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(getContext(),"请正确输入！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(),"请上传图片！",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.rg_company_phone1:    //营业执照
                buttonId = 1;
                showPopupWindow();
                break;
            case R.id.rg_company_phone2:      //身份证正面
                buttonId = 2;
                showPopupWindow();
                break;
            case R.id.rg_company_phone3:       //身份证反面
                buttonId = 3;
                showPopupWindow();
                break;
            case R.id.rg_company_yanzheng:
                //*
                //获取验证码，验证
                // */
                fanamenumberString = fanamenumber.getText().toString().trim();
                if (NumberLength(fanamenumberString) && isNumber(fanamenumberString)){
                    timeCount.start();
                    HttpUtils httputils = new HttpUtils();
                    httputils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Other/SendSms?type=2&mob="+fanamenumberString+"&merchantId=0", new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String resultq = responseInfo.result;
                            try {
                                JSONObject json = new JSONObject(resultq);
                                String getnumberstate = json.getString("state");
                                String s12 = json.getString("msg");
                                if("0".equals(getnumberstate)){
                                    Toast.makeText(getContext(),"短信已发送",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(),s12,Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(getContext(),"发送失败！",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    //*
    //点击图片展示提示框
    // */
    private void showPopupWindow() {
        popupWindow = new RegisterPopubWindowZhuce(getActivity(),R.layout.register_popubwindow_zhuce);
        popupWindow.showAtLocation(getActivity().findViewById(R.id.reg_zhunce),Gravity.CENTER_HORIZONTAL,0,0);
        View view = popupWindow.getContentView();
        ((Button) view.findViewById(R.id.rg_btn_first)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requwstId = 1 ;
                intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                File out = new File(getPhotopath());
                Uri uri = Uri.fromFile(out);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent1, Activity.RESULT_FIRST_USER);
                popupWindow.dismiss();
            }
        });
        ((Button) view.findViewById(R.id.rg_btn_third)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent2.setType("image/*");
                //intent2.putExtra("crop", "true");
                intent2.putExtra("return-data", "false");
                requwstId = 2 ;
                startActivityForResult(intent2, Activity.RESULT_FIRST_USER);
                popupWindow.dismiss();
            }
        });
        ((Button) view.findViewById(R.id.rg_btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    //*
    //判断是否是手机号
    // */
    private boolean NumberLength(String usernamber1) {
        if (11 == usernamber1.length()){
            return true;
        }
        Toast.makeText(getContext(),"请输入正确手机号！",Toast.LENGTH_SHORT).show();
        return false;
    }

    //*
    //判断手机号号段
    // */
    private boolean isNumber(String sj) {
        if (Pattern.compile("^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$").matcher(sj).matches()) {
            return true;
        }
        Toast.makeText(getContext(),"请输入正确手机号！",Toast.LENGTH_SHORT).show();
        return false;
    }

    private String getPhotopath() {
        // 照片全路径
        // 文件夹路径
        SimpleDateFormat formatter  =   new    SimpleDateFormat    ("yyyyMMddHHmmss");
        Date curDate    =   new Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        String pathUrl = Environment.getExternalStorageDirectory()+"/DCIM/";
        String imageName = str+".jpg";
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        return fileName;
    }


    //*
    // 此处对图片进行压缩至800*480
    // */

    private Bitmap getCompree(String filepath) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath,op);
        int height = op.outHeight;
        int width = op.outWidth;
        int reqHeight = 800;
        int reqWidth = 480;
        if (height > reqHeight || width > reqWidth){
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
        }
        op.inSampleSize = calculateInSampleSize(op,480,800);
        op.inJustDecodeBounds = false;
        Bitmap bitmapl = BitmapFactory.decodeFile(filepath, op);
        return bitmapl;
    }

    //*
    // 计算图片的缩放值
    // */

    private int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private  Bitmap zoomImage(Bitmap bgimage, double newWidth,
                              double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    //*
    // 获取验证码方法
    // */
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long l) {
            yanzheng.setText((l/1000)+"");
            yanzheng.setClickable(true);
        }
        @Override
        public void onFinish() {
            yanzheng.setText("验证");
            yanzheng.setClickable(false);
        }
    }
}