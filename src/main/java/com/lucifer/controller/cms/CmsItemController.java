package com.lucifer.controller.cms;


import com.lucifer.dao.CategoryDao;
import com.lucifer.dao.ItemDao;
import com.lucifer.model.Category;
import com.lucifer.model.Item;

import com.lucifer.utils.Constant;
import com.lucifer.utils.DateUtils;
import com.lucifer.utils.PageUtil;
import com.lucifer.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liufx on 17/1/16.
 */

@Controller
@RequestMapping("/cms/item")
public class CmsItemController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ItemDao itemDao;

    @Resource
    private CategoryDao categoryDao;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String appreciateList(HttpServletRequest request, @RequestParam(value = "title",required=false,defaultValue="") String title,
                                 @RequestParam(value = "categoryId",required=false,defaultValue="") Long categoryId,
                           @RequestParam(value = "page",required=false,defaultValue="1")Integer page){
        Integer pageSize = Constant.PAGESIZE;
        Integer offset = (page-1) * pageSize;
        List<Item> itemList = itemDao.itemList(title,categoryId,offset,pageSize);
        request.setAttribute("itemList",itemList);

        Integer matchRecordCount=itemDao.matchRecordCount(title,categoryId);

        Integer totalPageCount= PageUtil.getTotalPageCount(matchRecordCount, pageSize);

        PageUtil pageUtil = new PageUtil(request);
        String pageDiv = pageUtil.willPaginate(totalPageCount,  "pages_bar",new String []{"page","msg"});
        request.setAttribute("pageDiv",pageDiv);
        request.setAttribute("title",title);
        return "/cms/item/list";
    }

    @RequestMapping(value="/top-list",method = RequestMethod.GET)
    public String appreciateTopList(HttpServletRequest request, @RequestParam(value = "title",required=false,defaultValue="") String title,
                                 @RequestParam(value = "page",required=false,defaultValue="1")Integer page){
        Integer pageSize = Constant.PAGESIZE;
        Integer offset = (page-1) * pageSize;
        List<Item> itemList = itemDao.itemTopList(title,offset,pageSize);
        request.setAttribute("itemList",itemList);

        Integer matchRecordCount=itemDao.matchTopRecordCount(title);

        Integer totalPageCount= PageUtil.getTotalPageCount(matchRecordCount, pageSize);

        PageUtil pageUtil = new PageUtil(request);
        String pageDiv = pageUtil.willPaginate(totalPageCount,  "pages_bar",new String []{"page","msg"});
        request.setAttribute("pageDiv",pageDiv);
        request.setAttribute("title",title);
        return "/cms/item/top_list";
    }

    @RequestMapping(value="/add",method = RequestMethod.GET)
    public String appreciateAddInput(HttpServletRequest request){
        List<Category> categoryList = categoryDao.categoryList();
        request.setAttribute("categoryList",categoryList);

        Item appreciate = new Item();
        request.setAttribute("entity",appreciate);
        appreciate.setLogo("/cms/images/logo.png");
        appreciate.setPublishAt(DateUtils.now());
        return "/cms/item/add";
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    @ResponseBody
    public Result addSubmit(Item item){
        logger.info(" item AddSubmit has been called");
        itemDao.insertItem(item);
        return Result.ok();
    }
    @RequestMapping(value="/{id}/update",method = RequestMethod.GET)
    public String appreciateUpdateInput(HttpServletRequest request,@PathVariable Long id){
        Item item = itemDao.getItem(id);
        request.setAttribute("entity",item);
//        request.setAttribute("publishAt",news.getPublishAt());

        List<Category> categoryList = categoryDao.categoryList();
        request.setAttribute("categoryList",categoryList);
        return "/cms/item/update";
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public Result appreciateUpdateSubmit(Item item){
        itemDao.updateItem(item);

        return Result.ok();
    }

    @RequestMapping(value="/{id}/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteItem(@PathVariable Long id) {
        itemDao.deleteItem(id);
        return Result.ok();
    }

  

  
}
