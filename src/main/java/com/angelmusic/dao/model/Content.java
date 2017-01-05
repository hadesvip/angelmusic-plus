package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.angelmusic.utils.Constant;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;
import java.util.Map;

/**
 * 内容
 * Created by wangyong on 16-12-22.
 */
@TableBind(pkName = "content_id", tableName = "am_content")
public class Content extends Model<Content> {

    public static final Content ME = new Content();

    /**
     * 获取上一个content
     *
     * @return
     */
    public Content getPrevContent(int topicId, int contentId) {

        return findFirst(PlusSqlKit.sql("content.prevContent"), contentId, topicId);
    }


    /**
     * @param topicId 主题
     * @return
     */
    public List<Content> getTopicContentList(int topicId) {
        return ME.find(PlusSqlKit.sql("content.getTopicContentList"), topicId);
    }

    /**
     * 获取内容
     *
     * @param contentId 内容编号
     * @return
     */
    public Content getNextContent(int contentId) {
        return ME.findFirst(PlusSqlKit.sql("content.getNextContent"), contentId);
    }


}
