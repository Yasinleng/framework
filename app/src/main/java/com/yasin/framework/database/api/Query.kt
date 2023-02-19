package com.yasin.framework.database.api

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/15.
 */
interface Query {

    fun addOperator(operator: Operator): Query

    fun or(): Query

    /**
     * 添加排序，对 [key] 字段进行 [sortType] 方式排列。[sortType] 可使用：ASC = 0 | DESC = 1
     */
    fun orderBy(key: String, sortType: Int): Query

    fun limit(offset: Int, limit: Int): Query

    fun getOrSet(): Set<List<Operator>>

    fun getOrderByMap(): Map<String, Int>

    /**
     * OFFSET，大于 -1 生效，-1 表示不使用
     */
    fun getOffset(): Int

    /**
     * LIMIT，大于 0 生效，0 表示不使用
     */
    fun getLimit(): Int
}