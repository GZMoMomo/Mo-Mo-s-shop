package com.mo.timall.mapper;

import com.mo.timall.pojo.ProductImage;
import com.mo.timall.pojo.ProductImageExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ProductImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}