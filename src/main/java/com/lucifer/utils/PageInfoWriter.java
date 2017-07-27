package com.lucifer.utils;



import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by liufx on 15/11/18.
 */
public class PageInfoWriter {

    private static final Integer maxPage = 5000;

    private static final Integer defaultPerPageCount = 20;

    private Integer perPageCount;

    private Integer allRecordCount;

    private Integer page;

    private List dataList;


    public Integer getTotalPageCount(){
        if (allRecordCount%perPageCount == 0) {
            return allRecordCount/perPageCount ;
        }
        return allRecordCount/perPageCount +1;
    }

    public boolean isHasNext(){
        if (this.page<this.getTotalPageCount()) {
            return true;
        }

        return false;
    }

    public Integer getPerPageCount() {
        if (null == perPageCount) {
            return defaultPerPageCount;
        }
        return perPageCount;
    }

    public void setPerPageCount(Integer perPageCount) {
        this.perPageCount = perPageCount;
    }



    public static PageInfoWriter create(Integer page, Integer perPageCount, Integer allRecordCount){

        PageInfoWriter pageInfo = new PageInfoWriter();
        pageInfo.page = page;
        pageInfo.allRecordCount = allRecordCount;
        pageInfo.perPageCount = perPageCount;
        return pageInfo;
    }

    public Integer getAllRecordCount() {
        return allRecordCount;
    }

    public void setAllRecordCount(Integer allRecordCount) {
        this.allRecordCount = allRecordCount;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void write(HttpServletResponse response){
        response.setHeader("X-Total-Page",this.getTotalPageCount()+"");
        response.setHeader("X-Total-Row",this.getAllRecordCount()+"");
        response.setHeader("X-Page-Row",this.getPerPageCount()+"");
        response.setHeader("X-Page-Num",this.getPage()+"");
        response.setHeader("X-Page-HasNext",this.isHasNext()+"");
    }

    public Integer getOffset(){
        if (page == null || perPageCount == null) {
           return null;
        }
        int offset = (page -1)*perPageCount;
        return offset;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }
}
