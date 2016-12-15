package com.angelmusic.plugin.sql;

import com.jfinal.ext.kit.JaxbKit;
import com.jfinal.ext.plugin.sqlinxml.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyong on 16-12-14.
 */
public class PlusSqlKit {

    protected static final Log LOG = Log.getLog(SqlKit.class);

    private static Map<String, String> sqlMap;

    public static String sql(String groupNameAndsqlId) {
        if (sqlMap == null) {
            throw new NullPointerException("SqlInXmlPlugin not start");
        }
        return sqlMap.get(groupNameAndsqlId);
    }

    static void clearSqlMap() {
        sqlMap.clear();
    }

    static void init() {
        sqlMap = new HashMap<String, String>();
        File file = new File(PathKit.getRootClassPath());
        File[] files = file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith("sql.xml")) {
                    return true;
                }
                return false;
            }
        });
        for (File xmlfile : files) {
            Root root = JaxbKit.unmarshal(xmlfile, Root.class);
            List<SqlGroup> sqlGroupList = root.sqlGroupList;

            for (SqlGroup group : sqlGroupList) {
                String name = group.name;
                if (name == null || name.trim().equals("")) {
                    name = xmlfile.getName();
                }
                for (SqlItem sqlItem : group.sqlItems) {
                    sqlMap.put(name + "." + sqlItem.id, sqlItem.value);
                }
            }

        }
        LOG.debug("sqlMap" + sqlMap);
    }


}
