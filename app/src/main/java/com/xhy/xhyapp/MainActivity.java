package com.xhy.xhyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.xhy.xhyapp.adapter.ZKKMyAdapter;
import com.xhy.xhyapp.fragment.FinanceFragment;
import com.xhy.xhyapp.fragment.HomepageFragment;
import com.xhy.xhyapp.fragment.MyFragment;
import com.xhy.xhyapp.fragment.PurchaseFragment;
import com.xhy.xhyapp.fragment.StoreFragment;
import com.xhy.xhyapp.myview.JCGXPopubWindow;

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

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager view_pager;
    private LinearLayout ll_firstpage;
    private LinearLayout ll_caigou;
    private LinearLayout ll_jinrong;
    private LinearLayout ll_store;
    private LinearLayout ll_my;
    private ImageView image_shouye;
    private ImageView image_caigou;
    private ImageView image_jinrong;
    private ImageView image_store;
    private ImageView image_my;
    private TextView txt_firstpage;
    private TextView txt_caigou;
    private TextView txt_jinrong;
    private TextView txt_store;
    private TextView txt_my;
    List<Fragment> list = new ArrayList<>();
    List<LinearLayout> data = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private long mExitTime;

    //新添加的
    public String apkFile;
    public JCGXPopubWindow jcgxPopubWindow;
    public TextView quxiaogengxin;
    public TextView quedinggengxin;
    public ListView listView;
    List<String> list1 = new ArrayList<>();
    public ProgressBar mProgress;
    private Dialog mDownloadDialog;

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
    ImageView img01, img02, img03, img04;

    PushAgent pushAgent;

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
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pushAgent = PushAgent.getInstance(this);
        pushAgent.enable();
        pushAgent.onAppStart();
        String device_token = UmengRegistrar.getRegistrationId(this);
        System.out.println("-----------001" + device_token);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        int mode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        getWindow().setSoftInputMode(mode);
        img01 = new ImageView(this);
        img02 = new ImageView(this);
        img03 = new ImageView(this);
        img04 = new ImageView(this);
        //初始化view
        initView();
        //初始化fragment
        initFragment();
        //设置默认的显示首页界面
        showFragment(fragments.get(0));
    }

    /**
     * 初始化view
     */
    private void initView() {
        ll_firstpage = (LinearLayout) findViewById(R.id.ll_home);
        ll_caigou = (LinearLayout) findViewById(R.id.ll_pro);
        ll_jinrong = (LinearLayout) findViewById(R.id.ll_fin);
        ll_store = (LinearLayout) findViewById(R.id.ll_shop);
        ll_my = (LinearLayout) findViewById(R.id.ll_mine);

        image_shouye = ((ImageView) findViewById(R.id.img_shouye));
        image_caigou = ((ImageView) findViewById(R.id.img_caigou));
        image_jinrong = ((ImageView) findViewById(R.id.img_jinrong));
        image_store = ((ImageView) findViewById(R.id.img_store));
        image_my = ((ImageView) findViewById(R.id.img_wode));

        txt_firstpage = ((TextView) findViewById(R.id.txt_shouye));
        txt_caigou = ((TextView) findViewById(R.id.txt_caigou));
        txt_jinrong = ((TextView) findViewById(R.id.txt_jinrong));
        txt_store = ((TextView) findViewById(R.id.txt_store));
        txt_my = ((TextView) findViewById(R.id.txt_wode));

        //设置监听
        ll_firstpage.setOnClickListener(this);
        ll_caigou.setOnClickListener(this);
        ll_jinrong.setOnClickListener(this);
        ll_store.setOnClickListener(this);
        ll_my.setOnClickListener(this);


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

                        if (0==Integer.parseInt(jsonObject.getString("state")) ) {

                            String updateContent = jsonObject.getString("updateContent");
                            apkFile = jsonObject.getString("apkFile");

                            //为弹出窗口实现监听类
                            View.OnClickListener itemsOnClick = new View.OnClickListener() {
                                public void onClick(View v) {
                                    //menuWindow.dismiss();
                                }
                            };
                            jcgxPopubWindow = new JCGXPopubWindow(MainActivity.this, itemsOnClick, R.layout.mainpopub);

                            //显示窗口  设置layout在PopupWindow中显示的位置
                            jcgxPopubWindow.showAtLocation(MainActivity.this.findViewById(R.id.zhuye), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            View view1 = jcgxPopubWindow.getView();
                            quxiaogengxin = (TextView) view1.findViewById(R.id.quxiaogengxin);
                            quedinggengxin = (TextView) view1.findViewById(R.id.quedinggengxin);

                            listView = (ListView) view1.findViewById(R.id.gengxinneironglistview);
                            list1.clear();
                            list1.add(updateContent);
                            listView.setAdapter(new ZKKMyAdapter(MainActivity.this, list1));

                            quxiaogengxin.setOnClickListener(new View.OnClickListener() {
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


    }


    //更新提示框
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.soft_updating);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
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
     * 添加fragment
     */
    private void initFragment() {
        fragments.add(new HomepageFragment(img01,img02,img03,img04));
        fragments.add(new PurchaseFragment());
        fragments.add(new FinanceFragment());
        fragments.add(new StoreFragment());
        fragments.add(new MyFragment());
    }

    private Fragment lastFragment;

    /**
     * 设置监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                showFragment(fragments.get(0));
                image_shouye.setImageResource(R.drawable.shouye_20);
                image_caigou.setImageResource(R.drawable.caigou_hui);
                image_store.setImageResource(R.drawable.shop_hui);
                image_my.setImageResource(R.drawable.wode_25);

                txt_firstpage.setTextSize(14);
                txt_caigou.setTextSize(12);
                txt_jinrong.setTextSize(12);
                txt_store.setTextSize(12);
                txt_my.setTextSize(12);

                txt_firstpage.setTextColor(0xff3fc199);
                txt_caigou.setTextColor(Color.GRAY);
                txt_jinrong.setTextColor(Color.GRAY);
                txt_store.setTextColor(Color.GRAY);
                txt_my.setTextColor(Color.GRAY);
                break;

            case R.id.ll_pro:
                showFragment(fragments.get(1));
                image_shouye.setImageResource(R.drawable.shouye_hui);
                image_caigou.setImageResource(R.drawable.caigou_liang);
                image_store.setImageResource(R.drawable.shop_hui);
                image_my.setImageResource(R.drawable.wode_25);

                txt_firstpage.setTextSize(12);
                txt_caigou.setTextSize(14);
                txt_jinrong.setTextSize(12);
                txt_store.setTextSize(12);
                txt_my.setTextSize(12);

                txt_firstpage.setTextColor(Color.GRAY);
                txt_caigou.setTextColor(0xff3fc199);
                txt_jinrong.setTextColor(Color.GRAY);
                txt_store.setTextColor(Color.GRAY);
                txt_my.setTextColor(Color.GRAY);

                break;
            case R.id.ll_fin:
                showFragment(fragments.get(2));
                image_shouye.setImageResource(R.drawable.shouye_hui);
                image_caigou.setImageResource(R.drawable.caigou_hui);
                image_store.setImageResource(R.drawable.shop_hui);
                image_my.setImageResource(R.drawable.wode_25);

                txt_firstpage.setTextSize(12);
                txt_caigou.setTextSize(12);
                txt_jinrong.setTextSize(14);
                txt_store.setTextSize(12);
                txt_my.setTextSize(12);

                txt_firstpage.setTextColor(Color.GRAY);
                txt_caigou.setTextColor(Color.GRAY);
                txt_jinrong.setTextColor(0xff3fc199);
                txt_store.setTextColor(Color.GRAY);
                txt_my.setTextColor(Color.GRAY);
                break;
            case R.id.ll_shop:
                showFragment(fragments.get(3));
                image_shouye.setImageResource(R.drawable.shouye_hui);
                image_caigou.setImageResource(R.drawable.caigou_hui);
                image_store.setImageResource(R.drawable.store_liang);
                image_my.setImageResource(R.drawable.wode_25);

                txt_firstpage.setTextSize(12);
                txt_caigou.setTextSize(12);
                txt_jinrong.setTextSize(12);
                txt_store.setTextSize(14);
                txt_my.setTextSize(12);

                txt_firstpage.setTextColor(Color.GRAY);
                txt_caigou.setTextColor(Color.GRAY);
                txt_jinrong.setTextColor(Color.GRAY);
                txt_store.setTextColor(0xff3fc199);
                txt_my.setTextColor(Color.GRAY);
                break;
            case R.id.ll_mine:
                showFragment(fragments.get(4));
                image_shouye.setImageResource(R.drawable.shouye_hui);
                image_caigou.setImageResource(R.drawable.caigou_hui);
                image_store.setImageResource(R.drawable.shop_hui);
                image_my.setImageResource(R.drawable.wode_liang);

                txt_firstpage.setTextSize(12);
                txt_caigou.setTextSize(12);
                txt_jinrong.setTextSize(12);
                txt_store.setTextSize(12);
                txt_my.setTextSize(14);

                txt_firstpage.setTextColor(Color.GRAY);
                txt_caigou.setTextColor(Color.GRAY);
                txt_jinrong.setTextColor(Color.GRAY);
                txt_store.setTextColor(Color.GRAY);
                txt_my.setTextColor(0xff3fc199);
                break;
        }
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

        //

        MainActivity.this.startActivity(i);

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

                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    int responseCode = conn.getResponseCode();

                    Log.e("################", responseCode+"");
                    Log.e("@@@@@@@@@@@@@@@@", apkFile);

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
          //              Toast.makeText(MainActivity.this, "更新失败", Toast.LENGTH_LONG).show();
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


}
