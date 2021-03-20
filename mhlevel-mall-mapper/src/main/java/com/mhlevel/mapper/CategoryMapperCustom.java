package com.mhlevel.mapper;

import com.mhlevel.pojo.vo.CategoryVO;
import com.mhlevel.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author quanbin
 * @date 2021-03-20
 */
public interface CategoryMapperCustom {

    List<CategoryVO> getSubCatList(Integer rootCatId);

    List<NewItemsVO> getSixNewItemsLazy(@Param(value = "paramsMap") Map<String, Object> map);
}
