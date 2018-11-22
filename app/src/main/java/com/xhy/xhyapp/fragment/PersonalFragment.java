package com.xhy.xhyapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
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
public class PersonalFragment extends Fragment implements View.OnClickListener{

    private Intent intent1,intent2,intent3 ;
    EditText personname,personadress,personwork,personId,personnumber,personyanzhengnumber;
    String name,adress,work,id,number,yanzhengnymber;
    int phoneId , requestId ;
    TextView yanzheng;
    boolean phone1button,phone2button;
    Button button1,button2 , buttontijiao;
    PopupWindow popupWindow;
    TimeCount timeCount;
    private String userID;
    String fileName;
    private byte[] bytesbitmap;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button1 = ((Button) view.findViewById(R.id.rg_person_phone1));button1.setOnClickListener(this);
        button2 = ((Button) view.findViewById(R.id.rg_person_phone2));button2.setOnClickListener(this);
        buttontijiao = ((Button) view.findViewById(R.id.rg_person_tijiao1));buttontijiao.setOnClickListener(this);


        personname = ((EditText) view.findViewById(R.id.rg_person_personname));
        personadress = ((EditText) view.findViewById(R.id.rg_person_personaddress));
        personwork = ((EditText) view.findViewById(R.id.rg_person_personwork));
        personId = ((EditText) view.findViewById(R.id.rg_person_personId));
        personnumber = ((EditText) view.findViewById(R.id.rg_person_personnumber));
        personyanzhengnumber = ((EditText) view.findViewById(R.id.rg_person_yanzhennumber));
        yanzheng = ((TextView) view.findViewById(R.id.rg_person_yanzheng));yanzheng.setOnClickListener(this);

        intent1 = new Intent(getActivity(), XieyiActivity.class);
        intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
        intent3 = new Intent(Intent.ACTION_GET_CONTENT);
        timeCount = new TimeCount(60000,1000);

