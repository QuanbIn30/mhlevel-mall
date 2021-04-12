package com.mhlevel.service.center;

import com.mhlevel.pojo.OrderItems;
import com.mhlevel.pojo.bo.center.OrderItemsCommentBO;
import com.mhlevel.utils.PageGridResult;
import com.mhlevel.utils.PagedGridResult;

import java.util.List;

/**
 * @author quanbin
 * @date 2021-04-11
 */
public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);


    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 查询我的评价（分页）
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyComments(String userId, Integer page , Integer pageSize);
}
