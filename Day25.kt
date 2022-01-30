package com.sf.aoc2015

class Day25 : Solver {

    // this is the formula to get the sequence number (starting at 1) from a given row and column.
    // Each diagonal is exactly 1 longer than the one before so Gauss' sum formula can be used
    // to sum all diagonals before the one the given row & column is in.
    // The current one is defined by the sum of row and column which is constant for a diagonal.
    // Within the diagonal, the column number is synonymous with the sequence
    private fun count(row: Long, col: Long) = ((row + col) - 2) * ((row + col) - 1) / 2 + col

    // the next code
    private fun nxt(inp: Long):Long = (inp * 252533) % 33554393

    override fun solve(file: String) {
        val row = 2978L
        val col = 3083L
        var code = 20151125L

        repeat(count(row,col).toInt() - 1) { code = nxt(code) }
        println("\nThe code required is $red$bold$code$reset - Merry Christmas")
    }

}