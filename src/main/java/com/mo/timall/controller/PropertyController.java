package com.mo.timall.controller;


import com.mo.timall.Utils.DataUtils;
import com.mo.timall.dao.*;
import com.mo.timall.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PropertyController {
    @Autowired
    propertyvalueDAO propertyvalueDAO;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    PropertyDAO propertyDAO;
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryDAO categoryDAO;


    @RequestMapping("/listProperty")
    public String list(@RequestParam(value = "cid")int cid, Model model, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size){
        start = start<0?0:start;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
       PropertyExample example =new PropertyExample();
       example.createCriteria().andCidEqualTo(cid);
       Category category=categoryDAO.getById(cid);
       List<Property> p = propertyService.selectByExample(example);
       Page<Property> page= DataUtils.listToPage(p,start);
       model.addAttribute("c",category);
       model.addAttribute("page",page);
       return "listProperty";
    }

    @RequestMapping("/addProperty")
    public String add(Model model,@Validated Property property, BindingResult result,int cid)throws Exception{
        if(!result.hasErrors()){
            propertyDAO.save(property);
          List<Product> ps=productDAO.findByCid(cid);
          for(Product p:ps){
              int pid=p.getId();
              PropertyValue propertyValue=new PropertyValue();
              propertyValue.setPid(pid);
              propertyValue.setPtid(property.getId());
              propertyvalueDAO.save(propertyValue);
          }
        }
        return "redirect:listProperty?cid="+property.getCid();
    }

    @RequestMapping("/deleteProperty")
    public String delete(Model model,@Validated Property property, BindingResult result)throws Exception{
        if(!result.hasErrors()){
            List<PropertyValue> ps=propertyvalueDAO.findByPtid(property.getId());
            propertyvalueDAO.deleteAll(ps);
            propertyDAO.delete(property);
        }
        return "redirect:listProperty?cid="+property.getCid();
    }

    @RequestMapping("/editProperty")
    public String edit(Model model,@RequestParam(value = "id")int id)throws Exception{
        Property property=propertyDAO.getById(id);
        model.addAttribute("p",property);
        return "editProperty";
    }

    @RequestMapping("/updateProperty")
    public String update(Model model,Property property)throws Exception{
        propertyDAO.save(property);
        model.addAttribute("p",property);
        return "redirect:listProperty?cid="+property.getCid();
    }
}
