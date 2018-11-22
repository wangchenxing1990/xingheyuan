package com.xhy.xhyapp.storeactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xhy.xhyapp.R;
import com.xhy.xhyapp.view.SquareCenterImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReturnApplyActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_back;
    private List<Integer> images = new ArrayList<Integer>();
    private GridView gv_prove;
    Context context;
    private int selectPic = -1;
    ReturnApplyAdapter returnApplyAdapter;
    private TextView txt_yes;
    private TextView txt_no;
    public static DisplayImageOptions mNormalImageOptions;
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    public static final String IMAGES_FOLDER = SDCARD_PATH + File.separator + "demo" + File.separator + "images" + File.separator;
    private Button with_btn;

    //public static int[] images{}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_apply);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initImageLoader(this);
        ll_back = ((LinearLayout) findViewById(R.id.ll_back));
        txt_yes = ((TextView) findViewById(R.id.txt_yes));
        txt_no = ((TextView) findViewById(R.id.txt_no));
        with_btn = ((Button) findViewById(R.id.with_btn));

        with_btn.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        txt_yes.setOnClickListener(this);
        txt_no.setOnClickListener(this);

        images.add(R.drawable.pingzhneg001);
        images.add(R.drawable.pingzhneg002);
        images.add(R.drawable.pingzhneg003);
        gv_prove = ((GridView) findViewById(R.id.gv_prove));
        returnApplyAdapter = new ReturnApplyAdapter(getApplicationContext(), images);
        gv_prove.setAdapter(returnApplyAdapter);

        gv_prove.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(ReturnApplyActivity.this,ApplyAmplificationActivity.class);
                intent.putExtra("location",i+1);
                startActivity(intent);
            }
        });



//       gv_prove.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//           @Override
//           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//           }
//           @Override
//           public void onNothingSelected(AdapterView<?> adapterView) {
//
//           }
//       });
    }

    private void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
        MemoryCacheAware<String, Bitmap> memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }

        mNormalImageOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisc(true)
                .resetViewBeforeLoading(true).build();

        // This
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(mNormalImageOptions)
                .denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiscCache(new File(IMAGES_FOLDER)))
                // .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(memoryCache)
                // .memoryCacheSize(memoryCacheSize)
                .tasksProcessingOrder(QueueProcessingType.LIFO).threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3).build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_yes:
                txt_yes.setTextColor(0xff3fc199);
                txt_yes.setBackgroundResource(R.drawable.lvbiankuang);

                txt_no.setTextColor(0xff5a5a5a);
                txt_no.setBackgroundResource(R.drawable.huibiankuang);
                break;
            case R.id.txt_no:
                txt_no.setTextColor(0xff3fc199);
                txt_no.setBackgroundResource(R.drawable.lvbiankuang);

                txt_yes.setTextColor(0xff5a5a5a);
                txt_yes.setBackgroundResource(R.drawable.huibiankuang);
                break;
            case R.id.with_btn:
                Intent intent=new Intent(ReturnApplyActivity.this,AgreeApplyActivity.class);
                startActivity(intent);
                break;
        }
    }

    public class ReturnApplyAdapter extends BaseAdapter {
        Context context;
        private List<Integer> images = new ArrayList<>();

        public ReturnApplyAdapter(Context context, List<Integer> images) {
            this.context = context;
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int i) {
            return images.get(i);
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
                view = View.inflate(context, R.layout.prove_item, null);
                holder.image_prove = (ImageView) view.findViewById(R.id.image_prove);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.image_prove.setImageResource(images.get(i));
            final SquareCenterImageView imageView = new SquareCenterImageView(ReturnApplyActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage(String.valueOf(images.get(i)), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ReturnApplyActivity.this, SpaceImageDetailActivity.class);
                    intent.putExtra("images", (ArrayList<Integer>) images);
                    intent.putExtra("position", i);
                    int[] location = new int[2];
                    imageView.getLocationOnScreen(location);
                    intent.putExtra("locationX", location[0]);
                    intent.putExtra("locationY", location[1]);

                    intent.putExtra("width", imageView.getWidth());
                    intent.putExtra("height", imageView.getHeight());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

            });
            return view;
        }

        class ViewHolder {
            ImageView image_prove;
        }
    }
}