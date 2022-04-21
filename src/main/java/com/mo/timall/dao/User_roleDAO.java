package com.mo.timall.dao;

import com.mo.timall.pojo.User_role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface User_roleDAO extends JpaRepository<User_role,Integer> {
   public User_role findByUid(int uid);
}
