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

    //锁住
    int LOCKED = 1;

    //未锁住
    int UNLOCKED = 2;

    //订单类型:激活码
    int ORDER_TYPE_ACTIVATECODE = 1;

    //订单类型:大礼包
    int ORDER_TYPE_GIFT_PACK = 2;

    //主题收费
    int TOPIC_UNFREE = 1;

    //主题免费
    int TOPIC_FREE = 2;

    //支付成功
    int PAY_SUCESS = 1;

    //支付失败
    int PAY_FAIL = 2;

    //支付中
    int PAY_PAYING = 3;

    //统接加密
    String TJSDK_ENCRYPTDATA = "00c2ba5facc11ba8842144bfcdc15869";

    //关卡完成
    int MISSION_COMPLETE = 2;

    //关卡未完成
    int MISSION_UNCOMPLETE = 1;
}
