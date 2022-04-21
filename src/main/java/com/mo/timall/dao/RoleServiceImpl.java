package com.mo.timall.dao;


import com.mo.timall.mapper.RoleMapper;
import com.mo.timall.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> listRolesByUserName(String userName) {
        return roleMapper.listRolesByUserName(userName);
    }
}
