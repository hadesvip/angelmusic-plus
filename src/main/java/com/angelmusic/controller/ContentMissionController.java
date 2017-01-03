package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.ContentMissionService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 内容关卡
 * Created by wangyong on 16-12-23.
 */
@ControllerBind(controllerKey = "/webapi/contentMission")
public class ContentMissionController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(ContentMissionController.class);


    /**
     * 更新内容关卡
     */
    public void updateContentMission() {
        LOGGER.info("[invoke updateContentMission]");

        //用户手机号，主题内容编号，类型
        String account = getPara("account");
        int contentId = getParaToInt("contentId");

        //参数校验
        if (StrKit.isBlank(account)) {
            renderJson(Ret.create("code", HttpCode.USER_ACCOUNT_EMPTY).getData());
            return;
        }

        //更新用户关卡
        renderJson(ContentMissionService.ME.updateContentMission(account, contentId));

        LOGGER.info("[leave updateContentMission]");
    }

}
