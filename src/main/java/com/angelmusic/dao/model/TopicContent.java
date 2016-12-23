package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 主题内容
 * Created by wangyong on 16-12-23.
 */
@TableBind(pkName = "topic_content_id", tableName = "am_topic_content")
public class TopicContent extends Model<TopicContent> {

    public static final TopicContent ME = new TopicContent();


    /**
     * 获取主题内容
     *
     * @param topicId   主题编号
     * @param contentId 内容编号
     * @return
     */
    public TopicContent getTopicContent(int topicId, int contentId) {
        return findFirst(PlusSqlKit.sql("topicContent.getTopicContent"), topicId, contentId);
    }
}
