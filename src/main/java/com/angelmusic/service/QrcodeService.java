package com.angelmusic.service;

import com.angelmusic.dao.model.Qrcode;
import com.angelmusic.dao.model.QrcodeClient;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.angelmusic.utils.HttpResult;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 二维码服务层
 * Created by wangyong on 16-12-13.
 */
public class QrcodeService {

    public final static QrcodeService QRCODESERVICE = new QrcodeService();


    /**
     * 客户端是否绑定过
     *
     * @param clientId 客户端编号
     * @return
     */
    public HttpResult clientIsBinded(String clientId) {

        int count = QrcodeClient.ME.getClientCount(clientId);
        if (count > 0) {
            return new HttpResult(HttpCode.CLIENT_ACTIVATED, HttpCode.CLIENT_ACTIVATED_WORD);
        }
        return new HttpResult(HttpCode.SUCCESS, HttpCode.CLIENT_NOT_ACTIVATED_WORD);
    }


    /**
     * 绑定客户端
     *
     * @param codeNo   　二维码编号
     * @param clientId 　客户端编号
     * @return
     */
    @Before(Tx.class)
    public HttpResult bindClient(String codeNo, String clientId) {

        //二维码信息
        Qrcode qrCode = Qrcode.ME.getQRcode(codeNo);
        if (qrCode == null) {
            return new HttpResult(HttpCode.CODENO_NOT_EXISTS, HttpCode.CODENO_NOT_EXISTS_WORD);
        }

        //绑定客户端的个数
        int bindClientCount = QrcodeClient.ME.getBindClientCount(codeNo);

        //已达到绑定上限
        if (bindClientCount >= Constant.LIMIT_QRCODE_BINDCOUNT) {
            return new HttpResult(HttpCode.OVER_CODENO_BIND_CLIENTS, HttpCode.OVER_CODENO_BIND_CLIENTS_WORD);
        }

        //客户端绑定二维码
        boolean bindResult = QrcodeClient.ME.bindClientByCode(clientId, qrCode.getInt("qrcode_id"));

        //绑定成功
        if (bindResult) {
            return new HttpResult(HttpCode.SUCCESS, HttpCode.CODE_NO_BIND_CLIENT_SUCESS_WORD);
        }
        return new HttpResult(HttpCode.FAIL, HttpCode.FAIL_WORD);
    }


}
