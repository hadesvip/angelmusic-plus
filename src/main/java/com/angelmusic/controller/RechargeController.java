package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.jfinal.ext.route.ControllerBind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 充值
 * Created by wangyong on 16-12-19.
 */
@ControllerBind(controllerKey = "webapi/recharge/")
public class RechargeController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(RechargeController.class);


    /**
     * 上传充值记录
     */
    public void uploadRechargeRecord() {

    }


}
