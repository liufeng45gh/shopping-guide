package com.lucifer.controller.web;


import com.lucifer.dao.CategoryDao;
import com.lucifer.dao.ItemDao;
import com.lucifer.model.Category;
import com.lucifer.model.Item;

import com.lucifer.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufx on 17/2/11.
 */
@Controller
@RequestMapping("/item")
public class WebItemController {

    @Resource
    private ItemDao itemDao;


    @Resource
    private CategoryDao categoryDao;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        //Integer pageSize = Constant.PAGESIZE;

        List<Category> categoryList =  categoryDao.categoryList();

        request.setAttribute("categoryList",categoryList);

        for (Category category: categoryList) {
            List<Item> itemList = itemDao.itemList(null,category.getId(),0,1000);
            category.setItemList(itemList);
        }

       
        return "/web/appreciate/index";
    }

    @RequestMapping(value="/category",method = RequestMethod.GET)
    public String category(HttpServletRequest request){
        

        List<Category> categoryList =  categoryDao.categoryList();

        request.setAttribute("categoryList",categoryList);
        

        return "/web/appreciate/category";
    }

    @RequestMapping(value="/by-category/{categoryId}",method = RequestMethod.GET)
    public String listByCategory(HttpServletRequest request,@PathVariable  Long categoryId){
        Category currentCategory = categoryDao.getCategory(categoryId);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(currentCategory);

        request.setAttribute("categoryList",categoryList);

        for (Category category: categoryList) {
            List<Item> itemList = itemDao.itemList(null,category.getId(),0,1000);
            category.setItemList(itemList);
        }

       
        return "/web/item/index";
    }



    

    @RequestMapping(value="/{id}/detail",method = RequestMethod.GET)
    public String detail(HttpServletRequest request,@PathVariable Long id){
        Item item = itemDao.getItem(id);
        request.setAttribute("entity",item);
        return "/web/appreciate/detail";
    }


    
   

   

}
