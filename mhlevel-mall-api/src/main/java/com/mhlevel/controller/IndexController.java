package com.mhlevel.controller;

import com.mhlevel.enums.YesOrNo;
import com.mhlevel.pojo.Carousel;
import com.mhlevel.pojo.Category;
import com.mhlevel.pojo.vo.CategoryVO;
import com.mhlevel.pojo.vo.NewItemsVO;
import com.mhlevel.service.CarouseService;
import com.mhlevel.service.CategoryService;
import com.mhlevel.utils.MHLEVELJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-20
 */
@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouseService carouseService;

    @Autowired
    private CategoryService categoryService;



    /**
     * 获取首页轮播图列表
     * @return
     */
    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public MHLEVELJSONResult carousel(){
        List<Carousel> carouselList = carouseService.queryAll(YesOrNo.YES.getType());
        return MHLEVELJSONResult.ok(carouselList);
    }

    /**
     * 获取首页一级分类
     * @return
     */
    @ApiOperation(value = "获取首页一级分类", notes = "获取首页一级分类", httpMethod = "GET")
    @GetMapping("/cats")
    public MHLEVELJSONResult cats(){
        List<Category> cats = categoryService.queryAllRootLevelCat();
        return MHLEVELJSONResult.ok(cats);
    }

    /**
     * 获取商品子分类
     * @param rootCatId
     * @return
     */
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public MHLEVELJSONResult subCatsList(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId){
        if (rootCatId == null){
            return MHLEVELJSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
        if(subCatList == null) {
            System.out.println("subCatList is null");
        }
        return MHLEVELJSONResult.ok(subCatList);
    }


    @ApiOperation(value = "查询每个一级分类下的最新的六条数据", notes = "查询每个一级分类下的最新的六条数据作用当前推荐商品展示", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public MHLEVELJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId){
        if(rootCatId == null){
            return MHLEVELJSONResult.errorMsg("当前分类不存在");
        }

        List<NewItemsVO> sixNewItemsLazy = categoryService.getSixNewItemsLazy(rootCatId);
        return MHLEVELJSONResult.ok(sixNewItemsLazy);
    }
}
