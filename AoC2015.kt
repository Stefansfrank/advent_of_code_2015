package com.sf.aoc2015

import kotlin.reflect.full.createInstance

const val red = "\u001b[31m"
const val bold = "\u001b[1m"
const val reset = "\u001b[0m"
const val green = "\u001b[32m"

interface Solver {
    fun solve(file: String)
}

fun main(args: Array<String>) {

    val start = System.nanoTime()

    val kClass = Class.forName("com.sf.aoc2015.Day${args[0]}").kotlin
    kClass.members.filter { it.name == "solve" }[0].call( kClass.createInstance(), "data/d${args[0]}.${args[1]}.txt" )

    println("\nElapsed time: $green${"%,d".format(System.nanoTime()-start)}$reset ns")
}