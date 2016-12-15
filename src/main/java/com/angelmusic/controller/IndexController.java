package com.angelmusic.controller;

import com.angelmusic.base.BaseController;
import com.jfinal.ext.route.ControllerBind;

/**
 * Created by wangyong on 16-12-13.
 */
@ControllerBind(controllerKey = "/")
public class IndexController extends BaseController {


    public void index() {
        render("index.html");
    }

}
