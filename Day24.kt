package com.sf.aoc2015

class Day24 : Solver {

    // recursive function to compute all combinations of n elements in a list was added to Int.kt
    // it's called 'combinations' below

    override fun solve(file: String) {
        val packs = readTxtFile(file).map { it.toLong() }.sortedDescending()
        val total = packs.sum()

        // part 1
        val tgt = total/3
        val opts = mutableListOf<List<Long>>()

        // looking at the heaviest packages, at least 5 are needed
        combinations(packs, 5).forEach { if (it.sum() == tgt) opts.add(it) }
        if (opts.size == 0) {

            // if I didn't find one with 5, I'll try 6
            combinations(packs, 6).forEach { if (it.sum() == tgt) opts.add(it) }
        }
        println("\nThe quantum entanglement of the minimum package solution for 3 piles is " +
                "$red$bold${opts.minOf { it.fold(1L){ acc, it -> acc * it } }}$reset")

        // Part 2
        val tgt2 = total/4
        val opts2 = mutableListOf<List<Long>>()

        // similar to the above, at least 4 packages are needed but I'll try 5 if nothing found
        combinations(packs, 4).forEach { if (it.sum() == tgt2) opts2.add(it) }
        if (opts2.size == 0) {
            combinations(packs, 5).forEach { if (it.sum() == tgt2) opts2.add(it) }
        }
        println("For 4 piles the entanglement decreases to " +
                "$red$bold${opts2.minOf { it.fold(1L){ acc, it -> acc * it } }}$reset")
    }
}