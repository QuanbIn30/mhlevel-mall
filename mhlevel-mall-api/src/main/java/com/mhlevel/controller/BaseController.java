package com.mhlevel.controller;

import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * @author quanbin
 * @date 2021-03-22
 */
@Controller
public class BaseController {

    public static final Integer COMMON_PAGE_SIZE = 10;

    public static final Integer PAGE_SIZE = 20;

    public static final String FOODIE_SHOPCART =  "shopcart";

    //用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "Users" +
                                                          File.separator + "Shared" +
                                                          File.separator + "Relocated" +
                                                          File.separator + "Items" +
                                                          File.separator + "Security" +
                                                          File.separator + "MyDocument" +
                                                          File.separator + "网盘" +
                                                          File.separator + "mk" +
                                                          File.separator + "Architecture" +
                                                          File.separator + "WorkSpace" +
                                                          File.separator + "foodie";
}
