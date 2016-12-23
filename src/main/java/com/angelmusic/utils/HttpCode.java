package com.angelmusic.utils;

/**
 * 响应码常量类
 * Created by wangyong on 16-12-13.
 */
public interface HttpCode {


    //操作成功
    String SUCCESS = "200";
    String SUCCESS_WORD = "操作成功";

    //操作失败
    String FAIL = "201";
    String FAIL_WORD = "服务器异常";


    //客户端编号为空
    String CLIENTID_EMPTY = "201212";
    String CLIENTID_EMPTY_WORD = "客户端编号为空";

    //客户端已经激活过了
    String CLIENT_ACTIVATED = "201213";
    String CLIENT_ACTIVATED_WORD = "客户端已经激活过";

    //客户端还未激活
    String CLIENT_NOT_ACTIVATED = "201214";
    String CLIENT_NOT_ACTIVATED_WORD = "客户端还未激活过";

    //二维码编号为空
    String CODE_NO_EMPTY = "201215";
    String CODE_NO_EMPTY_WORD = "二维码编号为空";

    //二维码不存在
    String CODENO_NOT_EXISTS = "201216";
    String CODENO_NOT_EXISTS_WORD = "二维码不存在";

    //二维码绑定的客户端个数已达上限
    String OVER_CODENO_BIND_CLIENTS = "201217";
    String OVER_CODENO_BIND_CLIENTS_WORD = "二维码绑定的客户端个数已达上限";

    // 绑定成功
    String CODE_NO_BIND_CLIENT_SUCESS_WORD = "客户端绑定成功";

    //激活码参数为空
    String ACTIVATION_CODE_EMPTY = "201218";
    String ACTIVATION_CODE_EMPTY_WORD = "激活码参数为空";

    //激活码已经激活过了
    String ACTIVATION_CODE_ACTIVATED = "201219";
    String ACTIVATION_CODE_ACTIVATED_WORD = "激活码已经激活过了";

    //激活码不存在
    String ACTIVATION_CODE_NOT_EXISTS = "201220";
    String ACTIVATION_CODE_NOT_EXISTS_WORD = "激活码不存在";

    String ACTIVATION_CODE_ACTIVATED_OK = "激活码激活成功";

    //手机号码为空
    String USER_PHONE_EMPTY = "201221";
    String USER_PHONE_EMPTY_WORD = "用户手机号码为空";

    //上传用户信息成功
    String UPLOAD_USERINFO_SUCCESS_WORD = "上传用户信息成功";

    //上传用户信息失败
    String UPLOAD_USERINFO_FAIL_WORD = "上传用户信息失败";

    //用户已经长传过
    String UPLOAD_USER_EXISTS = "201222";
    String UPLOAD_USER_EXISTS_WORD = "用户信息已经存在";

    //主题加载成功
    String TOPICS_LOAD_SUCCESS_WORD = "主题加载成功";

    //用户编号为空
    String USERID_EMPTY = "201223";
    String USERID_EMPTY_WORD = "用户编号为空";

    //订单生成成功
    String ORDER_RECORD_SAVE_SUCESS_WORD = "订单生成成功";

    //订单生成失败
    String ORDER_RECORD_CREATE_FAIL = "201224";
    String ORDER_RECORD_CREATE_FAIL_WORD = "订单生成失败";

    //参数异常
    String PARAMS_INVAILD = "201225";
    String CREATEORDERRECORD_PARAMS_INVAILD = "用户编号,消费金额，产品，支付类型不可以为空";

    //订单金额不可以小于0
    String ORDER_RECORD_MONEY_LESS_ZERO_WORD = "201225";

    //订单类型只能是激活码和大礼包
    String ORDER_TYPE_OVER_WORD = "订单类型只能是激活码和大礼包";

    //订单更新成功
    String ORDER_UPDATE_SCUCESS_WORD = "订单更新成功";

    //订单更新失败
    String ORDER_UPDATE_FAIL = "201226";
    String ORDER_UPDATE_FAIL_WORD = "订单更新失败";

    //统接密码不正确
    String TJ_ENCRYPTDATA_ERROR = "201227";
    String TJ_ENCRYPTDATA_ERROR_WORD = "统接密码不正确";

    //主题编号和用户编号不能为空
    String TOPIC_USER_EMPTY_WORD = "主题编号和用户手机号不能为空";

    //用户手机号不可以为空
    String MOBILE_EMPTY_WORD = "用户手机号不可以为空";

    //关卡状态只有完成和未完成两个状态
    String MISSION_STATUS_OVER_WORD = "关卡状态只能有完成和未完成两个状态";

    //关卡更新成功
    String MISSION_UPDATE_SUCCESS_WORD = "关卡状态更新成功";

    //关卡更新失败
    String MISSION_UPDATE_FAIL_WORD = "关卡更新失败";

}
