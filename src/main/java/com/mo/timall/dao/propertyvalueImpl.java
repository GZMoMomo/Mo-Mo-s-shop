package com.mo.timall.dao;

import com.mo.timall.mapper.PropertyValueMapper;
import com.mo.timall.pojo.PropertyValue;
import com.mo.timall.pojo.PropertyValueExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class propertyvalueImpl implements propertyvalueService {
   @Autowired
    PropertyValueMapper propertyValueMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        propertyValueMapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(PropertyValue record) {
        propertyValueMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(PropertyValue record) {
        propertyValueMapper.insertSelective(record);
        return 0;
    }

    @Override
    public List<PropertyValue> selectByExample(PropertyValueExample example) {
        return propertyValueMapper.selectByExample(example);
    }

    @Override
    public PropertyValue selectByPrimaryKey(Integer id) {
        return propertyValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PropertyValue record) {
        propertyValueMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(PropertyValue record) {
        propertyValueMapper.updateByPrimaryKey(record);
        return 0;
    }
}
