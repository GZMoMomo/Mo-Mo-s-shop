package com.mo.timall.mapper;

import com.mo.timall.pojo.Review;
import com.mo.timall.pojo.ReviewExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    int insertSelective(Review record);

    List<Review> selectByExample(ReviewExample example);

    Review selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}