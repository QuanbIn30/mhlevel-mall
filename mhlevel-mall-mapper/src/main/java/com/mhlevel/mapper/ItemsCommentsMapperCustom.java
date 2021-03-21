package com.mhlevel.mapper;

import com.mhlevel.my.mapper.MyMapper;
import com.mhlevel.pojo.ItemsComments;
import com.mhlevel.pojo.vo.ItemCommentVO;
import com.mhlevel.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public List<MyCommentVO> queryMyComments(@Param("paramMap") Map<String, Object> map);
}