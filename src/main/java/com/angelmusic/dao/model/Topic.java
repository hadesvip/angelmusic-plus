package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.ext.plugin.tablebind.TableBind;
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

    private List<Content> contentList;

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    @Override
    protected Map<String, Object> getAttrs() {
        Map<String, Object> attrs = super.getAttrs();
        attrs.put("contentList", contentList);
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
     * 获取主题信息
     *
     * @param order 主题顺序
     * @return
     */
    public Topic getTopicByOrder(int order) {
        return ME.findFirst(PlusSqlKit.sql("topic.getTopicByOrder"), order);
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
