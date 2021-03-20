package com.mhlevel.service;

import com.mhlevel.pojo.Carousel;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-20
 */
public interface CarouseService {

    /**
     * 查询所有轮播图
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
