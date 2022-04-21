package com.mo.timall.controller;

import com.mo.timall.Utils.ImageUtil;
import com.mo.timall.dao.ProductDAO;
import com.mo.timall.dao.PropertyDAO;
import com.mo.timall.pojo.*;
import com.mo.timall.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CategoryController {
    @Autowired
    ProductDAO productDAO;
    @Autowired
    PropertyDAO propertyDAO;
    @Autowired
    CategoryDAO categoryDAO;

    @RequestMapping("/listCategory")
    public String listCategory(Model m, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Category> page =categoryDAO.findAll(pageable);
        m.addAttribute("page",page);
        m.addAttribute("msg",Msg.success());
        return "listCategory";
    }

    @RequestMapping("/addCategory")
    public String addCategory(@Validated Category category, Model model,HttpSession session,@Validated @RequestParam("image") MultipartFile uploadedImageFile,BindingResult result) throws IOException {
        if (result.hasErrors()) {
                Map map = new HashMap();
                List<FieldError> errors = result.getFieldErrors();
                for (FieldError fieldError : errors) {
                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                model.addAttribute("msg", Msg.fail().add("errorFields", map));
                return "redirect:listCategory";
            } else {
            categoryDAO.save(category);
            File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder,category.getId()+".png");
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            uploadedImageFile.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "png", file);
            return "redirect:listCategory";
            }

    }

    @RequestMapping("/deleteCategory")
    public String deleteCategory(Category category) throws Exception{
        List<Product> ps=productDAO.findByCid(category.getId());
        productDAO.deleteAll(ps);
        List<Property> pts=propertyDAO.findByCid(category.getId());
        propertyDAO.deleteAll(pts);
        categoryDAO.delete(category);
        return "redirect:listCategory";
    }

    @RequestMapping("/updateCategory")
    public String updateCategory(Category category,HttpSession session, UploadedImageFile uploadedImageFile) throws Exception{
        categoryDAO.save(category);
        MultipartFile image = uploadedImageFile.getImage();
        if(null!=image &&!image.isEmpty()){
            File  imageFolder= new File(session.getServletContext().getRealPath("static/js/img/category"));
            File file = new File(imageFolder,category.getId()+".png");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "png", file);
        }
        return "redirect:listCategory";
    }

    @RequestMapping("/editCategory")
    public String editCategory(@RequestParam(value = "id")int id,Model model) throws Exception{
        Category category=categoryDAO.getById(id);
        model.addAttribute("c",category);
        return "editCategory";
    }


}
