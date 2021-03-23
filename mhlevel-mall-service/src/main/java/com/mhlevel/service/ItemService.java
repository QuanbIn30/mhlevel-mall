package com.mhlevel.service;

import com.mhlevel.pojo.Items;
import com.mhlevel.pojo.ItemsImg;
import com.mhlevel.pojo.ItemsParam;
import com.mhlevel.pojo.ItemsSpec;
import com.mhlevel.pojo.vo.CommentLevelCountsVO;
import com.mhlevel.pojo.vo.ShopcartVO;
import com.mhlevel.utils.PageGridResult;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-21
 */
public interface ItemService {

    /**
     * 根据商品id查询详情
     * @param itemId
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品的图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);


    /**
     * 根据商品id查询商品的规格列表
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据善品id查询商品的参数信息
     * @param itemId
     * @return
     */
    ItemsParam queryItemParamList(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的评价（分页）
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    PageGridResult queryPagedComments(String itemId,
                                      Integer level,
                                      Integer page,
                                      Integer pageSize);

    /**
     * 搜索商品列表
     * @param keyword
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PageGridResult searchItems(String keyword,
                               String sort,
                               Integer page,
                               Integer pageSize);


    /**
     * 根据分类id搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PageGridResult searchItems(Integer catId,
                              String sort,
                              Integer page,
                              Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中的商品数据（用于刷新渲染购物车中的商品数据）
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);
}
