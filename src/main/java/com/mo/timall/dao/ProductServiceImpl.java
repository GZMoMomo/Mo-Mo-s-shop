package com.mo.timall.dao;

import com.mo.timall.mapper.OrderItemMapper;
import com.mo.timall.mapper.ProductMapper;
import com.mo.timall.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    productImageService productImageService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        productMapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Product record) {
        productMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(Product record) {
        productMapper.insertSelective(record);
        return 0;
    }

    @Override
    public List<Product> selectByExample(ProductExample example) {
        return productMapper.selectByExample(example);
    }

    @Override
    public Product selectByPrimaryKey(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Product record) {
        productMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Product record) {
        productMapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public void fill(List<Category> cs) {
        for (Category c : cs) {
            fill(c);
        }
    }

    @Override
    public void fill(Category c) {
       List<Product> ps=list(c.getId());
       c.setProducts(ps);
    }

    public void setCategory(List<Product> ps){
        for (Product p : ps)
            setCategory(p);
    }
    public void setCategory(Product p){
        int cid = p.getCid();
        Category c = categoryService.selectByPrimaryKey(cid);
        p.setCategory(c);
    }

    private List<Product> list(Integer cid) {
        ProductExample example=new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List result=productMapper.selectByExample(example);
        setCategory(result);
        return result;
    }

    @Override
    public void fillByRow(List<Category> cs) {
       int productNumberEachRow=5;
       for(Category c:cs){
           List<Product> products=c.getProducts();
           List<List<Product>> productsByRow=new ArrayList<>();
           for(int i=0;i<products.size();i+=productNumberEachRow){
               int size=i+productNumberEachRow;
               size=size>products.size()?products.size():size;
               List<Product> productsOfEachRow =products.subList(i, size);
               productsByRow.add(productsOfEachRow);
           }
           c.setProductsByRow(productsByRow);
       }
    }

    public int getSaleCount(int pid){
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> ois=orderItemService.selectByExample(example);
        int result=0;
        for(OrderItem oi:ois){
            result+=oi.getNumber();
        }
        return result;
    }

    public int getReviewCount(int pid){
        ReviewExample example=new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Review> result=reviewService.selectByExample(example);
        return result.size();
    }

    public void setSaleAndReviewNumber(Product p){
        int saleCount=getSaleCount(p.getId());
        int reviewCount=getReviewCount(p.getId());
        p.setSaleCount(saleCount);
        p.setReviewCount(reviewCount);
    }

    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p : ps) {
            setSaleAndReviewNumber(p);
        }
    }
}
