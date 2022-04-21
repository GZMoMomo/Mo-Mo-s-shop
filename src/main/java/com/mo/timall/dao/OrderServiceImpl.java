package com.mo.timall.dao;

import com.mo.timall.mapper.OrderMapper;
import com.mo.timall.pojo.Order;
import com.mo.timall.pojo.OrderExample;
import com.mo.timall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserDAO userDAO;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        orderMapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public int insert(Order record) {
        orderMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(Order record) {
        orderMapper.insertSelective(record);
        return 0;
    }

    @Override
    public List<Order> selectByExample(OrderExample example) {
        return orderMapper.selectByExample(example);
    }

    @Override
    public Order selectByPrimaryKey(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Order record) {
        orderMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Order record) {
        orderMapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public List list() {
        OrderExample example =new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result =orderMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    public void setUser(List<Order> os){
        for (Order o : os)
            setUser(o);
    }
    public void setUser(Order o){
        int uid = o.getUid();
        User u = userDAO.getById(uid);
        o.setUser(u);
    }
}
