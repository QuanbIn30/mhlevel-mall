package com.mhlevel.controller;

import com.mhlevel.pojo.Items;
import com.mhlevel.pojo.ItemsImg;
import com.mhlevel.pojo.ItemsParam;
import com.mhlevel.pojo.ItemsSpec;
import com.mhlevel.pojo.vo.CommentLevelCountsVO;
import com.mhlevel.pojo.vo.ItemInfoVo;
import com.mhlevel.service.ItemService;
import com.mhlevel.utils.MHLEVELJSONResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-21
 */
@RestController
public class ItemsController {

    @Autowired
    private ItemService itemService;


    @ApiOperation(value="查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public MHLEVELJSONResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId){

        if(StringUtils.isBlank(itemId)){
            return MHLEVELJSONResult.errorMsg(null);
        }

        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        ItemsParam itemsParam = itemService.queryItemParamList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);

        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItems(items);
        itemInfoVo.setItemsImgList(itemsImgs);
        itemInfoVo.setItemsSpecList(itemsSpecList);
        itemInfoVo.setItemsParam(itemsParam);

        return MHLEVELJSONResult.ok(itemInfoVo);
    }

    @ApiOperation(value="查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public MHLEVELJSONResult commontLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId){
        if(itemId == null){
            return MHLEVELJSONResult.errorMsg(null);
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return MHLEVELJSONResult.ok(commentLevelCountsVO);
    }
}
