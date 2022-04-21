package com.mo.timall.dao;

import com.mo.timall.mapper.OrderItemMapper;
import com.mo.timall.mapper.ProductMapper;
import com.mo.timall.pojo.Order;
import com.mo.timall.pojo.OrderItem;
import com.mo.timall.pojo.OrderItemExample;
import com.mo.timall.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        orderItemMapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(OrderItem record) {
        orderItemMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(OrderItem record) {
        orderItemMapper.insertSelective(record);
        return 0;
    }

    @Override
    public List<OrderItem> selectByExample(OrderItemExample example) {
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public OrderItem selectByPrimaryKey(Integer id) {
        return orderItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderItem record) {
        orderItemMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public int updateByPrimaryKey(OrderItem record) {
        orderItemMapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public void fill(List<Order> os) {
        for(Order o:os){
            fill(o);
        }
    }

    @Override
    public void fill(Order o) {
       OrderItemExample example=new OrderItemExample();
       example.createCriteria().andOidEqualTo(o.getId());
       example.setOrderByClause("id desc");
       List<OrderItem> ois=orderItemMapper.selectByExample(example);
       setProduct(ois);
       float total=0;
       int totalNumber=0;
       for(OrderItem oi:ois){
           total+=oi.getNumber()*oi.getProduct().getPromoteprice();
           totalNumber+=oi.getNumber();
       }
       o.setTotal(total);
       o.setTotalNumber(totalNumber);
       o.setOrderItems(ois);
    }

    public void setProduct(List<OrderItem> ois){
        for (OrderItem oi: ois) {
            setProduct(oi);
        }
    }

    public void setProduct(OrderItem oi) {
        Product p = productMapper.selectByPrimaryKey(oi.getPid());
        oi.setProduct(p);
    }
}
