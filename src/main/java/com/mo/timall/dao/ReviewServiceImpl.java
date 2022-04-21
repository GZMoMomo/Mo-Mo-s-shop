package com.mo.timall.dao;

import com.mo.timall.mapper.ReviewMapper;
import com.mo.timall.pojo.Review;
import com.mo.timall.pojo.ReviewExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        reviewMapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Review record) {
        reviewMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(Review record) {
        reviewMapper.insertSelective(record);
        return 0;
    }

    @Override
    public List<Review> selectByExample(ReviewExample example) {
        return reviewMapper.selectByExample(example);
    }

    @Override
    public Review selectByPrimaryKey(Integer id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Review record) {
        reviewMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Review record) {
        reviewMapper.updateByPrimaryKey(record);
        return 0;
    }
}
