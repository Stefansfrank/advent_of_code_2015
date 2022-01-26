package com.sf.aoc2015

class Day5 : Solver {

    override fun solve(file:String) {

        val inp = readTxtFile(file)
        var niceCnt = 0

        // rules 1
        for (ln in inp) {

            // condition 1
            val m = "[aeiou]".toRegex().findAll(ln)
            if (m.count() < 3) continue

            // condition 2
            var pair = false
            for (ix in 0 until ln.length - 1) pair = pair || (ln[ix] == ln[ix+1])
            if (!pair) continue

            // condition 3
            if (ln.contains("ab") || ln.contains("cd") ||
                    ln.contains("pq") || ln.contains("xy")) continue

            // passed all
            niceCnt += 1
        }

        println("\nWith the first rule set, Santa identifies $red$bold${niceCnt.also { niceCnt = 0 }}$reset nice words")

        // rules 2
        for (ln in inp) {

            // condition 1
            var pair = false
            for (ix in 0 until ln.length - 3) {
                val pr = ln.slice(ix .. ix+1)
                pair = pair || (ln.slice(ix+2 until ln.length).contains(pr))
            }
            if (!pair) continue else

            // condition 2
            pair = false
            for (ix in 0 until ln.length - 2) pair = pair || (ln[ix] == ln[ix+2])
            if (!pair) continue

            // passed all
            niceCnt += 1
        }

        println("With the improved rule set only $red$bold$niceCnt$reset nice words are found.")

    }
}