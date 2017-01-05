package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.angelmusic.utils.Constant;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

import java.util.Arrays;
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

    /**
     * 查询给点主题的所有内容
     *
     * @param array
     * @return
     */
    public List<Content> getAllContentByTids(Integer[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT c.topic_id, c.content_id, c.name content_name, c.free content_free, c.course_name, c.course_video_path," +
                " c.game_name FROM am_content c WHERE c.topic_id in (");
        for (int i : array){
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        return ME.find(sb.toString(), array);
        //return ME.find(PlusSqlKit.sql("content.getAllContentByTids"), topicIds);
    }
}
