package com.mo.timall.mapper;


import com.mo.timall.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> listRolesByUserName(String userName);
}
