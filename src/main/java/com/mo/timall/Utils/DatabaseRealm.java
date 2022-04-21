
package com.mo.timall.Utils;

import com.mo.timall.dao.RoleDAO;
import com.mo.timall.dao.RoleService;
import com.mo.timall.dao.UserDAO;
import com.mo.timall.dao.User_roleDAO;
import com.mo.timall.pojo.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseRealm extends AuthorizingRealm {
    @Autowired
    RoleService roleService;
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    User_roleDAO user_roleDAO;
    @Autowired
    UserDAO userDAO;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //能进入到这里，表示账号已经通过验证了
        String userName =(String) principalCollection.getPrimaryPrincipal();
        //通过DAO获取角色
        Set<String> result=new HashSet<String>();
        List<Role> roles=roleService.listRolesByUserName(userName);
        for(Role role:roles) {
            result.add(role.getName());
        }
        Set<String> roleset = result;
        //授权对象
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        //把通过DAO获取到的角色和权限放进去
        s.setRoles(roleset);
        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取账号密码
        UsernamePasswordToken t = (UsernamePasswordToken) token;
        String userName= token.getPrincipal().toString();
        String password= new String( t.getPassword());
        //获取数据库中的密码
        String passwordInDB = userDAO.findByName(userName).getPassword();

        //如果为空就是账号不存在，如果不相同就是密码错误，但是都抛出AuthenticationException，而不是抛出具体错误原因，免得给破解者提供帮助信息
        if(null==passwordInDB || !passwordInDB.equals(password))
            throw new AuthenticationException();

        //认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
        SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName,password,getName());
        return a;
    }
}

