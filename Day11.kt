package com.sf.aoc2015

class Day11 : Solver {

    // counts the occurrences of double letters
    private fun dblCnt(inp:String):Int {
        return "(\\w)\\1".toRegex().findAll(inp).count()
    }

    // checks for the required sequence of three increasing letter
    // maybe this one could be faster ...
    private fun seqDet(inp:String):Boolean {
        var cc = inp[0].code
        var cn = 1
        (1 until inp.length).forEach {
            if (inp[it].code == cc + cn) {
                cn += 1
                if (cn == 3) return true
            } else {
                cc = inp[it].code
                cn = 1
            }
        }
        return false
    }

    // increases the number using the reduced alphabet
    private fun iterate(inp: String, nextAZ: Map<Char,Char>):String {
        val cinp = inp.toCharArray()
        for (i in inp.length-1 downTo 0) {
            cinp[i] = nextAZ[cinp[i]]!!
            if (cinp[i] != 'a') return String(cinp)
        }
        return "a${String(cinp)}"
    }

    override fun solve(file: String) {

        // a reduced alphabet for faster iteration
        val nxt = mutableMapOf<Char,Char>()
        ('a' .. 'y').forEach {
            nxt[it] = it + if (it == 'h' || it == 'k' || it == 'n') 2 else 1
        }.also { nxt['z'] = 'a' }

        var inp = "cqjxjnds" // my puzzle input

        // Part 1
        // the solution checks for doubles and sequences after ever iteration
        // if I would run into performance problems, I would detect whether
        // the changed letter would have a chance to make the password valid by
        // keeping score of doubles and sequences ... but this runs fast enough
        while (dblCnt(inp) < 2 || !seqDet(inp))
            inp = iterate(inp, nxt)
        println("\nSanta's next password is $red$bold$inp$reset")

        // Part 2
        inp = iterate(inp, nxt)
        while (dblCnt(inp) < 2 || !seqDet(inp))
            inp = iterate(inp, nxt)
        println("The one after is $red$bold$inp$reset")
    }

}