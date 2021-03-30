package com.mhlevel.mapper;

import com.mhlevel.my.mapper.MyMapper;
import com.mhlevel.pojo.Users;

public interface UsersMapperCustom {

    void updateUserFaceByUserId(String userId, String userFaceUrl);
}