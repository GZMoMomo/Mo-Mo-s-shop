package com.mo.timall.dao;

import com.mo.timall.pojo.PropertyValue;
import com.mo.timall.pojo.PropertyValueExample;

import java.util.List;

public interface propertyvalueService {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}
