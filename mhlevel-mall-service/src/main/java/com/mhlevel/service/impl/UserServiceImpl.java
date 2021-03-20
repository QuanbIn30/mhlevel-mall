package com.mhlevel.service.impl;

import com.mhlevel.mapper.UsersMapper;
import com.mhlevel.pojo.Users;
import com.mhlevel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author quanbin
 * @date 2021-03-19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

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
}
