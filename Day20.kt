package com.sf.aoc2015

import kotlin.math.min
import kotlin.math.sqrt

class Day20 : Solver {

    // the part 1 packets count is a regular brut force divisor search
    // using the square root limit counting two factors at once
    // an optimization would be to use prime number factoring to determine
    // divisors but this was fast enough - maybe I'll get to it one of these days
    private fun packets(house: Int):Int {
        val lmt = sqrt(house.toDouble()).toInt()
        var num = 1 + house
        for (i in 2..lmt) if (house % i == 0) num += i + house/i
        if (lmt * lmt == house) num -= lmt // prevent double counting
        return num * 10
    }

    // part 2 packet counter benefits from the fact that I always compute
    // both factors, so I can limit the first factor to 50 and count the second factor
    // could be faster for numbers over 2500 by removing two more checks and hardcode the limit to 50
    // but it's already super fast
    private fun packets2(house: Int):Int {
        val lmt = min(sqrt(house.toDouble()).toInt(), 50)
        var num = 1 + house
        for (i in 2..lmt) if (house % i == 0) {
            val i2 = house/i
            num += i2
            if (i2 <= 50) num += i
        }
        if (lmt * lmt == house) num -= lmt
        return num * 11
    }

    override fun solve(file: String) {

        // Part 1
        var numP = 0
        var house = 100
        while (numP < 36_000_000) // my puzzle input
            numP = packets(++house)
        println("\nThe elves deliver $numP to house $red$bold$house$reset")

        // Part 2
        numP = 0
        house = 100
        while (numP < 36_000_000) // my puzzle input
            numP = packets2(++house)
        println("With a 50 houses limit, $numP packages get to house $red$bold$house$reset")
    }

}