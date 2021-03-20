package com.mhlevel.service.impl;

import com.mhlevel.mapper.CategoryMapper;
import com.mhlevel.mapper.CategoryMapperCustom;
import com.mhlevel.pojo.Category;
import com.mhlevel.pojo.vo.CategoryVO;
import com.mhlevel.pojo.vo.NewItemsVO;
import com.mhlevel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quanbin
 * @date 2021-03-20
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    /**
     * 获取一级分类
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example catsExample = new Example(Category.class);
        Example.Criteria criteria = catsExample.createCriteria();
        criteria.andEqualTo("type",1);
        List<Category> categories = categoryMapper.selectByExample(catsExample);
        return categories;
    }

    /**
     * 获取商品子分类
     * @param rootCatId
     * @return
     */
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        List<CategoryVO> categories = categoryMapperCustom.getSubCatList(rootCatId);
        return categories;
    }

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {

        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        List<NewItemsVO> sixNewItemsLazy = categoryMapperCustom.getSixNewItemsLazy(map);
        return sixNewItemsLazy;
    }
}
