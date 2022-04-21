package com.mo.timall.dao;

import com.mo.timall.mapper.PropertyMapper;
import com.mo.timall.pojo.Property;
import com.mo.timall.pojo.PropertyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService implements PropertyImpl  {
    @Autowired
    PropertyMapper propertyMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        propertyMapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Property record) {
        propertyMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(Property record) {
        propertyMapper.insertSelective(record);
        return 0;
    }

    @Override
    public List<Property> selectByExample(PropertyExample example) {
        return propertyMapper.selectByExample(example);
    }

    @Override
    public Property selectByPrimaryKey(Integer id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Property record) {
        propertyMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Property record) {
        propertyMapper.updateByPrimaryKey(record);
        return 0;
    }


}
