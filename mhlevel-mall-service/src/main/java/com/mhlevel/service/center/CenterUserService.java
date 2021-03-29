package com.mhlevel.service.center;

import com.mhlevel.pojo.Users;
import com.mhlevel.pojo.bo.center.CenterUserBO;

/**
 * @author quanbin
 * @date 2021-03-29
 */
public interface CenterUserService {

    /**
     * 查询用户基本信息
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);

    /**
     * 更新用户信息
     * @param userId
     * @param centerUserBO
     * @return
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);
}
