package com.yasin.framework.database.objectbox

import com.yasin.framework.database.api.DBTable
import com.yasin.framework.database.api.Operator
import com.yasin.framework.database.api.OperatorType
import com.yasin.framework.database.api.Query
import io.objectbox.Box
import io.objectbox.Property
import io.objectbox.query.QueryBuilder
import java.util.*

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/15.
 */
class ObjectBoxDBTable<T>(private val box: Box<T>) : DBTable<T> {
    override fun add(data: T): Long {
        return box.put(data)
    }

    override fun addAll(vararg data: T) {
        box.put(*data)
    }

    override fun addAll(dataList: MutableList<T>) {
        box.put(dataList)
    }

    override fun del(data: T) {
        box.remove(data)
    }

    override fun del(vararg data: T) {
        box.remove(*data)
    }

    override fun del(dataList: MutableList<T>) {
        box.remove(dataList)
    }

    override fun query(param: Query): MutableList<T> {
        var queryBuilder = box.query()

        val orSize = param.getOrSet().size
        var orCount = 0
        for (list in param.getOrSet()) {
            val adnSize = list.size
            var andCount = 0
            when (adnSize) {
                0 -> continue
                else -> {
                    for (op in list) {
                        queryBuilder = when (op.type) {
                            OperatorType.GT -> op.gtQB(box, queryBuilder)
                            OperatorType.LT -> op.ltQB(box, queryBuilder)
                            OperatorType.EQ -> op.eqQB(box, queryBuilder)
                            OperatorType.NE -> op.notEqQB(box, queryBuilder)
                            OperatorType.LIKE -> queryBuilder.contains(op.toProperty(box), op.value as String, QueryBuilder.StringOrder.CASE_SENSITIVE)
                            OperatorType.NOT_NULL -> queryBuilder.notNull(op.toProperty(box))
                            OperatorType.IS_NULL -> queryBuilder.isNull(op.toProperty(box))
                            else->{
                                throw IllegalArgumentException("ObjectBoxDBTable no support")
                            }

                        }
                        andCount++
                        if (andCount < adnSize) {
                            queryBuilder = queryBuilder.and()
                        }
                    }
                }
            }

            orCount++
            if (orCount < orSize) {
                queryBuilder = queryBuilder.or()
            }
        }


        for (entry in param.getOrderByMap()) {
            queryBuilder = if (entry.value == 0) {
                queryBuilder.order(entry.key.toProperty(box))
            } else {
                queryBuilder.orderDesc(entry.key.toProperty(box))
            }
        }

        val query = queryBuilder.build()
        if (param.getOffset() > -1 && param.getLimit() > 0) {
            return query.find(param.getOffset().toLong(), param.getLimit().toLong())
        }
        return query.find()
    }

    override fun getAll(): MutableList<T> {
        return box.all
    }

    override fun update(dataList: MutableList<T>) {
        box.put(dataList)
    }

    override fun update(vararg data: T) {
        box.put(*data)
    }

    override fun update(data: T) {
        box.put(data)
    }

    @Throws(IllegalArgumentException::class)
    private fun Operator.ltQB(box: Box<T>, qb: QueryBuilder<T>): QueryBuilder<T> {
        for (p in box.entityInfo.allProperties) {
            if (p.name == this.key) {
                return when (p.type) {
                    Long::class.java -> qb.less(p, this.value as Long)
                    Date::class.java -> qb.less(p, this.value as Date)
                    String::class.java -> qb.less(
                        p,
                        this.value as String,
                        QueryBuilder.StringOrder.CASE_SENSITIVE
                    ) // 区分大小写
                    Double::class.java -> qb.less(p, this.value as Double)
                    ByteArray::class.java -> qb.less(p, this.value as ByteArray)
                    else -> throw IllegalArgumentException("Cannot match value type")
                }
            }
        }
        throw IllegalArgumentException("Cannot found Property by ${this.key}")
    }


    @Throws(IllegalArgumentException::class)
    private fun Operator.gtQB(box: Box<T>, qb: QueryBuilder<T>): QueryBuilder<T> {
        for (p in box.entityInfo.allProperties) {
            if (p.name == this.key) {
                return when (p.type) {
                    Long::class.java -> qb.greater(p, this.value as Long)
                    Date::class.java -> qb.greater(p, this.value as Date)
                    String::class.java -> qb.greater(p, this.value as String, QueryBuilder.StringOrder.CASE_SENSITIVE)
                    Double::class.java -> qb.greater(p, this.value as Double)
                    ByteArray::class.java -> qb.greater(p, this.value as ByteArray)
                    else -> throw IllegalArgumentException("Cannot match value type")
                }
            }
        }
        throw IllegalArgumentException("Cannot found Property by ${this.key}")
    }

    @Throws(IllegalArgumentException::class)
    private fun Operator.eqQB(box: Box<T>, qb: QueryBuilder<T>): QueryBuilder<T> {
        for (p in box.entityInfo.allProperties) {
            if (p.name == this.key) {
                return when (p.type) {
                    Long::class.java -> qb.equal(p, this.value as Long)
                    Date::class.java -> qb.equal(p, this.value as Date)
                    String::class.java -> qb.equal(p, this.value as String, QueryBuilder.StringOrder.CASE_SENSITIVE)
                    Double::class.java -> qb.equal(p, this.value as Double, this.value as Double)
                    else -> throw IllegalArgumentException("Cannot match value type")
                }
            }
        }
        throw IllegalArgumentException("Cannot found Property by ${this.key}")
    }

    @Throws(IllegalArgumentException::class)
    private fun Operator.notEqQB(box: Box<T>, qb: QueryBuilder<T>): QueryBuilder<T> {
        for (p in box.entityInfo.allProperties) {
            if (p.name == this.key) {
                return when (p.type) {
                    Long::class.java -> qb.notEqual(p, this.value as Long)
                    Boolean::class.java -> qb.notEqual(p, this.value as Boolean)
                    Date::class.java -> qb.notEqual(p, this.value as Date)
                    String::class.java -> qb.notEqual(
                        p,
                        this.value as String,
                        QueryBuilder.StringOrder.CASE_SENSITIVE
                    ) // 区分大小写
                    else -> throw IllegalArgumentException("Cannot match value type")
                }
            }
        }
        throw IllegalArgumentException("Cannot found Property by ${this.key}")
    }


    @Throws(IllegalArgumentException::class)
    private fun Operator.toProperty(box: Box<T>): Property<T> {
        return this.key.toProperty(box)
    }


    @Throws(IllegalArgumentException::class)
    private fun String.toProperty(box: Box<T>): Property<T> {
        for (p in box.entityInfo.allProperties) {
            if (p.name == this) {
                return p
            }
        }
        throw IllegalArgumentException("Cannot found Property by $this")
    }
}