package com.sf.aoc2015

class Day8 : Solver {

    private data class Result(val dec: Int, val inc: Int)
    private fun count(inp:List<String>):Result {
        val clean = mutableListOf<String>()
        var dec = 0 // the decease in char count for part 1
        var inc = 0 // the increase in char count for part 2
        for (ln in inp) {
            var ix = 0
            dec += 2 // start & end quotes
            inc += 4 //
            while (true) {

                // I am deliberately manually go through the string left to right catching escapes
                // in order to avoid matches within matches ("\\x34" should only result in one \\ escape, not in two"
                val six = ln.drop(ix).indexOf('\\')
                if (six == -1) {
                    break
                } else {
                    when (ln[ix + six + 1]) {
                        '\\' -> { dec += 1; inc += 2; ix += six + 2 }
                        '\"' -> { dec += 1; inc += 2; ix += six + 2 }
                        'x'  -> { dec += 3; inc += 1; ix += six + 4 }
                    }
                }
            }
        }
        return Result(dec, inc)
    }

    override fun solve(file: String) {
        val inp = readTxtFile(file)
        println("\nThe reduction in chars due to escape resolution is $red$bold${count(inp).dec}$reset")
        println("The increase in chars due to escaping special chars is $red$bold${count(inp).inc}$reset")
    }


}