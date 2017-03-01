package com.taotao.search.pojo;

import java.util.List;

/**
 * @ClassName SearchResult
 * @Author volc
 * @Description 搜索结果集
 * @Date 2017年3月1日 下午8:24:42
 */
public class SearchResult {
    private Long total;
    private List<?> data;

    public Long getTotal() {
	return total;
    }

    public void setTotal(Long total) {
	this.total = total;
    }

    public List<?> getData() {
	return data;
    }

    public SearchResult() {
    }

    public SearchResult(Long total, List<?> data) {
	this.total = total;
	this.data = data;
    }

    @Override
    public String toString() {
	return "SearchResult [total=" + total + ", data=" + data + "]";
    }

    public void setData(List<?> data) {
	this.data = data;
    }

}
