package com.lucifer.controller.web;

import com.lucifer.cache.AppCache;
import com.lucifer.utils.Constant;
import com.lucifer.utils.Result;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by liufx on 17/3/15.
 */
@Controller
public class CacheController {

  
    @Resource
    private AppCache appCache;

    @ApiOperation(value = "删除缓存")
    @RequestMapping(value="/cache/remove-all-cache",method= RequestMethod.GET)
    @ResponseBody
    public Result removeAllCache(){
        //memberDao.removeAllCacheing();
        appCache.removeAll(Constant.CACHE_KEY_REMOVE_ALL);
        return Result.ok();
    }
}
