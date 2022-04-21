package com.mo.timall.mapper;

import com.mo.timall.pojo.Property;
import com.mo.timall.pojo.PropertyExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}