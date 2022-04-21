package com.mo.timall.dao;

import com.mo.timall.mapper.ProductImageMapper;
import com.mo.timall.pojo.ProductImage;
import com.mo.timall.pojo.ProductImageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class productImageServiceImpl implements productImageService {
    @Autowired
    ProductImageMapper productImageMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        productImageMapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(ProductImage record) {
        productImageMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(ProductImage record) {
        productImageMapper.insertSelective(record);
        return 0;
    }

    @Override
    public List<ProductImage> selectByExample(ProductImageExample example) {
        return productImageMapper.selectByExample(example);
    }

    @Override
    public ProductImage selectByPrimaryKey(Integer id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ProductImage record) {
        productImageMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ProductImage record) {
        productImageMapper.updateByPrimaryKey(record);
        return 0;
    }
}
