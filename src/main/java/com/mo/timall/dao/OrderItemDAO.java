package com.mo.timall.dao;

import com.mo.timall.pojo.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem,Integer> {
  public List<OrderItem> findByPid(int pid);

   public List<OrderItem> findByUid(int iod);
}
