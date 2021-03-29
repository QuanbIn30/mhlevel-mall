package com.mhlevel.service.impl;

import com.mhlevel.enums.YesOrNo;
import com.mhlevel.mapper.UserAddressMapper;
import com.mhlevel.pojo.UserAddress;
import com.mhlevel.pojo.bo.AddressBO;
import com.mhlevel.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author quanbin
 * @date 2021-03-24
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void AddNewUserAddress(AddressBO addressBO) {
        //1. 判断当前用户是否存在地址，如果没有，则新增地址为默认地址
        Integer isDefault = 0;
        List<UserAddress> list = this.QueryAll(addressBO.getUserId());
        if (list == null || list.size() == 0 || list.isEmpty()){
            isDefault = 1;
        }

        String addressId = sid.nextShort();
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(userAddress);
    }


    /**
     * 根据用户id查询用户的收货地址列表
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> QueryAll(String userId){
        UserAddress ua = new UserAddress();
        ua.setUserId(userId);
        return userAddressMapper.select(ua);
    }

    /**
     * 用户修改地址
     * @param addressBO
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void UpdateUserAddress(AddressBO addressBO) {
        String userId = addressBO.getUserId();
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setUserId(userId);
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    /**
     * 根据用户id和地址id，删除用户的对应地址信息
     * @param userId
     * @param addressId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void DeleteUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        userAddressMapper.delete(userAddress);
    }

    /**
     * 修改默认地址
     * @param userId
     * @param addressId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void UpdateUserAddressToBeDefault(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setIsDefault(YesOrNo.YES.getType());
        List<UserAddress> list = userAddressMapper.select(userAddress);
        for (UserAddress ua : list){
            ua.setIsDefault(YesOrNo.NO.getType());
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }
        UserAddress toBeDefaultAddress = new UserAddress();
        toBeDefaultAddress.setId(addressId);
        toBeDefaultAddress.setUserId(userId);
        toBeDefaultAddress.setIsDefault(YesOrNo.YES.getType());
        userAddressMapper.updateByPrimaryKeySelective(toBeDefaultAddress);
    }

    /**
     * 根据用户id和地址id，查询用户的具体的地址信息
     * @param userId
     * @param addressId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress QueryUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        return userAddressMapper.selectOne(userAddress);
    }
}
