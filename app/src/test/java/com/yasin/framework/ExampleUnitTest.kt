package com.yasin.framework

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testList() {

        var list1 = mutableListOf<Student>()
        val list2 = mutableListOf<Student>()

        for (i in 0..10) {
            list1.add(Student(i, "名称===$i", "描述===$i"))
        }

//        println(list1)

        for (i in 7..15) {
            list2.add(Student(i, "名称***$i", "描述***$i"))
        }
//        println(list2)
        val temp=list2.union(list1)
        println(temp)
        println("===================================================================")
        val test=temp
            .distinctBy { it.sid }
            .filter {
                println("==${it}============filter======")
                it.sid==4
            }
            .sortedWith(compareBy( { it.sid },{it.name}))

        println(test)
        println("===================================================================")
        println(list2)



    }

    @Test
    fun testrepeat(){
        repeat(5){
            if (it==4) return@repeat
            println("=============$it============================")
        }

        println("=============testrepeat============================")
    }

}