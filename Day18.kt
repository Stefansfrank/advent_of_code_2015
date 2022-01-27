package com.sf.aoc2015

class Day18 : Solver {

    // parses into Mask class (see 2D.kt)
    private fun parse(inp:List<String>):Mask {
        val xDim = inp.size
        val yDim = inp[0].length
        val board  = Mask(xDim+2, yDim+2)
        for ((y, ln) in inp.withIndex())
            ln.forEachIndexed{ x,c -> if (c=='#') board.msk[y+1][x+1] = true }
        return board
    }

    // executes one step of Game of Life setting the corners if 'mod' is on (part 2)
    private fun step(board: Mask, corners: Boolean) {
        val snap = board.snap()
        for (y in 1 .. board.ydim - 2) {
            for (x in 1..board.xdim - 2) {
                val sum = XY(x,y).neighbors(true).fold(0) { acc, xy ->
                    acc + if (snap[xy.y][xy.x]) 1 else 0
                }
                board.msk[y][x] = (sum == 3 || (sum == 2 && snap[y][x]))
            }
        }
        if (corners) board.setCorners()
    }

    // helper to set the corners in a padded mask
    private fun Mask.setCorners():Mask {
        msk[1][1] = true
        msk[ydim - 2][1] = true
        msk[1][xdim - 2] = true
        msk[ydim - 2][xdim - 2] = true
        return this
    }

    override fun solve(file: String) {
        val inp = readTxtFile(file)

        // Part 1
        val board = parse(inp)
        repeat(100 ) { step(board, false) }
        println("\nThe fully working light matrix has $red$bold${board.cnt()}$reset lit lights after 100 steps")

        // Part 2
        val board2 = parse(inp).setCorners()
        repeat(100 ) { step(board2, true) }
        println("With frozen corner lights, the matrix has $red$bold${board2.cnt()}$reset lamps lit after 100 steps")
    }

}