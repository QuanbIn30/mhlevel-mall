package com.mhlevel.mapper;

import com.mhlevel.pojo.vo.ItemCommentVO;
import com.mhlevel.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

public interface ItemsMapperCustom{

    List<ItemCommentVO> queryItemComments(@Param("paramMap") Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("paramMap") Map<String, Object> map);

    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramMap") Map<String, Object> map);


}