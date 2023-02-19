package com.yasin.framework.database

import android.content.Context
import com.yasin.framework.database.api.DBProvider
import com.yasin.framework.database.api.DBTable
import com.yasin.framework.database.objectbox.ObjectBoxDBProvider

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/15.
 */
object DatabaseHandle {
    lateinit var provider: DBProvider

    fun init(context: Context) {
        provider = ObjectBoxDBProvider(context)
    }

    fun <T> get(clazz:Class<T>): DBTable<T>? {
        return provider.get(clazz)
    }
}