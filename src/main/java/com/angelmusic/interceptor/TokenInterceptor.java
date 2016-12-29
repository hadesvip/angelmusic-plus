package com.angelmusic.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * token拦截器
 * <p>
 * token规则:...
 * Created by wangyong on 16-12-29.
 */
public class TokenInterceptor implements Interceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public void intercept(Invocation inv) {
        LOGGER.info("[invoke TokenInterceptor]");

        System.out.println("---执行TokenInterceptor---");

        inv.invoke();
    }
}
