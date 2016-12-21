package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.service.TopicService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主题
 * Created by wangyong on 16-12-20.
 */
public class TopicContoller extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(TopicContoller.class);

    /**
     * 加载主题列表
     */
    public void loadTopicList() {
        LOGGER.info("[invoke loadTopicList]");

        String userId = getPara("userId");

        //用户编号为空
        if (StrKit.isBlank(userId)) {
            error(HttpCode.USERID_EMPTY, HttpCode.USERID_EMPTY_WORD);
            return;
        }

        //加载用户主题
        renderJson(TopicService.TOPICSERVICE.loadTopicList(userId));

        LOGGER.info("[leave loadTopicList]");
    }


}
