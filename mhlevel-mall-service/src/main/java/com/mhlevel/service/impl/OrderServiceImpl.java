package com.mhlevel.service.impl;

import com.mhlevel.enums.OrderStatusEnum;
import com.mhlevel.enums.YesOrNo;
import com.mhlevel.mapper.OrderItemsMapper;
import com.mhlevel.mapper.OrderStatusMapper;
import com.mhlevel.mapper.OrdersMapper;
import com.mhlevel.mapper.UserAddressMapper;
import com.mhlevel.pojo.*;
import com.mhlevel.pojo.bo.AddressBO;
import com.mhlevel.pojo.bo.SubmitOrderBO;
import com.mhlevel.service.AddressService;
import com.mhlevel.service.ItemService;
import com.mhlevel.service.OrderService;
import com.mhlevel.utils.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 订单Service
 * @author quanbin
 * @date 2021-03-29
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    /**
     * 创建订单相关信息
     * @param submitOrderBO
     */
    @Override
    public String createOrder(SubmitOrderBO submitOrderBO) {
        // 随机生成订单id
        String id = sid.nextShort();
        String userId = submitOrderBO.getUserId();
        String itemSpecsIds = submitOrderBO.getItemSpecIds();
        String addressId = submitOrderBO.getAddressId();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 邮费设置为0
        Integer postAmount = 0;
        // 取出当前订单的地址信息
        UserAddress userAddress = addressService.QueryUserAddress(userId, addressId);

        // 1.新订单保存数据
        Orders orders = new Orders();
        orders.setId(id);
        orders.setUserId(userId);
        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverAddress(userAddress.getProvince()
                                    + userAddress.getCity()
                                    + userAddress.getDistrict()
                                    + userAddress.getDetail());
        orders.setPostAmount(postAmount);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setIsComment(YesOrNo.NO.getType());
        orders.setIsDelete(YesOrNo.NO.getType());
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());

        // 2. 循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecsIdArr = itemSpecsIds.split(",");
        Integer totalAmount = 0;
        Integer realPayAmount = 0;
        for (String spec : itemSpecsIdArr){
            // 随机生成订单商品表id
            String orderItemId = sid.nextShort();
            // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = 1;
            // 获取商品规格信息
            ItemsSpec itemsSpec = itemService.queryItemSpecById(spec);
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            // 获取商品图片信息
            String itemId = itemsSpec.getItemId();
            Items items = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            OrderItems orderItems = new OrderItems();
            orderItems.setId(orderItemId);
            orderItems.setOrderId(id);
            orderItems.setItemId(itemId);
            orderItems.setItemName(items.getItemName());
            orderItems.setItemImg(imgUrl);
            orderItems.setBuyCounts(buyCounts);
            orderItems.setItemSpecId(spec);
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);

            // 用户创建订单以后，规格表中需要扩库存
            itemService.decreaseItemSpecStock(spec, buyCounts);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(orders);

        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(id);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.getType());
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        return id;
    }

    /**
     * 关闭超时未支付订单
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {
        //查询所有未付款订单，判断时间是否超过一天，超时则关闭交易
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.getType());
        List<OrderStatus> orderStatusList = orderStatusMapper.select(orderStatus);
        for (OrderStatus orderStat : orderStatusList){
            //获取创建订单的时间
            Date createdTime = orderStat.getCreatedTime();
            //和当前时间比对
            int days = DateUtil.daysBetween(createdTime, new Date());
            if (days > 1){
                doCloseOrder(orderStat.getOrderId());
            }
        }
    }

    /**
     * 根据订单id关闭当前订单
     * @param orderId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId){
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.getType());
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }
}
