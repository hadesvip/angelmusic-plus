package com.angelmusic.interceptor;

import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 * Created by wangyong on 16-12-16.
 */
public class ExceptionInterceptor implements Interceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        try {

            inv.invoke();

        } catch (Exception e) {

            //开发模式
            if (JFinal.me().getConstants().getDevMode()) {
                e.printStackTrace();
            }

            //记录下错误日记
            LOGGER.error("[invoke controller occur exception,Contorller:" + controller + ",method:" + inv.getMethodName() + ", exception's detail:" + e.getMessage() + "]");
            controller.renderJson(new HttpResult(HttpCode.FAIL, HttpCode.FAIL_WORD));

        } finally {
            //最终处理：比如记录错误信息到mongodb或者db中
        }


    }
}
