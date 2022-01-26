package com.sf.aoc2015

class Day10 : Solver {

    // straightforward simple string traversal
    private fun step(inp: List<Int>): List<Int> {
        var cv = inp[0]  // the current value
        var cn = 0       // the amount of occurrences
        val next = mutableListOf<Int>()
        for (c in inp) {
           if (c == cv) cn++ else {
               next.add(cn.also { cn = 1 })
               next.add(cv.also { cv = c })
           }
        }
        next.add(cn)
        next.add(cv)
        return next
    }

    override fun solve(file: String) {

        // my input (= Conway's Mg element)
        var input = listOf(1,1,1,3,2,2,2,1,1,3)
        repeat(40) { input = step(input) }
        println("\nAfter 40 iterations, the value has $red$bold${input.size}$reset digits.")
        repeat(10) { input = step(input) }
        println("Another 10 iterations create a total of $red$bold${input.size}$reset digits.")
    }
}