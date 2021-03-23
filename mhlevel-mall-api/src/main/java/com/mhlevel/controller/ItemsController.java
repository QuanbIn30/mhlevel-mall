package com.mhlevel.controller;

import com.mhlevel.pojo.Items;
import com.mhlevel.pojo.ItemsImg;
import com.mhlevel.pojo.ItemsParam;
import com.mhlevel.pojo.ItemsSpec;
import com.mhlevel.pojo.vo.CommentLevelCountsVO;
import com.mhlevel.pojo.vo.ItemInfoVo;
import com.mhlevel.pojo.vo.SearchItemsVO;
import com.mhlevel.pojo.vo.ShopcartVO;
import com.mhlevel.service.ItemService;
import com.mhlevel.utils.MHLEVELJSONResult;
import com.mhlevel.utils.PageGridResult;
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
public class ItemsController extends BaseController{

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

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public MHLEVELJSONResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize){
        if(StringUtils.isBlank(itemId)){
            return MHLEVELJSONResult.errorMsg(null);
        }
        if (page == null){
            page = 1;
        }

        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PageGridResult grid = itemService.queryPagedComments(itemId,level,page,pageSize);
        return MHLEVELJSONResult.ok(grid);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public MHLEVELJSONResult search(
            @ApiParam(name = "keyword", value = "关键字", required = true)
            @RequestParam String keyword,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
    ){
        if (StringUtils.isBlank(keyword)){
            return MHLEVELJSONResult.errorMsg(null);
        }
        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }
        PageGridResult result = itemService.searchItems(keyword, sort, page, pageSize);

        return MHLEVELJSONResult.ok(result);
    }

    @ApiOperation(value = "通过分类id搜索商品列表", notes = "通过分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catitems")
    public MHLEVELJSONResult catItems(
            @ApiParam(name = "catId", value = "分类Id", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页查询总显示记录行数", required = false)
            @RequestParam Integer pageSize
    ){
        if (catId == null){
            return MHLEVELJSONResult.errorMsg(null);
        }

        if (page == null){
            page = 1;
        }

        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PageGridResult gridResult = itemService.searchItems(catId, sort, page, pageSize);
        return MHLEVELJSONResult.ok(gridResult);
    }

    @ApiOperation(value = "刷新购物车中的商品信息", notes = "刷新购物车中的商品信息", httpMethod = "GET")
    @GetMapping("/refresh")
    public MHLEVELJSONResult refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接规格ids", required = true, example = "1001,1003,1005")
            @RequestParam String itemSpecIds
    ){
        if(StringUtils.isBlank(itemSpecIds)){
            return MHLEVELJSONResult.ok();
        }
        List<ShopcartVO> shopcartVOList = itemService.queryItemsBySpecIds(itemSpecIds);
        return MHLEVELJSONResult.ok(shopcartVOList);
    }
}
