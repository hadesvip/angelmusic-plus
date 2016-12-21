package com.angelmusic.utils;

/**
 * 常量类
 * Created by wangyong on 16-12-13.
 */
public interface Constant {

    //二维码绑定客户端个数上限
    int LIMIT_QRCODE_BINDCOUNT = 5;

    //激活码状态已激活
    int ACTIVATION_CODE_ACTIVATED = 2;

    //主题锁住
    int TOPIC_LOCKED = 1;

    //主题未锁住
    int TOPIC_UNLOCKED = 2;

    //订单类型:激活码
    int ORDER_TYPE_ACTIVATECODE = 1;

    //订单类型:大礼包
    int ORDER_TYPE_GIFT_PACK = 2;

    //主题收费
    int TOPIC_UNFREE = 1;

    //支付成功
    int PAY_SUCESS = 1;

    //支付失败
    int PAY_FAIL = 2;

    //支付中
    int PAY_PAYING = 3;

}
