package com.mo.timall.mapper;

import com.mo.timall.pojo.PropertyValue;
import com.mo.timall.pojo.PropertyValueExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PropertyValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}