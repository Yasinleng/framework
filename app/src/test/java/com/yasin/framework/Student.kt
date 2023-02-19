package com.yasin.framework

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2023/2/16.
 */
data class Student(
    var sid: Int,
    var name: String = "",
    var desc: String = ""
) {

    override fun equals(other: Any?): Boolean {
        return if (other is Student){
            other.sid==sid
        }else{
            false
        }

    }

    override fun hashCode(): Int {
        return sid.hashCode()
    }
}