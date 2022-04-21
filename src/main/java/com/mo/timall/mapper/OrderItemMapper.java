package com.mo.timall.mapper;

import com.mo.timall.pojo.OrderItem;
import com.mo.timall.pojo.OrderItemExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    List<OrderItem> selectByExample(OrderItemExample example);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}