package com.mhlevel.service.impl;

import com.mhlevel.mapper.CarouselMapper;
import com.mhlevel.pojo.Carousel;
import com.mhlevel.service.CarouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-20
 */
@Service
public class CarouseServiceImpl implements CarouseService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example carouselExample = new Example(Carousel.class);
        carouselExample.orderBy("sort").desc();
        Example.Criteria criteria = carouselExample.createCriteria();
        criteria.andEqualTo("isShow", isShow);
        List<Carousel> carousels = carouselMapper.selectByExample(carouselExample);
        return carousels;
    }
}
