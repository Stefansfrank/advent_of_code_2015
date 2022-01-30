package com.sf.aoc2015

class Day23 : Solver {

    // one operation
    private data class Operation(val opcode: String, val reg: Char, val jmp: Int)

    // parsing the code into the structure above
    private fun parse(inp: List<String>):List<Operation> {
        val reOp = "^[a-z]{3}".toRegex()
        val re1  = " [a-z]$".toRegex()
        val re2  = " [+-]\\d+$".toRegex()
        val re3  = " ([a-z]), ([+-]\\d+)$".toRegex()

        val code = mutableListOf<Operation>()
        for (ln in inp) {
            val mo = reOp.find(ln)
            if (mo != null) {
                when (mo.value) {
                    in listOf("jio", "jie") -> {
                        val m3 = re3.find(ln)
                        if (m3 != null)
                            code.add(Operation( mo.value, m3.groupValues[1][0], m3.groupValues[2].toInt() ))
                    }
                    "jmp" -> {
                        val m2 = re2.find(ln)
                        if (m2 != null)
                            code.add(Operation( "jmp", ' ', m2.value.drop(1).toInt()))
                    }
                    else -> {
                        val m1 = re1.find(ln)
                        if (m1 != null)
                            code.add(Operation( mo.value, m1.value.drop(1)[0], 0))
                    }
                }
            }
        }

        return code
    }

    // running the code is pretty straightforward
    private fun run(code: List<Operation>, aStart: Int):Map<Char, Int> {
        val regs = mutableMapOf( 'a' to aStart, 'b' to 0 )
        var ip   = 0
        while (ip in code.indices) {
            when (code[ip].opcode) {
                "hlf" -> regs[code[ip].reg] = regs[code[ip++].reg]!! / 2
                "tpl" -> regs[code[ip].reg] = regs[code[ip++].reg]!! * 3
                "inc" -> regs[code[ip].reg] = regs[code[ip++].reg]!! + 1
                "jmp" -> ip += code[ip].jmp
                "jie" -> ip += if (regs[code[ip].reg]!! % 2 == 0) code[ip].jmp else 1
                "jio" -> ip += if (regs[code[ip].reg]!! == 1) code[ip].jmp else 1
            }
        }
        return regs
    }

    override fun solve(file: String) {

        val code = parse(readTxtFile(file))

        // Part 1
        val regs = run(code, 0)
        println("\nStarting with a = 0 and b = 0 the code finishes with $red$bold${regs['b']}$reset in register b")

        // Part 2
        val regs2 = run(code, 1)
        println("Starting with a = ${bold}1$reset and b = 0 makes register b finish with $red$bold${regs2['b']}$reset")
    }
}