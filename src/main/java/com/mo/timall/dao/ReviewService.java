package com.mo.timall.dao;

import com.mo.timall.pojo.Review;
import com.mo.timall.pojo.ReviewExample;

import java.util.List;

public interface ReviewService {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    int insertSelective(Review record);

    List<Review> selectByExample(ReviewExample example);

    Review selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}
