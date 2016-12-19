package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.jfinal.ext.route.ControllerBind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangyong on 16-12-13.
 */
@ControllerBind(controllerKey = "/")
public class IndexController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    public void index() {
        render("index.html");
    }

}
