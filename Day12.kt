package com.sf.aoc2015

class Day12 : Solver {

    // ues only in part 2
    // identifies bracket expressions (list or groups) and reduces them to their sum value
    // it tries to find a bracket expression that is complete (i.e. no inner bracket expressions)
    // replaces the whole expression with its sum (0 if a group and contains "red") and
    // starts anew for the resulting string
    // it ends if there are no curled brackets left (the outermost bracket in my input is curled)
    private fun cntInp(inp: String):Int {

        var redInp = inp  // the input string that will be modified as expressions are simplified
        var open  = -1    // the position of the last open bracket identified
        while (redInp.indexOf('{') > - 1) {
            for ((i, c) in redInp.withIndex()) {
                when (c) {
                    '{' -> open = i
                    '[' -> open = i
                    ']' -> {  // this assumes that the input is well-formed i.e. the last open bracket matches
                        redInp = redInp.take(open) +
                                cntNum(redInp.slice(open..i), true).toString() +
                                redInp.takeLast(redInp.length - 1 - i)
                        break
                    }
                    '}' -> {  // this assumes that the input is well-formed i.e. the last open bracket matches
                        redInp = redInp.take(open) +
                                cntNum(redInp.slice(open..i), false).toString() +
                                redInp.takeLast(redInp.length - 1 - i)
                        break
                    }
                }
            }
        }
        return cntNum(redInp, true)
    }

    // counts all numbers in a group
    // returns 0 if 'list' is false and "red" occurs in it
    private fun cntNum(inp: String, list: Boolean):Int {
        return if (!list && inp.contains("red")) 0 else {
            val ms = "-?\\d+".toRegex().findAll(inp)
            ms.fold(0) { acc, it -> acc + it.value.toInt() }
        }
    }

    override fun solve(file: String) {

        val inp = readTxtFile(file)[0]

        println("\nAll numbers in the document add up to $red$bold${cntNum(inp, true)}$reset")
        println("Removing the groups in red, $red$bold${cntInp(inp)}$reset is the new total")
    }
}