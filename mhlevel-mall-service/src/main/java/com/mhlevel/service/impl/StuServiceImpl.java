package com.mhlevel.service.impl;

import com.mhlevel.mapper.StuMapper;
import com.mhlevel.pojo.Stu;
import com.mhlevel.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author quanbin
 * @date 2021-03-19
 */
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {

        Stu stu = stuMapper.selectByPrimaryKey(id);
        return stu;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveStu() {

        Stu stu = new Stu();
        stu.setAge(22);
        stu.setId(2);
        stu.setName("mhlevel");
        stuMapper.insert(stu);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateStu(int id) {

        Stu stu = new Stu();
        stu.setName("tom");
        stu.setId(id);
        stu.setAge(2);
        stuMapper.updateByPrimaryKey(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteStu(int id) {

        stuMapper.deleteByPrimaryKey(id);
    }
}
