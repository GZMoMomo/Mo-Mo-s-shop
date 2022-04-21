package com.mo.timall.controller;


import com.mo.timall.dao.*;
import com.mo.timall.pojo.*;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.buf.StringUtils;
import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ForeController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyDAO propertyDAO;
    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    com.mo.timall.dao.propertyvalueDAO propertyvalueDAO;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderItemDAO orderItemDAO;

    @RequestMapping("/top")
    public  String top(Model model,HttpSession session){
        Subject subject=(Subject)session.getAttribute("subject");
        if(subject!=null){
            Object obj=subject.getPrincipal();
            model.addAttribute("obj",obj);
        }
        List<Category> cs=categoryDAO.findAll();
        productService.fill(cs);
        model.addAttribute("cs",cs);
        return "top";
    }
    @RequestMapping("/footer")
    public  String footer(Model model){

        return "footer";
    }

    @RequestMapping("/carousel")
    public  String carousel(Model model){

        return "carousel";
    }

    @RequestMapping("/homepageCategoryProducts")
    public  String homepageCategoryProducts(Model model,int cid){
        Category c=categoryDAO.getById(cid);
        productService.fill(c);
        model.addAttribute("c",c);
        return "homepageCategoryProducts";
    }

    @RequestMapping("/index")
    public String index(Model model,HttpSession session){
        Subject subject=(Subject)session.getAttribute("subject");
        if(subject!=null){
            Object obj=subject.getPrincipal();
            model.addAttribute("obj",obj);
        }
        List<Category> cs=categoryDAO.findAll();
        productService.fill(cs);
        model.addAttribute("cs",cs);
        return "index";
    }

    @RequestMapping("/productDetails")
    public String productDetails(int pid,Model model){
        Product p=productService.selectByPrimaryKey(pid);
        productService.setSaleAndReviewNumber(p);
        List<PropertyValue> pvs=propertyvalueDAO.findByPid(pid);
        List<Review> reviews=reviewDAO.findByPid(pid);
        Category category=categoryDAO.getById(p.getCid());
        List<Property> ps=propertyDAO.findByCid(category.getId());
        model.addAttribute("ps",ps);
        model.addAttribute("reviews",reviews);
        model.addAttribute("p",p);
        model.addAttribute("pvs",pvs);
        return "productDetails";
    }

    @RequestMapping("/forbuyone")
    public String forbuyone(Model model,int pid,int num,HttpSession session){
       List<OrderItem> ois=new ArrayList<>();
        Subject subject=(Subject)session.getAttribute("subject");
        Object obj=subject.getPrincipal();
            String userName=String.valueOf(obj);
            OrderItem orderItem=new OrderItem();
            orderItem.setPid(pid);
            orderItem.setNumber(num);
            orderItem.setUid(userDAO.findByName(userName).getId());
            orderItemService.insert(orderItem);
            Product product=productService.selectByPrimaryKey(pid);
            float price=product.getPromoteprice()*num;
            ois.add(orderItem);
            session.setAttribute("ois",ois);
            model.addAttribute("p",product);
            model.addAttribute("o",orderItem);
            model.addAttribute("price",price);
            return "forbuyone";
    }

    @RequestMapping("/forbuymany")
    public String forbuymany(Model model, String str, HttpSession session){
        String[] oiids=str.split(",");
        List<Product> products=new ArrayList<>();
        List<OrderItem> orderItems=new ArrayList<>();
        float price=0;
        for(int i=0;i<oiids.length;i++){
            OrderItem orderItem=orderItemService.selectByPrimaryKey(Integer.valueOf(oiids[i]));
            Product product=productService.selectByPrimaryKey(orderItem.getPid());
            products.add(product);
            orderItems.add(orderItem);
            price+=product.getPromoteprice()*orderItem.getNumber();
        }
        session.setAttribute("ois",orderItems);
        model.addAttribute("ps",products);
        model.addAttribute("os",orderItems);
        model.addAttribute("price",price);
        return "forbuymany";
    }

    @RequestMapping("foraddCart")
    @ResponseBody
    public String addCart(int pid,int num,Model model,HttpSession session){
        Subject subject=(Subject)session.getAttribute("subject");
        Object obj=subject.getPrincipal();
        String userName=String.valueOf(obj);
        User user=userDAO.findByName(userName);
        boolean found=false;
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andUidEqualTo(user.getId());
        List<OrderItem> ois=orderItemService.selectByExample(example);
        for(OrderItem oi:ois){
            if(oi.getPid()==pid){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.updateByPrimaryKey(oi);
                found=true;
                break;
            }
        }
        if(!found){
            OrderItem oi=new OrderItem();
            oi.setUid(user.getId());
            oi.setPid(pid);
            oi.setNumber(num);
            orderItemService.insert(oi);
        }
        return "success";
    }

    @RequestMapping("/quit")
    public String quit(@RequestParam(value = "pid") int pid, Model model){
        Product p=productService.selectByPrimaryKey(pid);
        productService.setSaleAndReviewNumber(p);
        List<PropertyValue> pvs=propertyvalueDAO.findByPid(pid);
        List<Review> reviews=reviewDAO.findByPid(pid);
        Category category=categoryDAO.getById(p.getCid());
        List<Property> ps=propertyDAO.findByCid(category.getId());
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> ois=orderItemService.selectByExample(example);
        for(OrderItem oi:ois){
            orderItemService.deleteByPrimaryKey(oi.getId());
        }
        model.addAttribute("ps",ps);
        model.addAttribute("reviews",reviews);
        model.addAttribute("p",p);
        model.addAttribute("pvs",pvs);
        return "productDetails";
    }

    @RequestMapping("/listCart")
    public String listCart(Model model,HttpSession session){
        Subject subject=(Subject)session.getAttribute("subject");
        Object obj=subject.getPrincipal();
        String userName=String.valueOf(obj);
        User user=userDAO.findByName(userName);
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andUidEqualTo(user.getId()).andOidIsNull();
        List<OrderItem> ois=orderItemService.selectByExample(example);
        orderItemService.setProduct(ois);
        model.addAttribute("ois",ois);
        return "listCart";
    }

    @RequestMapping("/createOrder")
    public String createOrder(Model model,Order order,HttpSession session){
        Subject subject=(Subject)session.getAttribute("subject");
        Object obj=subject.getPrincipal();
        String userName=String.valueOf(obj);
        User user=userDAO.findByName(userName);
        Random random=new Random();
        String orderCode=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+random.nextInt(1000);
        order.setUid(user.getId());
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setPayDate(new Date());
        order.setStatus(OrderService.waitDelivery);
        orderService.insert(order);
        List<OrderItem> ois=(List<OrderItem>)session.getAttribute("ois");
        float total=0;
        for(OrderItem oi:ois){
            oi.setOid(order.getId());
            orderItemService.updateByPrimaryKey(oi);
            Product product=new Product();
            product=productService.selectByPrimaryKey(oi.getPid());
            String stock=product.getStock();
            product.setStock(String.valueOf(Integer.parseInt(stock)-oi.getNumber()));
        }
        return "forpay";
    }


}
