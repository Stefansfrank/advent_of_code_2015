package com.sf.aoc2015

class Day15 : Solver {

    // parsing the input into the Ren structure above
    private fun parse(inp: List<String>):List<Day14.Ren> {
        val re = "^(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.$".toRegex()
        val rens = mutableListOf<Day14.Ren>()
        for (ln in inp) {
            val match = re.find(ln)?.groupValues
            if (match != null) rens.add(Day14.Ren(match[2].toInt(), match[3].toInt(), match[4].toInt(), match[1]))
        }
        return rens
    }

    override fun solve(file: String) {
        TODO("Not yet implemented")
    }
}