package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 用户主题信息
 * Created by wangyong on 17-1-4.
 */
public class UserTopic extends Model<UserTopic> {

    public static final UserTopic ME = new UserTopic();


    public UserTopic getUserTopic(String account) {
        return findFirst(PlusSqlKit.sql("userTopic.getUserTopic"), account);
    }

    /**
     * 保存用户主题信息
     *
     * @param account    账号
     * @param topicCount 主题数
     * @return
     */
    public boolean saveUserTopic(String account, int topicCount) {

        return
                ME
                        .set("account", account)
                        .set("topic_count", topicCount)
                        .save();
    }


    /**
     * 更新用户主题数
     */
    public boolean updateUserTopic(int id, int topicCount) {
        return
                Db.update(PlusSqlKit.sql("userTopic.updateUserTopic"), topicCount, id) > 0;
    }

}
