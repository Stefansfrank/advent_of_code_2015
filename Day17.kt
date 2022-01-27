package com.sf.aoc2015

class Day17 : Solver {

    override fun solve(file: String) {

        // parse in the containers given
        val containers = readTxtFile(file).map { it.toInt() }

        // create the number of permutations
        val perms = (1L shl containers.size) - 1L            // a number with as many bits as containers all set one
        var result = 0                                       // the count of valid solutions (i.e. 150L)
        val numCnt = MutableList(containers.size) { 0 } // the amount of solutions with 'ix' containers used

        // count from zero to the max number above interpreting 1s in the binary representation as filled containers
        for (p in 0L .. perms) {
            var bitIndex  = 0
            val vol = containers.fold(0) { acc, cont ->
                acc + cont * if ((p and (1L shl bitIndex++)) > 0) 1 else 0
            }
            if (vol == 150) {
                result += 1 // for part 1
                numCnt[p.countOneBits()] += 1 // for part 2
            }
        }

        println("\nThere are $red$bold$result$reset ways to fill the eggnog in the containers")
        println("There are $red$bold${numCnt.first { it > 0 }}$reset ways to fill the eggnog " +
                "into the minimum amount of containers (${numCnt.indices.first{ numCnt[it] > 0 }})")

    }
}