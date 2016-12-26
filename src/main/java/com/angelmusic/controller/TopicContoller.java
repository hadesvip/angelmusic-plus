package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.TopicService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主题
 * Created by wangyong on 16-12-20.
 */
@ControllerBind(controllerKey = "/webapi/topic")
public class TopicContoller extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(TopicContoller.class);

    /**
     * 加载主题列表
     */
    public void loadTopicList() {
        LOGGER.info("[invoke loadTopicList]");

        String userPhone = getPara("userPhone");

        //用户编号为空
        if (StrKit.isBlank(userPhone)) {
            error(HttpCode.USER_PHONE_EMPTY, HttpCode.USER_PHONE_EMPTY_WORD);
            return;
        }

        //加载用户主题
        renderJson(TopicService.TOPICSERVICE.loadTopicList(userPhone));

        LOGGER.info("[leave loadTopicList]");
    }


}
