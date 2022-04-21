package com.mo.timall.dao;

import com.mo.timall.mapper.CategoryMapper;
import com.mo.timall.pojo.Category;
import com.mo.timall.pojo.CategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService implements CategoryMapper {
    @Lazy
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Category record) {
        return 0;
    }

    @Override
    public int insertSelective(Category record) {
        return 0;
    }

    @Override
    public List<Category> selectByExample(CategoryExample example) {
        return null;
    }

    @Override
    public Category selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Category record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Category record) {
        return 0;
    }

    public void add(Category category) {
        categoryMapper.insert(category);
    }
}
