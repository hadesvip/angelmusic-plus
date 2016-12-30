package com.angelmusic.service;

import com.angelmusic.dao.model.User;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 用户服务
 * Created by wangyong on 16-12-19.
 */
public class UserService {

    public static final UserService ME = new UserService();

    /**
     * 上传用户信息
     *
     * @return
     */
    @Before(Tx.class)
    public HttpResult uploadUserInfo(String userPhone) {

        //用户信息是否已经存在
        final User user = User.ME.getUserByPhone(userPhone);
        if (user != null) {
            return new HttpResult(HttpCode.UPLOAD_USER_EXISTS, HttpCode.UPLOAD_USER_EXISTS_WORD, user);
        }

        //保存用户信息
        if (User.ME.saveUser(userPhone)) {
            return new HttpResult(HttpCode.SUCCESS, HttpCode.UPLOAD_USERINFO_SUCCESS_WORD, User.ME.getUserByPhone(userPhone));
        }

        //失败
        return new HttpResult(HttpCode.FAIL, HttpCode.UPLOAD_USERINFO_FAIL_WORD);

    }

    /**
     * 获取用户信息
     *
     * @param userPhone 用户手机号
     * @return
     */
    public HttpResult getUser(String userPhone) {
        final User user = User.ME.getUserByPhone(userPhone);
        return new HttpResult(HttpCode.SUCCESS, HttpCode.SUCCESS_WORD, user);
    }

}
