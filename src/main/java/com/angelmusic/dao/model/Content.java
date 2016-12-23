package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.angelmusic.utils.Constant;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 内容
 * Created by wangyong on 16-12-22.
 */
@TableBind(pkName = "content_id", tableName = "am_content")
public class Content extends Model<Content> {

    public static final Content ME = new Content();

    /**
     * 是否锁住
     */
    private int lock = Constant.LOCKED;

    public int getLock() {
        return lock;
    }

    public Content setLock(int lock) {
        this.lock = lock;
        return this;
    }

    /**
     * @param topicId 主题
     * @return
     */
    public List<Content> getTopicContentList(String topicId) {
        return ME.find(PlusSqlKit.sql("content.getTopicContentList"), topicId);
    }



}
