package com.mhlevel.service;

import com.mhlevel.pojo.Users;
import com.mhlevel.pojo.bo.UserBO;

/**
 * 用户相关接口
 * @author quanbin
 * @date 2021-03-19
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * @param UserName
     * @return
     */
    boolean queryUsernameIsExist(String UserName);

    /**
     * 创建用户
     * @param userBO
     * @return
     */
    Users createUser(UserBO userBO);
}
