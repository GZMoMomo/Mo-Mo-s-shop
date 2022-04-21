package com.mo.timall.dao;

import com.mo.timall.pojo.Order;
import com.mo.timall.pojo.OrderExample;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    int deleteByPrimaryKey(Integer id);
    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List list();
}
