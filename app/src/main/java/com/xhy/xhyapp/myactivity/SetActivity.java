package com.xhy.xhyapp.myactivity;

/**
 * Created by Administrator on 2016/8/2.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xhy.xhyapp.LoginActivity;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.adapter.ZKKMyAdapter;
import com.xhy.xhyapp.myview.JCGXPopubWindow;
import com.xhy.xhyapp.view.DataCleanManager;
import com.xhy.xhyapp.view.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//新引入的包来加载远端文件


public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    public LinearLayout back_collect;
    public TextView tuichudenglu;
    public LinearLayout dizhiguanli;
    public LinearLayout qingchuhuncun;
    public RelativeLayout jianchagengxin;
    public JCGXPopubWindow jcgxPopubWindow;
    public ImageView chahao;

    public TextView banbenhao;
    public TextView quedinggengxin;
    public ListView listView;
    public DataCleanManager datacleanmanager;
    public Context context;
    public UpdateManager updateManager;

    public ProgressBar mProgress;
    private Dialog mDownloadDialog;
    public String wenjian;
    public String apkFile;

    private Context mContext;
    private List<String> list = new ArrayList();

    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap = new HashMap<>();
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zkkactivity_main);
        init();
    }

    public void init() {
        back_collect = (LinearLayout) findViewById(R.id.ll_back);
        tuichudenglu = (TextView) findViewById(R.id.tuichudenglu);
        dizhiguanli = (LinearLayout) findViewById(R.id.dizhiguanli);
        qingchuhuncun = (LinearLayout) findViewById(R.id.qingchuhuancun);
        jianchagengxin = (RelativeLayout) findViewById(R.id.jianchagengxin);
        back_collect.setOnClickListener(this);
        banbenhao=(TextView) findViewById(R.id.banbenhao);

        banbenhao.setText( getVerName(this.getApplicationContext(),getPackName(this.getApplicationContext())));
        dizhiguanli.setOnClickListener(this);
        qingchuhuncun.setOnClickListener(this);
        jianchagengxin.setOnClickListener(this);
        dizhiguanli.setOnClickListener(this);
        tuichudenglu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.dizhiguanli:
                Intent intent = new Intent(SetActivity.this, ShippingAddressManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.qingchuhuancun:
                datacleanmanager = new DataCleanManager();
                try {
                    wenjian = DataCleanManager.getTotalCacheSize(context);
                    Toast.makeText(SetActivity.this, wenjian, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.jianchagengxin:


                //获取相应信息   imei：设备号；imsi：卡号； merchantId：商户ID；versionId：版本ID；apkPackName：包名；paltFrom；平台android:0/ios:1

                try {
                    StringBuilder strLog = new StringBuilder();
                    Context ctx = this.getApplicationContext();

                    /**
                     * 1.获取应用信息
                     *
                     * 要想获取更多应用相关信息请查阅PackageManager、ApplicationInfo资料
                     */

                    // 获取应用名称
                    String appName = getAppName(ctx);
                    strLog.append("应用名称:" + appName + "\r\n");

                    // 获取应用包名称
                    String apkpackName = getPackName(ctx);
                    strLog.append("应用包名称:" + apkpackName + "\r\n");

                    // 获取应用版本
                    String verName = getVerName(ctx, apkpackName);
                    strLog.append("应用版本名称:" + verName + "\r\n");

                    // 获取应用版本号
                    final double versionId = getVerCode(ctx, apkpackName);
                    strLog.append("应用版本号:" + versionId + "\r\n");

                    /**
                     * 2.获取设备信息
                     */
                    // 获取手机型号
                    String model = getPhoneModel();
                    strLog.append("手机型号:" + model + "\r\n");

