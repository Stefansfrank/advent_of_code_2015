package com.sf.aoc2015

class Day3 : Solver {

    override fun solve(file:String) {

        val cDir = mapOf( '^' to 0, '>' to 1, 'v' to 2, '<' to 3)

        var loc = XY(0, 0)
        val homes = mutableMapOf(loc to 1)
        for (c in readTxtFile(file)[0])
            homes[loc] = homes.getOrDefault(loc.let { loc = it.mv(cDir[c]!!); loc}, 0) + 1

        println("\nSanta brings gifts to $red$bold${homes.size}$reset homes")

        var loc1 = XY(0, 0)
        var loc2 = XY(0, 0)
        val homes2 = mutableMapOf(loc1 to 1)
        for (c in readTxtFile(file)[0].chunked(2)) {
            homes2[loc1] = homes2.getOrDefault(loc1.let { loc1 = it.mv(cDir[c[0]]!!); loc1}, 0) + 1
            homes2[loc2] = homes2.getOrDefault(loc2.let { loc2 = it.mv(cDir[c[1]]!!); loc2}, 0) + 1
        }

        println("With the help of Robo-Santa $red$bold${homes2.size}$reset homes are visited")

    }
}
