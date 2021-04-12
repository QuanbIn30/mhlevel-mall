package com.mhlevel.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.mhlevel.enums.YesOrNo;
import com.mhlevel.mapper.ItemsCommentsMapperCustom;
import com.mhlevel.mapper.OrderItemsMapper;
import com.mhlevel.mapper.OrderStatusMapper;
import com.mhlevel.mapper.OrdersMapper;
import com.mhlevel.pojo.OrderItems;
import com.mhlevel.pojo.OrderStatus;
import com.mhlevel.pojo.Orders;
import com.mhlevel.pojo.bo.center.OrderItemsCommentBO;
import com.mhlevel.pojo.vo.MyCommentVO;
import com.mhlevel.service.center.MyCommentsService;
import com.mhlevel.utils.PageGridResult;
import com.mhlevel.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quanbin
 * @date 2021-04-11
 */
public class MyCommentServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        List<OrderItems> select = orderItemsMapper.select(query);
        return select;
    }

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {

        // 1.保存评价涉及到的表 -- item_comments
        for (OrderItemsCommentBO oic : commentList){
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2.修改订单表以评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.getType());
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3.修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    /**
     * 查询我的评价（分页）
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);
        return setterPagedGrid(list, page);
    }
}
