package com.sf.aoc2015

class Day19 : Solver {

    data class React(val from: String, val to:String)
    data class Input(val target: String, val reacts:List<React>)

    // parses the input into the data classes above
    // 'target' is the molecule and 'reacts' the reaction rules
    private fun parse(inp: List<String>):Input {
        val reacts = mutableListOf<React>()
        for (ln in inp) {
            val m = "^(\\w+) => (\\w+)$".toRegex().find(ln)?.groupValues
            if (m != null) reacts.add(React(m[1], m[2]))
        }
        return Input(inp.last(), reacts)
    }

    override fun solve(file: String) {
        val inp = parse(readTxtFile(file))

        // Part 1 - a simple brute force approach using a set to get rid of duplicates implicitly
        val new = mutableSetOf<String>()
        for (r in inp.reacts) {
            var ix = inp.target.indexOf(r.from)
            while (ix > -1) {
                new.add(inp.target.take(ix) + r.to + inp.target.takeLast(inp.target.length - ix - r.from.length))
                ix = inp.target.indexOf(r.from, ix + 1)
            }
        }
        println("\nThere are $red$bold${new.size}$reset different ways to modify the molecule in the next step")

        // Part 2 - a solution by analysing the rules:
        // First we count all elements with an element being 1 or 2 letters with the second lowercase if present
        // If we look at the reaction rules, we can learn:
        // - Ar, Rn, and Y only occur as a result of a reaction and are inert
        // - Ar and Rn always occur together and Y only if Ar and Rn are present
        // - if Ar/Rn/Y are not present, the result of a reaction always consists of 2 elements
        // - the presence of Ar and Rn only (no Y) features also only 2 elements (other than Ar and Rn)
        // - for each Y present another element is added to the reaction results
        // With all of these observations, we can basically say:
        // - each reaction without Ar/Rn involved increases the number of elements by 1
        // - each reaction with Ar/Rn involved increases the number of elements by another 2 (Ar and Rn)
        // - each reaction with Y increases the outcome by another 2 (Y and the additional element)
        // In reverse, in order to get n elements down to 1 element, you basically need:
        // (n-1) steps minus the additional "savings" from the longer Ar/Rn/Y reactions
        // The Ar/Rn/Y reactions happen only as often as these characters are found in the end product
        // since they have no way to vanish again
        // so the number of steps is then: (n - 1) - #ofOccurencesOfAr * 2 - #ofOccurencesOfY * 2
        val elemCnt = "[A-Z]".toRegex().findAll(inp.target).count() // counting uppercase letters = elements
        val arRnCnt = "Rn".toRegex().findAll(inp.target).count()    // counting one of Ar/Rn is enough (always pairs)
        val yCnt = "Y".toRegex().findAll(inp.target).count()        // counting Y
        println("It takes at least $red$bold${elemCnt - 1 - 2 * arRnCnt - 2 * yCnt}$reset " +
                "steps to synthesize the molecule")
    }
}