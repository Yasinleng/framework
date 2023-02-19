package com.yasin.framework.database.api

import java.util.LinkedHashSet

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/16.
 */
class QueryParams:Query {

    private var andListTemp = ArrayList<Operator>()
    private val orSet = LinkedHashSet<ArrayList<Operator>>()
    private val orderByMap = HashMap<String, Int>()
    private var offset = -1
    private var limit = 0


    override fun addOperator(operator: Operator): Query {
        andListTemp.add(operator)
        if (orSet.size == 0) {
            orSet.add(andListTemp)
        }
        return this
    }

    override fun or(): Query {
        andListTemp = ArrayList()
        orSet.add(andListTemp)
        return this
    }

    override fun orderBy(key: String, sortType: Int): Query {
        orderByMap[key] = sortType
        return this
    }

    override fun limit(offset: Int, limit: Int): Query {
        this.offset = offset
        this.limit = limit
        return this
    }

    override fun getOrSet(): Set<List<Operator>> {
        return orSet
    }

    override fun getOrderByMap(): Map<String, Int> {
        return orderByMap
    }

    override fun getOffset(): Int {
        return offset
    }

    override fun getLimit(): Int {
        return limit
    }
}