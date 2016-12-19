package com.angelmusic.dao.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 用户信息
 * Created by wangyong on 16-12-19.
 */
@TableBind(pkName = "user_id", tableName = "am_user")
public class User extends Model<User> {

    public static final User ME = new User();

}
