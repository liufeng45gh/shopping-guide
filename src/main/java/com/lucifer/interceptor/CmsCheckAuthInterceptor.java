package com.lucifer.interceptor;

import com.lucifer.model.User;
import com.lucifer.utils.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CmsCheckAuthInterceptor extends HandlerInterceptorAdapter{
	
	private static  Log log = LogFactory.getLog(CmsCheckAuthInterceptor.class);

	@Override
	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception{
		
		log.info("checkAuth method has been called");
		//log.info(request.getPathInfo());
		//log.info(request.getPathTranslated());
		//log.info(request.getContextPath());
		//log.info(request.getRequestURI());
//		if(true) {
//			return true;
//		}
		if("/cms/login".equals(request.getRequestURI())){
			return true;
		}
		if(request.getRequestURI().startsWith("/cms/css")){
			return true;
		}
		if(request.getRequestURI().startsWith("/cms/images")){
			return true;
		}
		if(request.getRequestURI().startsWith("/cms/script")){
			return true;
		}
		if(request.getRequestURI().startsWith("/cms/fonts")){
			return true;
		}
		User user = (User)request.getSession().getAttribute(Constant.KEY_CMS_USER);
		if(null==user){
			//throw new Exception("not login!");
			response.sendRedirect("/cms/login");
			return false;
		}
		return true;
		
	}
	
	public static User getSessionUser(HttpServletRequest request){
		return (User)request.getSession().getAttribute(Constant.KEY_CMS_USER);
	}
	
	public static void saveSessionUser(User user, HttpServletRequest request){
		request.getSession().setAttribute(Constant.KEY_CMS_USER, user);
	}
}
