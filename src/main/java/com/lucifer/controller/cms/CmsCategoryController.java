package com.lucifer.controller.cms;


import com.lucifer.dao.CategoryDao;

import com.lucifer.model.Category;
import com.lucifer.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by fx on 2017/1/30.
 */

@Controller
@RequestMapping("/cms")
public class CmsCategoryController {

    @Resource
    private CategoryDao categoryDao;

    @RequestMapping(value="/category/list",method = RequestMethod.GET)
    public String categoryList(HttpServletRequest request){
        List<Category> appreciateCategoryList= categoryDao.categoryList();
        request.setAttribute("categoryList",appreciateCategoryList);
        return "/cms/category/category_list";
    }

    @RequestMapping(value="/category/list.json",method = RequestMethod.GET)
    @ResponseBody
    public List<Category>  categoryListForJSON(HttpServletRequest request){
        List<Category> appreciateCategoryList = categoryDao.categoryList();
        return appreciateCategoryList;
    }

    @RequestMapping(value="/category/add",method = RequestMethod.POST)
    public String categoryAdd(Category category) {
        categoryDao.addCategory(category);
        return "redirect:/cms/category/list";
    }

    @RequestMapping(value="/category/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result categoryDelete(Long id) {
        categoryDao.deleteCategory(id);
        return Result.ok();
    }

    @RequestMapping(value="/category/{id}/update",method = RequestMethod.GET)
    public String categoryUpdateInput(@PathVariable Long id, HttpServletRequest request) {
        Category category = categoryDao.getCategory(id);
        request.setAttribute("entity",category);
        return "/cms/category/category_update";
    }

    @RequestMapping(value="/category/update",method = RequestMethod.POST)
    @ResponseBody
    public Result categoryUpdateSubmit(Category category){
        categoryDao.updateCategory(category);
        return Result.ok();
    }

}
