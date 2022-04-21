package com.mo.timall.dao;

import com.mo.timall.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product,Integer> {
  public List<Product> findByCid(int cid);
}
