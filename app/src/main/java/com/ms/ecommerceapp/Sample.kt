package com.ms.ecommerceapp

class Sample: A() {
    var objectClass = B()
    var result = ""

    fun main() {
        var  result = objectClass.printClassName()
    }

    override fun printClassName() {
        println("Name: Sample $result")
    }
}

//Parent Class
open class A {
    open var name: String = "Class A"

    open fun printClassName() {
        println("Name: $name")
    }

}

//Child 1 --> Derived from parent class A
open class B: A() {
    override var name: String = "Class B"

    override fun printClassName() {
        println("Name: $name")
    }
}

//Child 2 --> Derived from parent class A
open class C: A() {
    //override var name: String = "Class C"

    override fun printClassName() {
        println("Name: $name")
    }
}

// This class derived from class B and C ---> This is called Diamond problem in Inheritance
// In java dosen't support Multiple Inheritance
/*open class Child : B(),C() {
    //var child = Child().printClassName()
    override fun printClassName() {
        TODO("Not yet implemented")
    }

//    override fun printClassName() {
//        TODO("Not yet implemented")
//    }
}*/

interface GrandParent {
    fun printInterface() {
        println("Grand Parent Interface")
    }

    fun check()
}

interface CheckInterface {
    fun check() {
        println("checkInterface")
    }
}

interface GreatGrandParent: GrandParent, CheckInterface {
    override fun printInterface() {
        println("GreatGrandParent Interface")
    }

    override fun check() {
        println("GreatGrandParent Check called")
    }

    fun printInterface1() {
        println("Parent Interface")
    }
}

open class Parent1: GrandParent, GreatGrandParent {
    override fun printInterface() {
        super<GrandParent>.printInterface()
    }
    // This function which interface method is override --> from interface GrandParent or GreatGrandParent ?
    override fun check() {
        println("Parent1 Check called")
    }

    fun getGreatGrandParent() {
        super<GreatGrandParent>.printInterface()
        check()
    }

}

open class Parent2: GreatGrandParent {

}

class child: Parent1() {

    override fun printInterface() {
        //super<Parent1>.printInterface()
        println("Child Interface")
    }

    fun main() {
        var child = child()
        child.printInterface()
    }
}