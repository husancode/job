package yofoto.issue.controller;

import java.util.Collections;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import yofoto.issue.util.Page;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author husan
 * @Date 2013-2-18
 * @description
 */
public abstract class JqGridBaseAction<T> extends ActionSupport {
	
	private List<T> gridModel = Collections.emptyList();   
    private Integer rows = 0;   //每页记录数
    private Integer page = 0;   //当前页码
    private Long total = 0L;   //总页数
    private Long records = 0L;   //记录总数
    private String sord;   //排序的方式
    private String sidx;   //用于排序的列名
    
    private String oper;  //表格操作
    
 // (1)添加和查询有关的成员变量_search、searchField、searchString、searchOper 
    private boolean _search;   //是否是用于查询的请求 
    private String searchField;   
    private String searchString;   
    private String searchOper; 
    private String filters;
    
    protected boolean _search1;
    
    public abstract Page<T> listByPage(Page<T> pages); 
    
    public abstract Page<T> searchPage(Page<T> pages,String filter);
    
    //添加常用字段查询
    //public abstract Page<T> searchPage(Page<T> page);
    
    public String refreshGridModel() {   
        try { 
        	
            List<T> results = Collections.emptyList();   
            Page<T> pages=new Page<T>();
            pages.setPageSize(rows);
            pages.setPageNo(page);
            pages.setOrder(sord);
            pages.setOrderBy(sidx);
           
            if(_search||_search1){
            	pages = searchPage(pages,filters);
            }else{
            	 pages = listByPage(pages);
            }
            records=pages.getTotalCount(); 
       
            results = pages.getResult();
            this.setGridModel(results);   
            total = pages.getTotalPages();
            return "json";   
        } catch (Exception e) {   
            e.printStackTrace();   
            this.addActionError(e.getMessage());   
            return ERROR;   
        }   
    } 
   
	public List<T> getGridModel() {
		return gridModel;
	}
	public void setGridModel(List<T> gridModel) {
		this.gridModel = gridModel;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public boolean is_search() {
		return _search;
	}
	public void set_search(boolean search) {
		_search = search;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}

	public boolean is_search1() {
		return _search1;
	}

	public void set_search1(boolean search1) {
		_search1 = search1;
	} 
    
    

}
