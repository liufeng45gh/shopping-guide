package com.lucifer.controller.cms;

import com.lucifer.dao.UserDao;
import com.lucifer.exception.ParamException;
import com.lucifer.model.SearchParam;
import com.lucifer.model.User;
import com.lucifer.service.UserService;
import com.lucifer.utils.Constant;
import com.lucifer.utils.PageUtil;
import com.lucifer.utils.Result;
import com.lucifer.utils.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */


@Controller
public class CmsUserController {

    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/cms/user/list",method = RequestMethod.GET)
    public String list(HttpServletRequest request, SearchParam param){
        logger.info("page :"+param.getPage());
        Integer perPageCount = 20;
        @SuppressWarnings("rawtypes")


        List<User> userList=userService.userCmsSearch(param);
        Integer matchRecordCount=userService.userCmsSearchCount(param);
        Integer totalPageCount= PageUtil.getTotalPageCount(matchRecordCount, perPageCount);
        //request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("userList", userList);
        PageUtil pageUtil = new PageUtil(request);
        String pageDiv = pageUtil.willPaginate(totalPageCount,  "pages_bar",new String []{"page","msg"});
        request.setAttribute("pageDiv",pageDiv);
        request.setAttribute("param",param);
        return "/cms/user/list";
    }

    @RequestMapping(value="/cms/user/add",method = RequestMethod.GET)
    public String toAddUser(){
        return "/cms/user/addUser";
    }

    @RequestMapping(value="/cms/user/add",method = RequestMethod.POST)
    public String addUserSubmit(HttpServletRequest request,User user) throws ParamException {
        if (StringHelper.isEmpty(user.getAccount())) {
            throw new ParamException("account 不能为空");
        }
        if (StringHelper.isEmpty(user.getNickName())) {
            throw new ParamException("nickName 不能为空");
        }
        if (StringHelper.isEmpty(user.getPassword())) {
            throw new ParamException("password 不能为空");
        }
        user.setRePassword(user.getPassword());
        userService.createUser(user);
        request.setAttribute(Constant.KEY_RESULT_MESSAGE, "用户已添加");
        request.setAttribute(Constant.KEY_RESULT_MESSAGE_COLOR,"green");
        return "/cms/user/addUser";
    }

    @RequestMapping(value="/cms/user/up-user-pass",method = RequestMethod.POST)
    @ResponseBody
    public Result resetUserPassword(HttpServletRequest request, User user) throws ParamException {
        userService.resetUserPassword(user);
        return Result.ok();
    }

    @RequestMapping(value="/cms/user/set-status",method = RequestMethod.POST)
    @ResponseBody
    public Result setUserLock(HttpServletRequest request, User user){
        userDao.setUserBlock(user);
        return Result.ok();
    }
}
