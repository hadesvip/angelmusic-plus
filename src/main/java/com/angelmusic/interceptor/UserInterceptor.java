package com.angelmusic.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户拦截器
 * Created by wangyong on 16-12-16.
 */
public class UserInterceptor implements Interceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public void intercept(Invocation inv) {
        final HttpServletRequest request = inv.getController().getRequest();
        //TODO 用户逻辑
        inv.invoke();
    }
}
