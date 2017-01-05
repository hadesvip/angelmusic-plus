package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 大礼包
 * Created by wangyong on 16-12-21.
 */
@TableBind(pkName = "gift_pack_id", tableName = "am_gift_pack")
public class GiftPack extends Model<GiftPack> {

    public static final GiftPack ME = new GiftPack();

    /**
     * 根据大礼包名获取大礼包详细信息
     *
     * @param name 大礼包名
     * @return
     */
    public GiftPack getGiftPackByName(String name) {
        return ME.findFirst(PlusSqlKit.sql("giftPack.getGiftPackByName"), name);
    }

    public List<GiftPack> getAllGiftPack() {
        return ME.find(PlusSqlKit.sql("giftPack.getGiftPack"));
    }

    /**
     * 根据主键获取大礼包
     *
     * @param id
     * @return
     */
    public GiftPack getGiftPackById(String id) {
        return findById(id);
    }
}
