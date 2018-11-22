package com.xhy.xhyapp.bean;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/8/8.
 */
public class BitMapHelp {

    public BitMapHelp(){

    }
        private  static BitmapUtils bitmapUtils;

        public  static BitmapUtils getBitmapUtils(Context appContext) {
            if (bitmapUtils == null) {
                bitmapUtils = new BitmapUtils(appContext);
            }
            return bitmapUtils;
        }
    }





