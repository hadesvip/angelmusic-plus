package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.ContentMissionService;
import com.angelmusic.utils.Constant;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
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

        //用户手机号，主题内容编号，游戏关卡，课程关卡
        String userPhone = getPara("userPhone");
        int topicContentId = getParaToInt("topicContentId");
        int gameMission = getParaToInt("gameMission");
        int courseMission = getParaToInt("courseMission");

        //参数校验
        if (StrKit.isBlank(userPhone)) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.MOBILE_EMPTY_WORD);
            return;
        }

        //关卡状态校验
        if ((gameMission < Constant.MISSION_UNCOMPLETE || gameMission < Constant.MISSION_COMPLETE)
                && (courseMission < Constant.MISSION_UNCOMPLETE || courseMission < Constant.MISSION_COMPLETE)) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.MISSION_STATUS_OVER_WORD);
            return;
        }

        //更新用户关卡
        renderJson(ContentMissionService.ME.updateContentMission(userPhone, topicContentId, gameMission, courseMission));

        LOGGER.info("[leave updateContentMission]");
    }

}
