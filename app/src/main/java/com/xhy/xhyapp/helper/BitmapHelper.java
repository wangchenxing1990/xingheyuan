package com.xhy.xhyapp.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.factory.BitmapFactory;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class BitmapHelper {
    public static BitmapUtils init(Context context) {
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        BitmapDisplayConfig config = new BitmapDisplayConfig();
        config.setBitmapFactory(new Factory());
        bitmapUtils.configDefaultDisplayConfig(config);//对图片二次加工
//        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.ic_launcher);//错误图片
//        bitmapUtils.configDefaultLoadingImage(R.mipmap.ic_launcher);//默认图片
        // //默认下载图片大小是以屏幕大小为准，手动设置图片最大宽高
        //  bitmapUtils.configDefaultBitmapMaxSize(200,200);
        return bitmapUtils;
    }
    public static class Factory implements BitmapFactory {

        @Override
        public BitmapFactory cloneNew() {
            return new Factory();
        }
        /**
         * 图片的加工方法，该方法只在写入缓存的时候执行一次
         * @param rawBitmap
         * @return
         */
        @Override
        public Bitmap createBitmap(Bitmap rawBitmap) {
            Bitmap output=Bitmap.createBitmap(rawBitmap.getWidth(),rawBitmap.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas=new Canvas(output);
            Paint paint=new Paint();
            paint.setColor(Color.BLACK);
            Path path=new Path();

            //右上角
            path.moveTo(rawBitmap.getWidth(),0);
//            path.lineTo(rawBitmap.getWidth(), 30);
//            path.lineTo(rawBitmap.getWidth() - 30, 0);
//            path.lineTo(rawBitmap.getWidth(), 0);
            canvas.drawBitmap(rawBitmap,0,0,null);
            canvas.drawPath(path,paint);
            rawBitmap.recycle();
            return output;
        }
    }
}
