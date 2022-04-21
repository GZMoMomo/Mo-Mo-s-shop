package com.mo.timall.dao;

import com.mo.timall.pojo.Property;
import com.mo.timall.pojo.PropertyExample;

import java.util.List;

public interface PropertyImpl {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}
