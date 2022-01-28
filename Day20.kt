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

    // part 2 packet counter can be streamlined from the above as we only count
    // factors where the second factor is <= 50, so we can stop at 50 for one factor
    // for large numbers and also omit the double counting check for large numbers
    // NOTE: this works only correctly for numbers over 2500!!
    private fun packets2(house: Int):Int {
        var num = 1 + house
        for (i in 2..50) if (house % i == 0) num += house/i
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
        house = 2500 // the optimized package counter works correctly only for n > 2500
        while (numP < 36_000_000) // my puzzle input
            numP = packets2(++house)
        println("With a 50 houses limit, $numP packages get to house $red$bold$house$reset")
    }

}