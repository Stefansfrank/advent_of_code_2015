package com.sf.aoc2015

import kotlin.math.max

class Day13 : Solver {

    private data class Family(val names: List<String>, val happy: MapInt, val num: Int)

    // parser creates 2d map of int containing the happiness of person y if x sits next (happy.mp[y][x])
    // additionally a names list mapping the person index to the name (not needed in the puzzle but ..)
    // in order to avoid lengthy code, this assumes the total number of cities to be known and given as input
    // Note that for part 2 it's fine to just increase the number of people by one as the default value is 0
    // for all connections and is changed only if the input file contains a different spec (which it doesn't for me)
    private fun parse(inp:List<String>, num:Int):Family {
        val ck = mutableMapOf<String, Int>()
        val names = mutableListOf<String>()
        val happy = MapInt(num, num)
        for (ln in inp) {
            val match = "(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+)".toRegex().find(ln)?.groupValues
            var ix1: Int
            var ix2: Int
            if (match != null) {
                ix1 = if (ck[match[1]] == null) {
                    ck[match[1]] = names.size
                    names.add(match[1])
                    names.size - 1
                } else ck[match[1]]!!
                ix2 = if (ck[match[4]] == null) {
                    ck[match[4]] = names.size
                    names.add(match[4])
                    names.size - 1
                } else ck[match[4]]!!
                happy.mp[ix1][ix2] = match[3].toInt() * if (match[2] == "gain") 1 else - 1
            }
        }
        return Family(names, happy, num)
    }

    // since there are only 8/9 family members in the input, computing all possible
    // permutations should be fine in terms of performance (9! = ~350K permutations)
    override fun solve(file: String) {

        val numPeople = 8                                // the number of people
        val family = parse(readTxtFile(file), numPeople) // read and parse the happy factors from the input
        val perms = perm(List(numPeople){ it })          // compute all permutations of an int array [0..numPeople]

        var mx = 0
        for (p in perms) mx = max(mx, (1 until numPeople).fold(family.happy.mp[p[0]][p[numPeople -1]] +
                                                        family.happy.mp[p[numPeople - 1]][p[0]])
                { acc, i -> acc + family.happy.mp[p[i]][p[i - 1]] + family.happy.mp[p[i - 1]][p[i]] })
        println("\nThe most happiness achievable with 8 is $red$bold$mx$reset")

        val numPeople2 = 9
        val family2 = parse(readTxtFile(file), numPeople2) // the default values of 0 take care of the additional guest
        val perms2 = perm(List(numPeople2){ it })

        mx = 0
        for (p in perms2) mx = max(mx, (1 until numPeople2).fold(family2.happy.mp[p[0]][p[numPeople2 -1]] +
                    family2.happy.mp[p[numPeople2 - 1]][p[0]])
            { acc, i -> acc + family2.happy.mp[p[i]][p[i - 1]] + family2.happy.mp[p[i - 1]][p[i]] })
        println("The most happiness achievable adding myself is $red$bold$mx$reset")

    }

}