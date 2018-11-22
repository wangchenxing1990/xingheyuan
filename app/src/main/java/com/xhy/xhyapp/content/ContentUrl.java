package com.xhy.xhyapp.content;

/**
 * Created by Administrator on 2016/8/9.
 */
public class ContentUrl {

    public final static String BASE_URL="http://www.lvselianmeng.net:8000/";
    public final static String CATEGORY_URL="appapi/Goods/GetGoodsCategory";
    public final static String CATEGORY_LIST_URL="appapi/Goods/GetGoods";
    public final static String COLLECTION_URL="appapi/Goods/GetCollectionGoods";
    public final static String ADD_COLLECTION_URL="appapi/Goods/AddGoodsCollection";
    public final static String CANCEL_COLLECTION_URL="appapi/Goods/CancelGoodsCollection";
    public final static String CUSTOMER_SERVICE_ADD_URL="appapi/Merchant/CustomerServiceAdd";
    //获取商户地址的URL
    public final static String GET_MERCHANT_ADDRESS_URL="appapi/Merchant/GetMerchantAddress";
    //编辑地址的URL
    public final static String EDIT_ADDRESS_URL="appapi/Merchant/EditMerchantAddress";
    //添加新地址的URL
    public final static String ADD_NEW_ADDRESS_URL="appapi/Merchant/AddMerchantAddress";
    //获取商品详情的URL
    public final static String GET_GOODS_DETAIL_URL="appapi/Goods/GetGoodsDetail";
    //订单管理
    public final static String GET_USER_ORDER="appapi/UserOrder/GetUserOrder";
    //确认收货E_SEND_
    public final static String SURE_SEND_PRODUCT="appapi/UserOrder/merchantId";
    //生成订单
    public final static String GENERATE_URL="appapi/MerchantOrder/PlaceAnOrder";
    //获取订单信息
    public final static String GENERATE_ORDER_DETAIL_URL="appapi/MerchantOrder/GetMerchantOrder";
    //取消订单的URL
    public final static String CANCEl_ORDER_URL="appapi/MerchantOrder/CancelMerchantOrder";
    //提醒发货的URL
    public final static String REMIND_MERCHANT_URL="appapi/MerchantOrder/RemindMerchant";
    //确认收货
    public final static String CONFIRM_MERCHANT_URL="appapi/MerchantOrder/ConfirmMerchantOrder";

}
