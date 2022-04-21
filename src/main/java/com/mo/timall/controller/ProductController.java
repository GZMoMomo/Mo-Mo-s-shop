package com.mo.timall.controller;

import com.mo.timall.Utils.DataUtils;
import com.mo.timall.Utils.ImageUtil;
import com.mo.timall.dao.*;
import com.mo.timall.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProductController {
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderItemDAO orderItemDAO;
    @Autowired
    propertyvalueDAO propertyvalueDAO;
    @Autowired
    propertyvalueService propertyvalueService;
    @Autowired
    PropertyDAO propertyDAO;
    @Autowired
    PropertyService propertyService;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryDAO categoryDAO;

    @RequestMapping("/listProduct")
    public String list(Model model, int cid, @RequestParam(name = "start",defaultValue = "0") int start,@RequestParam(name = "size",defaultValue = "5") int size){
        Category category=categoryDAO.getById(cid);
        ProductExample example=new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        List<Product> list=productService.selectByExample(example);
        start=start<0?0:start;
        Page<Product> page=DataUtils.listToPage(list,start);
        model.addAttribute("page",page);
        model.addAttribute("c",category);
        return "listProduct";
    }

    @RequestMapping("/addProduct")
    public String add(@Validated Product product, int cid, HttpSession session, @Validated @RequestParam("image") MultipartFile uploadedImageFile, BindingResult result)throws IOException {
        if(!result.hasErrors()){
            productDAO.save(product);
            List<Property> ps=propertyDAO.findByCid(cid);
            for(Property p:ps){
                PropertyValue propertyValue=new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(p.getId());
                propertyvalueService.insert(propertyValue);
            }
            File imageFolder= new File(session.getServletContext().getRealPath("img/product"));
            File file = new File(imageFolder,product.getId()+".png");
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            uploadedImageFile.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "png", file);
        }
        return "redirect:listProduct?cid="+cid;
    }
    //属性编辑器 更改日期格式
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @RequestMapping("/deleteProduct")
    public String delete(Model model,int id,HttpSession session){
        Product product=productDAO.getById(id);
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andPidEqualTo(id);
        List<OrderItem> orderItems=orderItemService.selectByExample(example);
        for(OrderItem oi:orderItems){
            orderItemService.deleteByPrimaryKey(oi.getId());
        }
        List<PropertyValue> ps=propertyvalueDAO.findByPid(id);
        for(PropertyValue p:ps){
            propertyvalueDAO.delete(p);
        }
        String fileName=product.getId()+".png";
        String imageFolder=session.getServletContext().getRealPath("img/product");
        File imageFile=new File(imageFolder,fileName);
        imageFile.delete();
        int cid=productDAO.getById(id).getCid();
        productDAO.deleteById(id);
        return "redirect:listProduct?cid="+cid;
    }

    @RequestMapping("/editProduct")
    public String edit(Model model,@RequestParam(name = "id")int id,@RequestParam(name = "cid")int cid){
        Product product=productDAO.getById(id);
        model.addAttribute("p",product);
        model.addAttribute("cid",cid);
        return "editProduct";
    }

    @RequestMapping("/updateProduct")
    public String update(Product product,@RequestParam("cid") int cid, HttpSession session, @RequestParam("image") MultipartFile uploadedImageFile)throws IOException{
            productDAO.save(product);
            File imageFolder= new File(session.getServletContext().getRealPath("img/product"));
            File file = new File(imageFolder,product.getId()+".png");
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            uploadedImageFile.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "png", file);
        return "redirect:listProduct?cid="+cid;
    }

    @RequestMapping("/editProductproperty")
    public String editProperty(Model model,int cid,int id){
        Product product=productDAO.getById(id);
        PropertyExample example =new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        List<Property> p = propertyService.selectByExample(example);
        PropertyValueExample propertyValueExample=new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(id);
        List<PropertyValue> pv=propertyvalueService.selectByExample(propertyValueExample);
        model.addAttribute("propertys",p);
        model.addAttribute("p",product);
        model.addAttribute("cid",cid);
        model.addAttribute("pvs",pv);
        return "updateProductproperty";
    }

    @RequestMapping("/updateProductproperty")
    @ResponseBody
    public String updateProperty(PropertyValue propertyValue){
        propertyvalueDAO.save(propertyValue);
        return "success";
    }

}
