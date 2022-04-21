package com.mo.timall.dao;

import com.mo.timall.pojo.ProductImage;
import com.mo.timall.pojo.ProductImageExample;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface productImageService  {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}
