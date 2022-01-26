package com.sf.aoc2015

import kotlin.math.max
import kotlin.math.min

class Day9 : Solver {

    private data class Geo(val names: List<String>, val dist: MapInt, val num: Int)

    // parser creates 2d map of int containing the distance between city x and city y in dist.mp[y][x]
    // additionally a names list mapping the city index to the name (not needed in the puzzle but ..)
    // in order to avoid lengthy code, this assumes the total number of cities to be known and given as input
    private fun parse(inp:List<String>, num:Int):Geo {
        val ck = mutableMapOf<String, Int>()
        val names = mutableListOf<String>()
        val dist = MapInt(num, num)
        for (ln in inp) {
            val m = "(\\w+) to (\\w+) = (\\d+)".toRegex().find(ln)?.groupValues
            var ix1: Int
            var ix2: Int
            if (m != null) {
                ix1 = if (ck[m[1]] == null) {
                    ck[m[1]] = names.size
                    names.add(m[1])
                    names.size -1
                } else ck[m[1]]!!
                ix2 = if (ck[m[2]] == null) {
                    ck[m[2]] = names.size
                    names.add(m[2])
                    names.size -1
                } else ck[m[2]]!!
                dist.mp[ix1][ix2] = m[3].toInt()
                dist.mp[ix2][ix1] = m[3].toInt()
            }
        }
        return Geo(names, dist, num)
    }

    // since there are only 8 cities in the input, computing all possible
    // permutations and compute the fast calculation is possible in under 500 ms
    override fun solve(file: String) {

        val numCities = 8                               // the number of cities
        val geo   = parse(readTxtFile(file), numCities) // read and parse the distance table
        val perms = perm(List(numCities){ it })         // compute all permutations of an int array [0..numCities]

        var mn = 1_000_000
        var mx = 0
        var sm:Int
        for (p in perms) {
            sm = (1 until numCities).fold(0) { acc, i -> acc + geo.dist.mp[p[i]][p[i - 1]] }
            mn = min(mn, sm)
            mx = max(mx, sm)
        }
        println("\nThe shortest route Santa can take is of length $red$bold$mn$reset")
        println("The longest road on the other hand is $red$bold$mx$reset")
    }

}