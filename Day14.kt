package com.sf.aoc2015

import kotlin.math.min

class Day14 : Solver {

    // the reindeer with the provided data in the primary (Ren is German for reindeer)
    data class Ren(val speed: Int, val burst: Int, val rest: Int, val name: String) {

        private val period = burst + rest // the time period of the repetitive cycle
        private val dist = burst * speed  // the distance travelled during one repetitive cycle
        var points = 0                    // a points counter externally accessible (for part 2)

        // this function computes the distance travelled after the given time using the following facts:
        // - there is a periodic behavior of fixed distance
        // - there is only movement in the first part of the unfinished last period
        // this function is O(1) on time
        fun distAfter(time: Int) = (time / period) * dist + min((time % period), burst) * speed
    }

    // parsing the input into the Ren structure above
    private fun parse(inp: List<String>):List<Ren> {
        val re = "^(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.$".toRegex()
        val rens = mutableListOf<Ren>()
        for (ln in inp) {
            val match = re.find(ln)?.groupValues
            if (match != null) rens.add(Ren( match[2].toInt(), match[3].toInt(), match[4].toInt(), match[1]))
        }
        return rens
    }

    override fun solve(file: String) {

        val rens = parse(readTxtFile(file))
        val cut  = 2503 // cutoff

        // simple use of the Ren.distAfter(time)
        val ren1 = rens.maxByOrNull { it.distAfter(cut) }!!
        println("\n$bold${ren1.name.uppercase()}$reset is in front after "
                    + "$cut seconds at km $red$bold${ren1.distAfter(cut)}$reset")

        // instead of an evolutionary simulation of the race, the O(1) time dependency of Ren.distAfter(time)
        // allows to always calculate the positions of each reindeer for each time in (1..2503) sec from 0
        // both parts together run under 400 ms on my MB Pro 2019
        for (time in 1..cut) rens.maxByOrNull { it.distAfter(time) }!!.points += 1
        val ren2 = rens.maxByOrNull { it.points }!!
        println("$bold${ren2.name.uppercase()}$reset has led $red$bold${ren2.points}$reset seconds of that race " +
                    "- more than any other reindeer")
    }
}