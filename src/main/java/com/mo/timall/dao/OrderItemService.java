package com.mo.timall.dao;

import com.mo.timall.pojo.Order;
import com.mo.timall.pojo.OrderItem;
import com.mo.timall.pojo.OrderItemExample;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderItemService {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    List<OrderItem> selectByExample(OrderItemExample example);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    void fill(List<Order> os);

    void fill(Order o);

    void setProduct(List<OrderItem> ois);

    void setProduct(OrderItem oi);
}
