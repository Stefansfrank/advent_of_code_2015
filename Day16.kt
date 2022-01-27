package com.sf.aoc2015

class Day16 : Solver {

    private fun parse(inp: List<String>, index: Map<String, Int>):List<List<Int>> {
        // Typical line: "Sue 79: pomeranians: 9, cars: 1, perfumes: 0"

        val things = mutableListOf<List<Int>>()
        for ((ix, ln) in inp.withIndex()) {

            // check whether the input is in fact sequential by aunt nubmer
            if (ln.substring(4 until ln.indexOf(':')).toInt() != ix+1) println("WARNING")

            // parse things (adding a trailing comma in order to make the regex simpler)
            val ms = " (\\w+): (\\d+),".toRegex().findAll("$ln,")
            val thing = MutableList(10){ -1 }
            for (m in ms) thing[index[m.groupValues[1]]!!] = m.groupValues[2].toInt()
            things.add(thing)
        }

        return things
    }

    override fun solve(file: String) {

        // giving each 'thing' an index, so we can represent things / aunts with List<Int>
        // aligned in a way that the special cases in part 2 are at the beginning / end
        val index = mapOf("trees" to 0, "cats" to 1, "samoyeds" to 2, "cars" to 3,
            "akitas" to 4, "vizslas" to 5, "perfumes" to 6, "children" to 7, "pomeranians" to 8, "goldfish" to 9)

        // the numbers required in the text
        val required = listOf(3,7,2,2,0,0,1,3,3,5)

        // parsing the input file
        val things = parse(readTxtFile(file), index)

        // part 1 - simple loop
        for ((aunt, aThings) in things.withIndex()) {
            val rightAunt = (0..9).fold(true) { acc, ix ->
                acc && (aThings[ix] == -1 || required[ix] == aThings[ix])}
            if (rightAunt) {
                println("\nThe best match sems to be aunt $red$bold${aunt+1}$reset at first")
                break
            }
        }

        // part 2 - checking the 10 conditions in three subgroups
        for ((aunt, aThings) in things.withIndex()) {
            var rightAunt = (0..1).fold(true) { acc, ix ->
                acc && (aThings[ix] == -1 || required[ix] < aThings[ix])}
            rightAunt = rightAunt && (2..7).fold(true) { acc, ix ->
                acc && (aThings[ix] == -1 || required[ix] == aThings[ix])}
            rightAunt = rightAunt && (8..9).fold(true) { acc, ix ->
                acc && (aThings[ix] == -1 || required[ix] > aThings[ix])}
            if (rightAunt) {
                println("With the modified interpretation it's actually aunt $red$bold${aunt+1}$reset")
                break
            }
        }

    }
}