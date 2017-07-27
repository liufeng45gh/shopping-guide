package com.lucifer.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

public class PageUtil {
	
	public static int default_per_page_count = 20;
	
	public static String perPageCount="perPageCount";
	
	public static String offset="offset";
	
	private static  Log log = LogFactory.getLog(PageUtil.class);
	
	public static Integer getStartOffset(Integer page,Integer perPageCount){	
		if (null == page || page.equals(0)) {
			page = 1;
		}
		return perPageCount * (page-1);		
	}
	
	public static Integer getTotalPageCount(Integer matchRecordCount,Integer perPageCount){
		if (matchRecordCount % perPageCount == 0){
			return matchRecordCount/perPageCount;
		}else{
			return matchRecordCount/perPageCount + 1;
		}		
	}
	

	private HttpServletRequest request = null; 
	
	public PageUtil(HttpServletRequest request){
		this.request=request;
	}
	
    public String param(String name) {
        return request.getParameter(name);
    }

	public String param(String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
            return defaultValue;
        }

        return value;
	}
    public int paramAsInt(String name) {
        return Integer.parseInt(param(name, "-1"));
    }

    public int paramAsInt(String name, int defaultValue) {
        String value = param(name);
        if (value != null) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }
    
    public Object attr(String name) {
        return request.getAttribute(name);
    }
    
    public Integer attrAsInt(String name) {
        if(null==request.getAttribute(name)){
           return -1;
        }else if(request.getAttribute(name) instanceof Integer){
            return (Integer)request.getAttribute(name);
        }
        return -1;
    }

    public Boolean attrAsBool(String name) {
        if(null==request.getAttribute(name)){
            return false;
        }else if(request.getAttribute(name) instanceof Boolean){
            return (Boolean)request.getAttribute(name);
        }
        return false;
    }
	
	public  String willPaginate(long pageCount,  String styleClass,String [] pageparas) {
		log.info("willPaginate has been called");
        if(null==pageparas){
            pageparas=new String []{"page"};
        }
        String requestParameterStrings =getRequestParameterStrings(pageparas) ;

        StringBuffer buffer = new StringBuffer();

        int currentPage = paramAsInt("page", 1);

       
        if (pageCount > 1) {
            buffer.append(String.format("<div class='%s'>", styleClass));
            if (currentPage > 1) {
                buffer.append(String.format("<a href='?page=%d"+requestParameterStrings+"' class='btn'>上一页</a>",
                        currentPage - 1,
                        styleClass
                ));
            }

            boolean dotAddedLeft = false;
            boolean dotAddedRight = false;
            for (int i = 0; i < pageCount; i++) {

                if (Math.abs((currentPage-1) - i) >= 5 && i != 0 && i != pageCount - 1) {
                    if (!dotAddedLeft && (currentPage-1) - i >= 5) {
                        buffer.append("<a class='ellipsis'>......</a>");
                        dotAddedLeft = true;
                    } else if (!dotAddedRight && i - (currentPage-1) >= 5) {
                        buffer.append("<a class='ellipsis'>......</a>");
                        dotAddedRight = true;
                    }
                    continue;
                }

                buffer.append(String.format("<a href='?page=%d"+requestParameterStrings+"' class='%s'>%d</a>",
                        i + 1,                    
                        currentPage == (i + 1) ? "current_page" : "number",
                        i + 1
                ));
            }

            if (currentPage < pageCount) {
                buffer.append(String.format("<a href='?page=%d"+requestParameterStrings+"' class='btn'>下一页</a>",
                        currentPage + 1
                ));
            }

            buffer.append("<span>&nbsp;</span></div>");
        }

        return buffer.toString();
    }
	
	
	private String getRequestParameterStrings(String [] excludes){
	    StringBuffer parameterBuffer=new StringBuffer();
	     Map <String,String[]> requestParameterMap=request.getParameterMap();
	     Set<String>   keySet=requestParameterMap.keySet();
	     for(String key:keySet){
	         boolean flag=isContain(excludes,key) ;
	         if(flag){
	            continue;
	         }
	         String [] values= requestParameterMap.get(key);
	         for(String value:values){
	             parameterBuffer.append("&"+key+"="+value);
	             }
	         }
	    return parameterBuffer.toString();
	}
	
    private boolean isContain(String [] strArray,String str){
        for(String _str:strArray){
            if(_str.equals(str)){
               return true;
            }
        }
         return false;
     }
	
	
}