//            // 获取手机号码
//            String phoneNum = getLineNum(ctx);
//            strLog.append("手机号码:" + phoneNum + "\r\n");

                    // 获取移动用户标志，IMSI
                    String imsi = getSubscriberId(ctx);
                    strLog.append("IMSI:" + imsi + "\r\n");

                    // 获取设备ID
                    String imei = getDeviceID(ctx);
                    strLog.append("设备ID:" + imei + "\r\n");

                    // 获取SIM卡号
                    String sim = getSim(ctx);
                    strLog.append("SIM卡号:" + sim + "\r\n");

                    //当前app版本号和服务器app版本号对比
                    String ApiUrl = "http://139.196.234.104:8000/appapi/Other/SoftUpdate?imei=" + imei + "&imsi=" + imsi + "&merchantId=" + 1 + "&versionId=" + versionId + "&apkPackName=" + apkpackName + ".apk&paltFrom=0";
                    HttpUtils http = new HttpUtils();

                    http.send(HttpRequest.HttpMethod.POST, ApiUrl, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String strings = responseInfo.result;
                            try {
                                JSONObject jsonObject = new JSONObject(strings);

                                if (1 == Integer.parseInt(jsonObject.getString("state"))) {
                                    Toast.makeText(SetActivity.this, "已是最新版本", Toast.LENGTH_LONG).show();
                                } else if (0 == Integer.parseInt(jsonObject.getString("state"))) {

                                    String updateContent = jsonObject.getString("updateContent");

                                    apkFile = jsonObject.getString("apkFile");

                                    Toast.makeText(SetActivity.this, jsonObject.getString("state"), Toast.LENGTH_LONG).show();

                                    Toast.makeText(SetActivity.this, jsonObject.getString("updateContent"), Toast.LENGTH_LONG).show();

                                    View.OnClickListener itemsOnClick = new View.OnClickListener() {
                                        public void onClick(View v) {

                                        }
                                    };
                                    jcgxPopubWindow = new JCGXPopubWindow(SetActivity.this, itemsOnClick, R.layout.zkkpopub);
                                    //显示窗口  设置layout在PopupWindow中显示的位置
                                    jcgxPopubWindow.showAtLocation(SetActivity.this.findViewById(R.id.shezhi), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    View view1 = jcgxPopubWindow.getView();
                                    chahao = (ImageView) view1.findViewById(R.id.chahao);
                                    quedinggengxin = (TextView) view1.findViewById(R.id.quedinggengxin);

                                    listView = (ListView) view1.findViewById(R.id.gengxinneironglistview);
                                    list.clear();
                                    list.add(updateContent);


                                    ZKKMyAdapter zkkMyAdapter=new ZKKMyAdapter(SetActivity.this,list);
                                    listView.setAdapter(zkkMyAdapter);

                                    Toast.makeText(SetActivity.this, "唔哈哈哈"+list.toString(), Toast.LENGTH_LONG).show();

                                    //listView.setAdapter(new ZKKMyAdapter(SetActivity.this, list));

                                    chahao.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            jcgxPopubWindow.dismiss();
                                        }
                                    });

                                    quedinggengxin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showDownloadDialog();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return;
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            return;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

            case R.id.tuichudenglu:
                Intent intent1 = new Intent(SetActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;

        }
    }


    //更新提示框
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
        builder.setTitle(R.string.soft_updating);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(SetActivity.this);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progressBar1);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = (Dialog) builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    mSavePath = Environment.getExternalStorageDirectory() + "/download/";
                    mHashMap.put("url", apkFile);

                    Log.e("################", mSavePath);
                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        conn.connect();
                        // 获取文件大小
                        int length = conn.getContentLength();

                        // 创建输入流
                        InputStream is = conn.getInputStream();

                        File file = new File(mSavePath);
                        // 判断文件目录是否存在
                        if (!file.exists()) {
                            file.mkdir();
                        }

                        File apkFile = new File(mSavePath + "app-debug.apk");

                        Log.e("################", mSavePath + "app-debug.apk");

                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        // 缓存
                        byte buf[] = new byte[1024];
                        // 写入到文件中
                        do {
                            int numread = is.read(buf);
                            count += numread;
                            // 计算进度条位置
                            progress = (int) (((float) count / length) * 100);
                            // 更新进度
                            mHandler.sendEmptyMessage(DOWNLOAD);
                            if (numread <= 0) {
                                // 下载完成
                                mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            // 写入文件
                            fos.write(buf, 0, numread);
                        } while (!cancelUpdate);// 点击取消就停止下载.
                        fos.close();
                        is.close();
                    } else {
                        mDownloadDialog.dismiss();
                        //     Toast.makeText(SetActivity.this, "更新失败", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    }

    ;

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath + "app-debug.apk");
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        SetActivity.this.startActivity(i);

    }


    /**
     * 获取应用包名称
     */
    public String getPackName(Context ctx) {
        return ctx.getPackageName();
    }

    /**
     * 获取应用版本名称
     */
    public String getVerName(Context ctx, String packName) {
        String verName = "";
        try {
            verName = ctx.getPackageManager().getPackageInfo(packName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verName;
    }

    /**
     * 获取应用版本号
     */
    public int getVerCode(Context context, String packName) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(packName,
                    0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionCode;
    }

    /**
     * 获取应用名称
     */
    public String getAppName(Context ctx) {
        String appName = "";
        try {
            PackageManager packManager = ctx.getPackageManager();
            ApplicationInfo appInfo = ctx.getApplicationInfo();
            appName = (String) packManager.getApplicationLabel(appInfo);
        } catch (Exception e) {
        }
        return appName;
    }

    /**
     * 获取手机型号
     * <p/>
     * android.os.Build提供以下信息：
     * String  BOARD   The name of the underlying board, like "goldfish".
     * String  BRAND   The brand (e.g., carrier) the software is customized for, if any.
     * String  DEVICE  The name of the industrial design.
     * String  FINGERPRINT     A string that uniquely identifies this build.
     * String  HOST
     * String  ID  Either a changelist number, or a label like "M4-rc20".
     * String  MODEL   The end-user-visible name for the end product.
     * String  PRODUCT     The name of the overall product.
     * String  TAGS    Comma-separated tags describing the build, like "unsigned,debug".
     * long    TIME
     * String  TYPE    The type of build, like "user" or "eng".
     * String  USER
     */
    public String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机号码，一般获取不到
     * <p/>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * <p/>
     * 要想获取更多电话、数据、移动网络相关信息请查阅TelephonyManager资料
     */
    public String getLineNum(Context ctx) {
        String strResult = "";
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            strResult = telephonyManager.getLine1Number();
        }
        return strResult;
    }

    /**
     * 获取移动用户标志，IMSI
     * <p/>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public String getSubscriberId(Context ctx) {
        String strResult = "";
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            strResult = telephonyManager.getSubscriberId();
        }
        return strResult;
    }

    /**
     * 获取设备ID
     * <p/>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public String getDeviceID(Context ctx) {
        String strResult = null;
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            strResult = telephonyManager.getDeviceId();
        }
        if (strResult == null) {
            strResult = Settings.Secure.getString(ctx.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return strResult;
    }

    /**
     * 获取SIM卡号
     * <p/>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public String getSim(Context ctx) {
        String strResult = "";
        TelephonyManager telephonyManager = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            strResult = telephonyManager.getSimSerialNumber();
        }
        return strResult;
    }

}
