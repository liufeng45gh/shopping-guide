package com.lucifer.dao;


import com.lucifer.dao.IBatisBaseDao;
import com.lucifer.model.Item;

import com.lucifer.utils.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liufx on 17/1/16.
 */
@Component
public class ItemDao extends IBatisBaseDao {

  
    public List<Item> itemList(String title, Long categoryId, Integer offset, Integer count){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("title",title);
        param.put("categoryId",categoryId);
        param.put("offset",offset);
        param.put("count",count);
        return this.sqlSession.selectList("itemList",param);
    }

    public List<Item> itemTopList(String title, Integer offset, Integer count){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("title",title);
        param.put("offset",offset);
        param.put("count",count);
        return this.sqlSession.selectList("itemTopList",param);
    }

    public Integer matchRecordCount(String title,Long categoryId){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("title",title);
        param.put("categoryId",categoryId);
        return this.sqlSession.selectOne("cmsItemMatchRecordCount",param);
    }

    public Integer matchTopRecordCount(String title){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("title",title);
        return this.sqlSession.selectOne("cmsItemTopMatchRecordCount",param);
    }

    public Integer insertItem(Item appreciate){
        appreciate.setCreatedAt(DateUtils.now());
        appreciate.setUpdatedAt(DateUtils.now());
        //news.setTop(0f);
        appreciate.setClickCount(0);
        Integer updateCount =  this.sqlSession.insert("insertItem",appreciate);
        //appreciateService.updateAllAppreciateCount();
        return updateCount;
    }

    public Item getItem(Long id){
       
        return sqlSession.selectOne("getItem",id);

    }
    

    public Integer updateItem(Item item){
        item.setUpdatedAt(DateUtils.now());
        
        return this.sqlSession.update("updateItem",item);
    }

    public Integer deleteItem(Long id){
        Integer updateCount = this.sqlSession.delete("deleteItem",id);
        //appreciateService.updateAllAppreciateCount();
        return updateCount;
    }

    public List<Item> itemListOrderByUpdatedAt(Date updatedAt, int count){
        Map param = new HashMap();
        param.put("updatedAt", updatedAt);
        param.put("count", count);
        return this.sqlSession.selectList("itemListOrderByUpdatedAt",param);
    }
    
   
}
