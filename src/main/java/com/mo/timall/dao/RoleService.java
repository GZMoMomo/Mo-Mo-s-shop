package com.mo.timall.dao;

import com.mo.timall.pojo.Role;

import java.util.List;
import java.util.Set;


public interface RoleService {
    public List<Role> listRolesByUserName(String userName);
}
