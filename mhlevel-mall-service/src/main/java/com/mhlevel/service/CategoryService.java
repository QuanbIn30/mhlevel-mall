package com.mhlevel.service;

import com.mhlevel.pojo.Category;
import com.mhlevel.pojo.vo.CategoryVO;
import com.mhlevel.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-20
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类信息
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新的商品数据
     * @param rootCatId
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
