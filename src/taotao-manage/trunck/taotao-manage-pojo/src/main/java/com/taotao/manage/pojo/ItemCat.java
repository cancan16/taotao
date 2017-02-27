package com.taotao.manage.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_item_cat")
public class ItemCat extends BasePojo {

    // 指定主键和主键自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Boolean isParent;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getParentId() {
	return parentId;
    }

    public void setParentId(Long parentId) {
	this.parentId = parentId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Integer getSortOrder() {
	return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
	this.sortOrder = sortOrder;
    }

    public Boolean getIsParent() {
	return isParent;
    }

    public void setIsParent(Boolean isParent) {
	this.isParent = isParent;
    }

    // 扩展EasyUI中tree需要的字段，text
    public String getText() {
	return this.getName();
    }

    // 扩展tree需要的state字段，用字叶子节点和子节点的状态
    public String getState() {
	return this.getIsParent() ? "closed" : "open";
    }

}
