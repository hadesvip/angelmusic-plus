package com.angelmusic.plugin.sql;

import com.jfinal.plugin.IPlugin;

/**
 * Created by wangyong on 16-12-14.
 */
public class PlusSqlIXmlPlugin implements IPlugin {
    @Override
    public boolean start() {
        PlusSqlKit.init();
        return true;
    }

    @Override
    public boolean stop() {
        PlusSqlKit.clearSqlMap();
        return true;
    }
}