        Intent it = getActivity().getIntent();
        Bundle bundle1 = it.getExtras();
        userID = bundle1.getString("userID");
        System.out.println("------------------"+userID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rg_person_tijiao1:
                name = personname.getText().toString().trim();
                adress = personadress.getText().toString().trim();
                id = personId.getText().toString().trim();
                work = personwork.getText().toString().trim();
                number = personnumber.getText().toString().trim();
                yanzhengnymber = personyanzhengnumber.getText().toString().trim();
                if (phone1button&&phone2button){
                    if(name.length()>0 && adress.length()>0 && id.length()>0 && work.length()>0 && number.length()>0 &&
                            yanzhengnymber.length()>0){
                        System.out.println("-----------"+userID);
                        String path2 = "http://139.196.234.104:8000/appapi/Merchant/PerfectInfoPerson?name="+name+"&address="+adress+"&workSplace="+work+"&idcard="+id+"&mob="+number+"&number="+yanzhengnymber+"&merchantId="+userID;
                        System.out.println("-----------------------"+path2);
                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.send(HttpRequest.HttpMethod.POST, path2, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String s1 = responseInfo.result;
                                try {
                                    JSONObject json = new JSONObject(s1);
                                    if ("0".equals(json.getString("state"))){
                                        Toast.makeText(getContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
                                        startActivity(intent1);
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
            case R.id.rg_person_yanzheng:
                number = personnumber.getText().toString().trim();
                if ( isNumber(number)){
                    timeCount.start();
                    HttpUtils httputils = new HttpUtils();
                    httputils.send(HttpRequest.HttpMethod.POST, "http://139.196.234.104:8000/appapi/Other/SendSms?type=2&mob="+number+"&merchantId=0", new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String resultq = responseInfo.result;
                            try {
                                JSONObject json = new JSONObject(resultq);
                                String getnumberstate = json.getString("state");
                                if("0".equals(getnumberstate)){
                                    Toast.makeText(getContext(),"短信已发送",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
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
            case R.id.rg_person_phone1:
                showPopupWindow();
                phoneId = 1;
                break;
            case R.id.rg_person_phone2:
                showPopupWindow();
                phoneId = 2;
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            Bundle bundle;
            Bitmap bitmap = null;
            if (requestId == 1 ){
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
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                Cursor cursor = getContext().getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                if (cursor!=null){
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    fileName = cursor.getString(columnIndex);
//                    cursor.close();
//                }else {
//                    fileName = selectedImage.getPath();
//                }
//                if (fileName.length()>0){
//                    File file1 =new File(fileName);
//                    if (file1.exists()){
//                        bitmap = getCompree(fileName);
//                    }
//                }else {
//                    Toast.makeText(getContext(),"图片获取失败，请拍照上传",Toast.LENGTH_SHORT).show();
//                }

            }

            if (phoneId == 1){
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
                            httpPost.addHeader("pType","4");
                            httpPost.addHeader("shopId","0");
                            ByteArrayEntity se = new ByteArrayEntity(bytesbitmap);
                            httpPost.setEntity(se);
                            try {
                                HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                                String st = EntityUtils.toString(httpResponse.getEntity());
                                System.out.println("--------------------------"+st);
                                JSONObject jsonObject = new JSONObject(st);
                                String s11 = jsonObject.getString("state");
                                if ("0".equals(s11)){
                                    System.out.println("--------------chenggong");
                                    phone1button = true;
                                    Toast.makeText(getContext(),"营业执照上传成功！",Toast.LENGTH_SHORT).show();
                                }else{
                                    phone1button = false;
                                    System.out.println("--------------false");
                                    Toast.makeText(getContext(),"营业执照上传失败！",Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    button1.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }else {
                    Toast.makeText(getContext(),"获取图片失败！",Toast.LENGTH_SHORT).show();
                }
                bitmap = null;
            }else {
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
                                    phone2button = true;
                                    Toast.makeText(getContext(),"营业执照上传成功！",Toast.LENGTH_SHORT).show();
                                }else{
                                    phone2button = false;
                                    Toast.makeText(getContext(),"营业执照上传失败！",Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    button2.setBackgroundDrawable(new BitmapDrawable(bitmap));
                }else {
                    Toast.makeText(getContext(),"获取图片失败！",Toast.LENGTH_SHORT).show();
                }
                bitmap = null;
            }
        }
    }

//    private String geturipath(Uri uri) {
//        boolean isKitKat = Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT;
//        if (isKitKat && )
//        if (uri.getScheme().toString().compareTo("context")==0){
//            Cursor cursor = getActivity().getContentResolver().query(uri,new String[]{MediaStore.Audio.Media.DATA},null,null,null);
//            if (cursor.moveToFirst()){
//                fileName=cursor.getString(0);
//            }
//        }else if (uri.getScheme().toString().compareTo("file")==0){
//            fileName = uri.toString();
//            fileName = uri.toString().replace("file://", "");
//            if (!fileName.startsWith("/mnt")){
//                fileName+="mnt";
//            }
//
//        }
//        return "";
//    }

    private void showPopupWindow() {
        popupWindow = new RegisterPopubWindowZhuce(getActivity(),R.layout.register_popubwindow_zhuce);
        popupWindow.showAtLocation(getActivity().findViewById(R.id.reg_zhunce), Gravity.CENTER_HORIZONTAL,0,0);
        View view = popupWindow.getContentView();
        ((Button) view.findViewById(R.id.rg_btn_first)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestId = 1 ;
                intent2.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                File out = new File(getPhotopath());
                Uri uri = Uri.fromFile(out);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent2, Activity.RESULT_FIRST_USER);
                popupWindow.dismiss();
            }
        });
        ((Button) view.findViewById(R.id.rg_btn_third)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent3.setType("image/*");
                //intent2.putExtra("crop", "true");
                intent3.putExtra("return-data", "false");
                requestId = 2 ;
                startActivityForResult(intent3 , Activity.RESULT_FIRST_USER);
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
    //判断手机号号段
    // */
    private boolean isNumber(String sj) {
        if (Pattern.compile("^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$").matcher(sj).matches()) {
            return true;
        }
        Toast.makeText(getContext(),"请输入正确手机号！",Toast.LENGTH_SHORT).show();
        return false;
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
}
