package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 内容关卡
 * Created by wangyong on 16-12-23.
 */
@TableBind(pkName = "content_mission_id", tableName = "am_content_mission")
public class ContentMission extends Model<ContentMission> {

    public static final ContentMission ME = new ContentMission();

    /**
     * 内容关卡
     *
     * @param account 用户账号
     * @return 内容关卡
     */
    public ContentMission getContentMission(String account) {
        return ME.findFirst(PlusSqlKit.sql("contentMission.getContentMission"), account);
    }

    /**
     * @param account
     * @return
     */
    public ContentMission getContentMissionByAccount(String account) {
        return ME.findFirst(PlusSqlKit.sql("contentMission.getContentMissionByAccount"), account);
    }

    /**
     * 保存内容关卡
     *
     * @param account        用户账号
     * @param topicContentId 主题内容编号
     */
    public boolean saveContentMission(String account, int topicContentId) {
        return
                ME
                        .set("account", account)
                        .set("content_id", topicContentId)
                        .save();
    }

    /**
     * 更新内容关卡
     *
     * @param account 　用户账号
     * @return
     */
    public boolean updateContentMission(String account, int contentId) {
        return
                Db.update(PlusSqlKit.sql("contentMission.updateContentMission"), contentId, account) > 0;
    }
}
