package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.angelmusic.dao.model.Topic;
import com.angelmusic.service.TopicService;
import com.angelmusic.utils.HttpCode;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.Ret;
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

        String account = getPara("account");

        //用户账号为空
        if (StrKit.isBlank(account)) {
            renderJson(Ret.create("code", HttpCode.USER_ACCOUNT_EMPTY).getData());
            return;
        }

        //加载用户主题
        Ret data = TopicService.ME.getData(account);

        renderJson(data.getData());
        LOGGER.info("[leave loadTopicList]");
        return;
    }
}
