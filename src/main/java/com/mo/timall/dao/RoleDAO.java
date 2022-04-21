package com.mo.timall.dao;

import com.mo.timall.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role,Integer> {
}
