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
    String FAIL_WORD = "操作失败";


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


}
