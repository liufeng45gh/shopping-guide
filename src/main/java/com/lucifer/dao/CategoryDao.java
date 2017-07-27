package com.lucifer.dao;

import com.lucifer.dao.IBatisBaseDao;
import com.lucifer.model.Category;
import com.lucifer.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/12.
 */
@Component
public class CategoryDao extends IBatisBaseDao {

    public List<Category> categoryList(){
        return this.sqlSession.selectList("categoryList");
    }

    public Integer addCategory(Category category){
        return this.sqlSession.insert("addCategory",category);
    }

    public Integer deleteCategory(Long id){
        return this.sqlSession.delete("deleteCategory",id);
    }

    public Category getCategory(Long id){
        return this.sqlSession.selectOne("getCategory",id);
    }

    public Integer updateCategory(Category category){
        return this.sqlSession.update("updateCategory",category);
    }

}
