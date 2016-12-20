package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 课程
 * Created by wangyong on 16-12-19.
 */
public class CourseController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    /**
     * 获取用户课程:<=当前月并且已订阅的课程
     */
    public void getUserCourses() {
        LOGGER.info("[invoke getUserCourses]");
        String userId = getPara("userId");

    }


    /**
     * 订阅课程
     */
    public void subscribeCourse() {
        LOGGER.info("[invoke subscribeCourse]");
        String userId = getPara("userId");
        String[] courseIds = getParaValues("courseIds");

    }


}
