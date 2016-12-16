package com.angelmusic.dao.model;


import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.aop.Before;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 二维码
 * Created by wangyong on 16-11-30.
 */
@TableBind(pkName = "qrcode_id", tableName = "am_qrcode")
public class Qrcode extends Model<Qrcode> {

    public static final Qrcode ME = new Qrcode();


    /**
     * 获取二维码个数
     *
     * @return
     */
    public Qrcode getQRcode(String codeNo) {
        return findFirst(PlusSqlKit.sql("qrcode.getQRcodeBycodeNo"), codeNo);
    }

    /**
     * 保存二维码
     *
     * @param qrcode 二维码
     * @return
     */
    @Before(Tx.class)
    public boolean saveQRcode(String qrcode) {
        ME.set("qrcode_id", null);
        return ME.set("qrcode_no", qrcode).save();
    }

}
