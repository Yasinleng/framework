package com.yasin.framework.database.api

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/15.
 */
interface DBTable<T> {

    /**
     * 插入数据
     */
    fun add(data: T): Long

    /**
     * 批量插入数据
     */
    fun addAll(vararg data: T)

    /**
     * 批量插入数据
     */
    fun addAll(dataList: MutableList<T>)

    /**
     * 删除数据
     */
    fun del(data: T)

    /**
     * 批量删除数据
     */
    fun del(vararg data: T)

    /**
     * 批量删除数据
     */
    fun del(dataList: MutableList<T>)

    /**
     * 查询数据
     */
    fun query(param: Query): MutableList<T>

    /**
     * 获取所有数据
     */
    fun getAll(): MutableList<T>

    /**
     * 更新数据
     */
    fun update(data: T)

    /**
     * 批量更新数据
     */
    fun update(vararg data: T)

    /**
     * 批量更新数据
     */
    fun update(dataList: MutableList<T>)


}