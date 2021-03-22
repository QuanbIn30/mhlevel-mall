package com.mhlevel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhlevel.mapper.*;
import com.mhlevel.pojo.*;
import com.mhlevel.pojo.vo.CommentLevelCountsVO;
import com.mhlevel.pojo.vo.ItemCommentVO;
import com.mhlevel.pojo.vo.SearchItemsVO;
import com.mhlevel.service.ItemService;
import com.mhlevel.utils.DesensitizationUtil;
import com.mhlevel.utils.PageGridResult;
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
 * @date 2021-03-21
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    /**
     * 根据商品id查询商品详情
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        Items items = itemsMapper.selectByPrimaryKey(itemId);
        return items;
    }

    /**
     * 根据商品id查询商品的img列表
     * @param itemId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example itemsImgExample = new Example(ItemsImg.class);
        Example.Criteria criteria = itemsImgExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        List<ItemsImg> itemsImgs = itemsImgMapper.selectByExample(itemsImgExample);
        return itemsImgs;
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemsSpecExample = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemsSpecExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        List<ItemsSpec> itemsSpecs = itemsSpecMapper.selectByExample(itemsSpecExample);
        return itemsSpecs;
    }

    @Override
    public ItemsParam queryItemParamList(String itemId) {
        Example itemParamExample = new Example(ItemsParam.class);
        Example.Criteria criteria = itemParamExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        ItemsParam itemsParams = itemsParamMapper.selectOneByExample(itemParamExample);
        return itemsParams;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommonCounts(itemId, 1);
        Integer normalCounts = getCommonCounts(itemId, 2);
        Integer badCounts = getCommonCounts(itemId, 3);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setBadCounts(badCounts);
        commentLevelCountsVO.setTotalCounts(totalCounts);
        return commentLevelCountsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommonCounts(String itemId, Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null){
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }

    @Override
    public PageGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        PageHelper.startPage(page, pageSize);

        List<ItemCommentVO> itemCommentVOS = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVO vo : itemCommentVOS){
            vo.setNickName(DesensitizationUtil.commonDisplay(vo.getNickName()));
        }

        return setterPagedGrid(itemCommentVOS, page);
    }

    @Override
    public PageGridResult searchItems(String keyword, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> searchItemsVOList  = itemsMapperCustom.searchItems(map);
        return setterPagedGrid(searchItemsVOList, page);
    }

    private PageGridResult setterPagedGrid(List<?> list, Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PageGridResult grid = new PageGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
