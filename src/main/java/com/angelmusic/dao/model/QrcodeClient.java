package com.angelmusic.dao.model;

import com.angelmusic.plugin.sql.PlusSqlKit;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 绑定二维码的客户端
 * Created by wangyong on 16-12-1.
 */
public class QrcodeClient extends Model<QrcodeClient> {

    public static final QrcodeClient ME = new QrcodeClient();

    /**
     * 根据客户端Id获取激活记录数
     */
    public int getClientCount(String clientId) {
        long count = Db.queryLong(PlusSqlKit.sql("qrcodeclient.getBindClientCount"), clientId);
        return (int) count;

    }

    /**
     * 获取绑定的二维码客户端个数
     *
     * @return
     */
    public int getBindClientCount(String codeNo) {
        return Db.queryInt(PlusSqlKit.sql("qrcodeclient.getBindQrodeClientCount"), codeNo);
    }

    /**
     * 绑定客户端
     *
     * @param clientId 客户端编号
     * @param codeId   二维码编号
     * @return
     */
    @Before(Tx.class)
    public boolean bindClientByCode(String clientId, int codeId) {
        return ME
                .set("client_id", clientId)
                .set("qrcode_id", codeId)
                .save();
    }


}
