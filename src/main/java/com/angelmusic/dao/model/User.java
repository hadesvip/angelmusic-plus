package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;

/**
 * 用户信息
 * Created by wangyong on 16-12-19.
 */
@TableBind(pkName = "user_id", tableName = "am_user")
public class User extends Model<User> {

    public static final User ME = new User();

    /**
     * 用户购买了多少个月
     */
    private int buyMonths;

    public int getBuyMonths() {
        return buyMonths;
    }

    public User setBuyMonths(int buyMonths) {
        this.buyMonths = buyMonths;
        return this;
    }

    /**
     * 上传用户信息
     *
     * @param userPhone 用户手机号
     * @return
     */
    public boolean saveUser(String userPhone) {

        return
                ME
                        .set("user_name", "")
                        .set("user_phone", userPhone)
                        .set("total_recharge", 0)
                        .set("create_date", new Date())
                        .save();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public User getUserByPhone(String phone) {
        return ME.findFirst(PlusSqlKit.sql("user.getUserByPhone"), phone);
    }
}
