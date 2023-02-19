package com.yasin.framework.database.api

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/15.
 */
interface DBProvider {

    fun <T> get(clazz:Class<T>): DBTable<T>?
}