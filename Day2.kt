package com.sf.aoc2015

class Day2 : Solver {

    private fun parseDims(inp: List<String>): List<List<Int>> {

        val dims = mutableListOf<List<Int>>()
        inp.forEach { ln ->
            val m = "(\\d+)x(\\d+)x(\\d+)".toRegex().find(ln)
            m?.groupValues?.drop(1)?.map{ it.toInt() }?.let { dims.add(it) }
        }
        return dims
    }

    // the wrap and ribbon formulas
    private fun wrap(dim: List<Int>) = 2*(dim[0]*dim[1] + dim[1]*dim[2] + dim[2]*dim[0]) + dim[0]*dim[1]*dim[2]/dim.maxOrNull()!!
    private fun ribbon(dim: List<Int>) = 2*(dim.sum() - dim.maxOrNull()!!) + dim.reduce{acc, it -> acc * it}

    override fun solve(file: String) {

        val dims = parseDims(readTxtFile(file))
        println("\nThe elfs need $red$bold${ dims.fold(0) { acc, it -> acc + wrap(it) } }$reset square inch of gift wrap")
        println("and $red$bold${ dims.fold(0) { acc, it -> acc + ribbon(it) } }$reset inch of ribbon.")

    }
}