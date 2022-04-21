package com.mo.timall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mo.timall.Utils.DataUtils;
import com.mo.timall.dao.*;
import com.mo.timall.pojo.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @RequestMapping("/listOrder")
    public String list(Model model, @RequestParam(value = "start", defaultValue = "0")int start){
        start = start<0?0:start;
        List<Order> os=orderService.list();
        orderItemService.fill(os);
        Page<Order> page= DataUtils.listToPage(os,start);
        model.addAttribute("page",page);
        return "listOrder";
    }

    @RequestMapping("/listOrdershop")
    public String listshop(Model model, @RequestParam(value = "start", defaultValue = "0")int start){
        start = start<0?0:start;
        List<Order> os=orderService.list();
        orderItemService.fill(os);
        Page<Order> page= DataUtils.listToPage(os,start);
        model.addAttribute("page",page);
        return "listOrdershop";
    }

    @RequestMapping("/deliveryOrder")
    public String delivery(Model model,Order order){
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andOidEqualTo(order.getId());
        List<OrderItem> orderItems=orderItemService.selectByExample(example);
        for(OrderItem orderItem:orderItems){
            Product product=productService.selectByPrimaryKey(orderItem.getPid());
            product.setStock(String.valueOf(Integer.parseInt(product.getStock())-orderItem.getNumber()));
            productService.updateByPrimaryKey(product);
        }
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.updateByPrimaryKeySelective(order);
        return "redirect:listOrdershop";
    }

    @RequestMapping("/confirmOrder")
    public String confirm(Model model,Order order){
        order.setConfirmDate(new Date());
        order.setStatus(OrderService.waitReview);
        orderService.updateByPrimaryKeySelective(order);
        return "redirect:listOrder";
    }

    @RequestMapping("/reviewOrder")
    public String review(Model model,int pid,int oiid){
        OrderItem orderItem=orderItemService.selectByPrimaryKey(oiid);
        Order order=orderService.selectByPrimaryKey(orderItem.getOid());
        Product product= productService.selectByPrimaryKey(pid);
        model.addAttribute("p",product);
        model.addAttribute("o",order);
        return "reviewProduct";
    }

    @RequestMapping("submitreview")
    public String submitreview(Model model,String content ,int pid, int oid,HttpSession session){
        Subject subject=(Subject)session.getAttribute("subject");
        Object obj=subject.getPrincipal();
        String userName=String.valueOf(obj);
        User user=userDAO.findByName(userName);
        Review review=new Review();
        review.setContent(content);
        review.setUid(user.getId());
        review.setPid(pid);
        review.setCreateDate(new Date());
        reviewService.insert(review);
        Order order=orderService.selectByPrimaryKey(oid);
        order.setStatus(OrderService.finish);
        orderService.updateByPrimaryKeySelective(order);
        return "redirect:listOrder";
    }

    @RequestMapping("/getOrderitem")
    public String getOrderitem(Model model,int oid){
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andOidEqualTo(oid);
        List<OrderItem> ois=orderItemService.selectByExample(example);
        orderItemService.setProduct(ois);
        model.addAttribute("ois",ois);
        return "getOrderitem";
    }

    @RequestMapping("/deleteOrderitem")
    @ResponseBody
    public String deleteOrderitem(Model model,int id){
        orderItemService.deleteByPrimaryKey(id);
        return "success";
    }

    @RequestMapping("/changenumberOrderitem")
    @ResponseBody
    public String changenumberOrderitem(Model model,int id,int num){
        OrderItem orderItem=orderItemService.selectByPrimaryKey(id);
        orderItem.setNumber(num);
        orderItemService.updateByPrimaryKey(orderItem);
        return "success";
    }




}
