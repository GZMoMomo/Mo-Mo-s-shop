package com.mo.timall.dao;

import com.mo.timall.pojo.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface propertyvalueDAO extends JpaRepository<PropertyValue,Integer> {

  public List<PropertyValue> findByPid(int pid);
  public List<PropertyValue> findByPtid(int ptid);
}
