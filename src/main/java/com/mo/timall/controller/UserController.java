package com.mo.timall.controller;

import com.mo.timall.dao.UserDAO;
import com.mo.timall.dao.User_roleDAO;
import com.mo.timall.pojo.User;
import com.mo.timall.pojo.UserExample;
import com.mo.timall.pojo.User_role;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class UserController {

    @Autowired
    User_roleDAO user_roleDAO;
    @Autowired
    UserDAO userDAO;
    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(Model model, @RequestParam(name = "name")String name,@RequestParam(name = "password")String password){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(name,password);
        try {
            subject.login(token);
            Session session=subject.getSession();
            session.setAttribute("subject",subject);
            return "forward:/index";
        }catch (AuthenticationException e){
             return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "forward:/index";
    }

    @RequestMapping(value="/register")
    public String register(Model model){
        String m=null;
        model.addAttribute("msg", m);
        return "register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model, @Validated User user, BindingResult result,int rid){
        if(!result.hasErrors()){
           User users=userDAO.findByName(user.getName());
            if(users!=null){
                String m ="用户名已经被使用,不能使用!";
                model.addAttribute("msg", m);
                model.addAttribute("user", null);
                return "register";
            }
            userDAO.save(user);
            User_role ur=new User_role();
            ur.setRid(rid);
            ur.setUid(user.getId());
            user_roleDAO.save(ur);
        }
        return "login";
    }
}
