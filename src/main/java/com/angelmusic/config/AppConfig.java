package com.angelmusic.config;

import com.angelmusic.interceptor.ExceptionInterceptor;
import com.angelmusic.interceptor.TokenInterceptor;
import com.angelmusic.interceptor.UserInterceptor;
import com.angelmusic.plugin.sql.PlusSqlIXmlPlugin;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.plugin.tablebind.ParamNameStyles;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * 项目配置
 * Created by wangyong on 16-12-13.
 */
public class AppConfig extends JFinalConfig {

    /**
     * 常量参数配置
     *
     * @param me
     */
    @Override
    public void configConstant(Constants me) {

        //开发者模式
        me.setDevMode(true);

        // freemarker
        me.setBaseViewPath("/WEB-INF/template/");
        me.setFreeMarkerViewExtension(".html");


        //加载配置文件
        loadPropertyFile("app.properties");
        PropKit.use("jdbc.properties");
    }

    /**
     * 路由配置
     *
     * @param me
     */
    @Override
    public void configRoute(Routes me) {

        //自动绑定路由，启动注解方式
        me.add(new AutoBindRoutes());

    }

    /**
     * 配置插件
     *
     * @param me
     */
    @Override
    public void configPlugin(Plugins me) {

        //数据源配置
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbc.url"), PropKit.get("jdbc.username"), PropKit.get("jdbc.password"));


        //自动绑定表跟model的插件
        AutoTableBindPlugin autoTableBindPlugin = new AutoTableBindPlugin(druidPlugin, ParamNameStyles.lowerUnderlineModule("am"));
        autoTableBindPlugin.setShowSql(true);
        autoTableBindPlugin.addScanPackages("com.angelmusic.dao.model");

        me.add(druidPlugin);
        me.add(autoTableBindPlugin);

        //sql配置文件方式
        me.add(new PlusSqlIXmlPlugin());
        me.add(new EhCachePlugin());
    }

    /**
     * 拦截器
     *
     * @param me
     */
    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new UserInterceptor());
        me.add(new ExceptionInterceptor());
        me.add(new TokenInterceptor());
    }

    /**
     * 处理器
     *
     * @param me
     */
    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("baseUrl"));
    }
}
