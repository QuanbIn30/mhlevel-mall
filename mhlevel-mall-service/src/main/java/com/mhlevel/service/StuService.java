package com.mhlevel.service;

import com.mhlevel.pojo.Stu;

/**
 * @author quanbin
 * @date 2021-03-19
 */
public interface StuService {

    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);
}
