package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.angelmusic.utils.Constant;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;
import java.util.Map;

/**
 * 主题
 * Created by wangyong on 16-12-20.
 */
@TableBind(pkName = "topic_id", tableName = "am_topic")
public class Topic extends Model<Topic> {

    public final static Topic ME = new Topic();

    //当前用户主题是否锁住1锁住2未锁
    private int lock = Constant.LOCKED;

    public int getLock() {
        return lock;
    }

    public int setLock(int lock) {
        this.lock = lock;
        return this.lock;
    }

    @Override
    protected Map<String, Object> getAttrs() {
        Map<String, Object> attrs = super.getAttrs();
        attrs.put("lock", lock);

        return attrs;
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
    public Topic getTopic(String topicId) {
        return ME.findById(topicId);
    }

    /**
     * 获取试看和收费的主题列表
     *
     * @return
     */
    public List<Topic> getTopicList() {
        return find(PlusSqlKit.sql("topic.getToppicList"));
    }


}
