package com.sf.aoc2015

class Day6 : Solver {

    private data class Instr(val typ: Int, val range: Rect)

    // parses the input into a more convenient format
    private fun parse(inp: List<String>):List<Instr> {
        val re = "^(toggle|turn off|turn on) (\\d+),(\\d+) through (\\d+),(\\d+)$".toRegex()
        val instructions = mutableListOf<Instr>()
        for (ln in inp) {
            val match = re.find(ln)
            match?.groupValues!!.let { instructions.add(Instr ( when (it[1]) {
                "toggle"  -> 0
                "turn on" -> 1
                else      -> 2
            }, Rect(XY(it[2].toInt(), it[3].toInt()), XY(it[4].toInt(), it[5].toInt()) )))}
        }
        return instructions
    }

    override fun solve(file: String) {

        val instr = parse(readTxtFile(file))

        // part 1 - using a mask (details in 2D.kt)
        val msk = Mask(1000, 1000)
        for (i in instr) when (i.typ) {
            0 -> msk.tgl(i.range)
            1 -> msk.on(i.range)
            2 -> msk.off(i.range)
        }
        println("\nTotal lights shining: $red$bold${ msk.cnt() }$reset")

        // part 2 - using an integer map (details in 2D.kt)
        val mp = MapInt(1000, 1000)
        for (i in instr) when (i.typ) {
            0 -> mp.add(2, i.range)
            1 -> mp.add(1, i.range)
            2 -> mp.sub(1, i.range, true)
        }
        println("Total brightness: $red$bold${ mp.cnt() }$reset")
    }
}