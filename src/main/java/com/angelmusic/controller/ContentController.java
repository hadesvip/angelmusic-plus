package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.ContentService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 内容
 * Created by wangyong on 16-12-22.
 */
@ControllerBind(controllerKey = "webapi/content")
public class ContentController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    /**
     * 主题内容列表
     */
    public void topicContentList() {
        LOGGER.info("[invoke topicContentList]");

        //主题，用户编号
        String topicId = getPara("topicId");
        String userPhone = getPara("userPhone");

        //参数校验
        if (StrKit.isBlank(topicId) || StrKit.isBlank(userPhone)) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.TOPIC_USER_EMPTY_WORD);
            return;
        }

        renderJson(ContentService.ME.getTopicContentList(Integer.parseInt(topicId), userPhone));

        LOGGER.info("[leave topicContentList]");
    }

    /**
     * 获取游戏视频
     */
    public void getVideoAndGame() {
        LOGGER.info("[invoke getVedio]");

        //主题编号，内容编号
        int topicId = getParaToInt("topicId");
        int contentId = getParaToInt("contentId");

        //获取视频和游戏
        renderJson(ContentService.ME.getVedioAndGame(topicId, contentId));

        LOGGER.info("[leave getVedio]");
    }

}
