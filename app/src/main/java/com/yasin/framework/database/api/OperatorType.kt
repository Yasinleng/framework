package com.yasin.framework.database.api

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/15.
 */
enum class OperatorType {

    /**
     * 大于
     */
    GT,

    /**
     * 小于
     */
    LT,

    /**
     * 大于等于
     */
    GE,

    /**
     * 小于等于
     */
    LE,

    /**
     * 等于
     */
    EQ,

    /**
     * 不等于
     */
    NE,

    /**
     * 类似
     */
    LIKE,

    /**
     * 不为Null
     */
    NOT_NULL,

    /**
     * 为Null
     */
    IS_NULL
}