package com.mo.timall.dao;


import com.mo.timall.pojo.Property;
import com.mo.timall.pojo.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyDAO extends JpaRepository<Property,Integer> {
    public List<Property> findByCid(int cid);

}
