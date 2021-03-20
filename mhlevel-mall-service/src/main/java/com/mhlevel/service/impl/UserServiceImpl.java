package com.mhlevel.service.impl;

import com.mhlevel.enums.Sex;
import com.mhlevel.mapper.UsersMapper;
import com.mhlevel.pojo.Users;
import com.mhlevel.pojo.bo.UserBO;
import com.mhlevel.service.UserService;
import com.mhlevel.utils.DateUtil;
import com.mhlevel.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author quanbin
 * @date 2021-03-19
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String faceUrl = "https://pic.17qq.com/uploads/sscharcsscx.jpeg";

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String userName) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", userName);
        Users users = usersMapper.selectOneByExample(userExample);
        if(users == null){
            return false;
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO){

        String userId = sid.nextShort();
        String userName = userBO.getUsername();
        String passWord = userBO.getPassword();
        Users user = new Users();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5Str(passWord));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setId(userId);
        user.setFace(faceUrl);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        user.setNickname(userName);
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        user.setSex(Sex.MAN.getType());
        usersMapper.insert(user);
        return user;
    }
}
