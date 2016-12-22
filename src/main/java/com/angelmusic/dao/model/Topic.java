package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.angelmusic.utils.Constant;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 主题
 * Created by wangyong on 16-12-20.
 */
@TableBind(pkName = "topic_id", tableName = "am_topic")
public class Topic extends Model<Topic> {

    public final static Topic ME = new Topic();

    //当前用户主题是否锁住1锁住2未锁
    private int lock = Constant.TOPIC_LOCKED;

    public int getLock() {
        return lock;
    }

    public Topic setLock(int lock) {
        this.lock = lock;
        return this;
    }

    /**
     * 获取所有的主题信息
     *
     * @return
     */
    public List<Topic> topics() {
        return ME.find(PlusSqlKit.sql("topic.allTopic"));
    }

    /**
     * 获取不免费的主题个数
     *
     * @return
     */
    public int getNotFreeTopicCount() {
        return Db.queryInt(PlusSqlKit.sql("getNotFreeTopicCount"));
    }

    /**
     * 获取主题信息
     *
     * @param topicId 主题编号
     * @return
     */
    public Topic getTopic(int topicId) {
        return ME.findById(topicId);
    }

}
