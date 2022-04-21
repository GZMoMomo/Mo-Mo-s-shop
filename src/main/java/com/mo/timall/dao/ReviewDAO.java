package com.mo.timall.dao;

import com.mo.timall.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDAO extends JpaRepository<Review,Integer> {
    public List<Review> findByPid(int pid);
}
