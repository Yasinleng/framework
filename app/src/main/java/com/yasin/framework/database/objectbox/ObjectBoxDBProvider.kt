package com.yasin.framework.database.objectbox

import android.content.Context
import com.yasin.framework.database.api.DBProvider
import com.yasin.framework.database.api.DBTable
import io.objectbox.BoxStore

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/15.
 */
class ObjectBoxDBProvider(context: Context): DBProvider {

    private var boxStore: BoxStore?=null

    init {
//        boxStore = MyObjectBox.builder()
//            .androidContext(context)
//            .build()
    }

    override fun <T> get(clazz:Class<T>): DBTable<T> {

        TODO()
//        return ObjectBoxDBTable(boxStore?.boxFor(clazz))clazz
    }
}