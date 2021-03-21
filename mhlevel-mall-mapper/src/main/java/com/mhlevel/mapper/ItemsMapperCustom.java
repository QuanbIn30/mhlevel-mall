package com.mhlevel.mapper;

import com.mhlevel.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

public interface ItemsMapperCustom{

    List<ItemCommentVO> queryItemComments(@Param("paramMap") Map<String, Object> map);
}