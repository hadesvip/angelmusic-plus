package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.dao.model.Qrcode;
import com.angelmusic.dao.model.QrcodeClient;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.StrKit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 二维码
 * Created by wangyong on 16-11-30.
 */
@ControllerBind(controllerKey = "webapi/qrcode", viewPath = "/qrcode")
public class QrcodeController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QrcodeController.class);

    /**
     * 客户端是否激活过
     */
    public void clientIsActivated() {
        LOGGER.info("[invoke clientIsActivated...]");

        String clientId = getPara("clientId");
        if (StrKit.isBlank(clientId)) {
            error(HttpCode.CLIENTID_EMPTY, HttpCode.CLIENTID_EMPTY_WORD);
            return;
        }
        int count = QrcodeClient.ME.getClientCount(clientId);
        if (count > 0) {
            success(HttpCode.CLIENT_ACTIVATED, HttpCode.CLIENT_ACTIVATED_WORD);
            return;
        }
        success(HttpCode.CLIENT_NOT_ACTIVATED_WORD);

        LOGGER.info("[leave clientIsActivated...]");
    }

    /**
     * 根据二维码绑定客户端
     */
    public void bindClientByQRcode() {
        LOGGER.info("[invoke activateClient...]");

        String method = getRequest().getMethod();

        if (method.equalsIgnoreCase("get")) {
            error("此接口只支持post请求");
            return;
        }

        final String clientId = getPara("clientId");
        final String codeNo = getPara("codeNo");

        // 参数校验
        if (StrKit.isBlank(clientId)) {
            error(HttpCode.CLIENTID_EMPTY, HttpCode.CLIENTID_EMPTY_WORD);
            return;
        }

        if (StrKit.isBlank(codeNo)) {
            error(HttpCode.CODE_NO_EMPTY, HttpCode.CODE_NO_EMPTY_WORD);
            return;
        }

        //二维码信息
        Qrcode qrCode = Qrcode.ME.getQRcode(codeNo);
        if (qrCode == null) {
            error(HttpCode.CODENO_NOT_EXISTS, HttpCode.CODENO_NOT_EXISTS_WORD);
            return;
        }

        //绑定客户端的个数
        int bindClientCount = QrcodeClient.ME.getBindClientCount(codeNo);

        //已达到绑定上限
        if (bindClientCount >= Constant.LIMIT_QRCODE_BINDCOUNT) {
            error(HttpCode.OVER_CODENO_BIND_CLIENTS, HttpCode.OVER_CODENO_BIND_CLIENTS_WORD);
            return;
        }

        //客户端绑定二维码
        boolean bindResult = QrcodeClient.ME.bindClientByCode(clientId, qrCode.getInt("qrcode_id"));

        if (bindResult) {
            success(HttpCode.CODE_NO_BIND_CLIENT_SUCESS_WORD);
        }

        LOGGER.info("[leave activateClient...]");
    }

    /**
     * 二维码打印
     */
    public void print() {
        render("qrcode_print.html");
    }

    /**
     * 保存打印的二维码编号
     */
    public void saveQrcode() {
        String uuid = getPara("qrcode");
        boolean saveResult = Qrcode.ME.saveQRcode(uuid);
        if (saveResult) {
            success();
            return;
        }
        error();
    }

}
