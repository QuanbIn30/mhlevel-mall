package com.mhlevel.service;

import com.mhlevel.pojo.UserAddress;
import com.mhlevel.pojo.bo.AddressBO;

import java.util.List;

/**
 * 收货地址相关接口
 * @author quanbin
 * @date 2021-03-24
 */
public interface AddressService {

    /**
     * 根据用户id查询用户的收货地址列表
     * @param userId
     * @return
     */
    List<UserAddress> QueryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     */
    void AddNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     * @param addressBO
     */
    void UpdateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id和地址id，删除用户的对应地址信息
     * @param userId
     * @param addressId
     */
    void DeleteUserAddress(String userId, String addressId);

    /**
     * 修改默认地址
     * @param userId
     * @param addressId
     */
    void UpdateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id，查询用户的具体的地址信息
     * @param userId
     * @param addressId
     * @return
     */
    UserAddress QueryUserAddress(String userId, String addressId);
}
