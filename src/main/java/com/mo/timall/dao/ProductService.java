package com.mo.timall.dao;

import com.mo.timall.pojo.Category;
import com.mo.timall.pojo.Product;
import com.mo.timall.pojo.ProductExample;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    void fill(List<Category> cs);

    void fill(Category c);

    void fillByRow(List<Category> cs);

    void setSaleAndReviewNumber(Product p);

    int getReviewCount(int pid);

    int getSaleCount(int pid);

    void setSaleAndReviewNumber(List<Product> ps);
}
