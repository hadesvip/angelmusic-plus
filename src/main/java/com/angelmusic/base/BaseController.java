package com.angelmusic.base;

import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.core.Controller;

/**
 * controller基类
 * Created by wangyong on 16-12-13.
 */
public class BaseController extends Controller {

    protected void success() {
        renderJson(new HttpResult(HttpCode.SUCCESS, HttpCode.SUCCESS_WORD, null));
    }

    protected void success(Object object) {
        renderJson(new HttpResult(HttpCode.SUCCESS, HttpCode.SUCCESS_WORD, object));
    }

    protected void success(String code, String message) {
        renderJson(new HttpResult(code, message, null));
    }

    protected void error(String message) {
        renderJson(new HttpResult(HttpCode.FAIL, message, null));
    }

    protected void error() {
        renderJson(new HttpResult(HttpCode.FAIL, null, null));
    }

    protected void error(String code, String message) {
        renderJson(new HttpResult(code, message, null));
    }
}
