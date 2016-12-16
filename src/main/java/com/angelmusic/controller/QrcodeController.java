package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.dao.model.Qrcode;
import com.angelmusic.service.QrcodeService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        final String clientId = getPara("clientId");

        if (StrKit.isBlank(clientId)) {
            error(HttpCode.CLIENTID_EMPTY, HttpCode.CLIENTID_EMPTY_WORD);
            return;
        }
        //客户端是否绑定过
        renderJson(QrcodeService.QRCODESERVICE.clientIsBinded(clientId));

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

        //绑定客户端
        renderJson(QrcodeService.QRCODESERVICE.bindClient(codeNo, clientId));

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
        final String uuid = getPara("qrcode");
        boolean saveResult = Qrcode.ME.saveQRcode(uuid);
        if (saveResult) {
            success();
            return;
        }
        error();
    }

}
