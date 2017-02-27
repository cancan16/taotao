package com.taotao.manage.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {

    // 定义抽象方法用于获取指定类型的通用Mapper对象，子类进行实例化返回给这里的父类
    // public abstract Mapper<T> getMapper();

    /**
     * @Fields mapper 注入通用mapper实体对象
     */
    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据id查询数据
     * 
     * @param id
     * @return
     */
    public T queryById(Long id) {
	return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * @Title selectById @Description 根据主键查询 @param id @return T @throws
     */
    public T selectById(Long id) {
	// 调用子类实现的getmapper返回的用用mapper实例。调用通用方法
	return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * @Title queryAll @Description 查询所有数据 @return List<T> @throws
     */
    public List<T> queryAll() {
	return this.mapper.select(null);
    }

    /**
     * @Title queryOne @Description 根据条件查询查询一条数据 @param record @return T @throws
     */
    public T queryOne(T record) {
	return this.mapper.selectOne(record);
    }

    /**
     * @Title queryListByWhere @Description 根据条件查询所有数据 @param record @return
     *        List<T> @throws
     */
    public List<T> queryListByWhere(T record) {
	return this.mapper.select(record);
    }

    /**
     * @Title queryPageListByWhere @Description 通用分页根据条件查询数据返回list并分页 @param
     *        page @param rows @param record @return PageInfo<T> @throws
     */
    public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T record) {
	PageHelper.startPage(page, rows);
	List<T> list = this.mapper.select(record);
	return new PageInfo<T>(list);
    }

    /**
     * @Title save 
     * @Description 选择不为空的字段作为插入字段使用insertSelective保存一条数据返回受影响行数
     * @param record
     * @return Integer
     * @throws
     */
    public Integer save(T record) {
	record.setCreated(new Date());
	record.setUpdated(record.getCreated());
	return this.mapper.insert(record);
    }

    /**
     * @Title saveSelective @Description
     *        选择不为空的字段作为插入字段使用insertSelecttive保存一条数据返回受影响行数 @param
     *        record @return Integer @throws
     */
    public Integer saveSelective(T record) {
	record.setCreated(new Date());
	record.setUpdated(record.getCreated());
	return this.mapper.insertSelective(record);
    }

    /**
     * @Title udpate @Description 根据主键更新一条数据 @param record @return
     *        Integer @throws
     */
    public Integer udpate(T record) {
	record.setUpdated(new Date());
	return this.mapper.updateByPrimaryKey(record);
    }

    /**
     * @Title updateSelective @Description 根据主键选择性更新 @param record @return
     *        Integer @throws
     */
    public Integer updateSelective(T record) {
	record.setUpdated(new Date());
	return this.mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @Title deleteById @Description 根据主键删除 @param id @return Integer @throws
     */
    public Integer deleteById(Long id) {
	return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * @Title delectByIds @Description 批量删除 @param clazz @param property @param
     *        values @return Integer @throws
     */
    public Integer delectByIds(Class<T> clazz, String property, List<Object> values) {
	Example example = new Example(clazz);
	example.createCriteria().andIn(property, values);
	return this.mapper.deleteByExample(example);
    }

    /**
     * @Title deleteByWhere @Description 根据条件删除数据 @param record @return
     *        Integer @throws
     */
    public Integer deleteByWhere(T record) {
	return this.mapper.delete(record);
    }

}
