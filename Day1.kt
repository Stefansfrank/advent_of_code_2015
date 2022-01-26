package com.sf.aoc2015

class Day1 : Solver {

    // determines Santa's floor based on the string
    private fun floor(s: String) = s.count { it == '(' } - s.count { it == ')' }

    // main function
    override fun solve(file: String) {

        val inp = readTxtFile(file)[0]

        println("\nAt the end Santa is at floor $red$bold${floor(inp)}$reset")
        for (ix in 1 .. inp.length) if (floor(inp.take(ix)) < 0) {
            println("After changing floors $red$bold$ix$reset times, Santa enters the basement")
            return
        }

    }

}