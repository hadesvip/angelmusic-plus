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
        String userId = getPara("userId");

        //参数校验
        if (StrKit.isBlank(topicId) || StrKit.isBlank(userId)) {
            error(HttpCode.PARAMS_INVAILD, HttpCode.TOPIC_USER_EMPTY_WORD);
            return;
        }

        renderJson(ContentService.ME.getTopicContentList(topicId,userId));


        LOGGER.info("[leave topicContentList]");
    }
}
