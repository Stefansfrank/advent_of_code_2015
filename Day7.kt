package com.sf.aoc2015

/* Blocks
opcode 0: 123 -> x        means that the signal 123 is provided to wire x.
opcode 1: x AND y -> z    means that the bitwise AND of wire x and wire y is provided to wire z.
opcode 2: x OR y -> z     means that the bitwise OR of wire x and wire y is provided to wire z.
opcode 3: p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and then provided to wire q.
opcode 4: p RSHIFT 2 -> q means that the value from wire p is right-shifted by 2 and then provided to wire q.
opcode 5: NOT e -> f      means that the bitwise complement of the value from wire e is provided to wire f.
opcode 6: lx -> x         (not mentioned in text but occurs in input, version of 0 but with a register)
opcode 7: 1 AND y -> z    (not mentioned in text but occurs in input, version of 1 but with a register)
*/

class Day7 : Solver {

    // one operation / building block
    data class Oper(val opc: Int, val tgt: String, val src1: String, val src2: String, val vl: Int)

    // the parser is more complex than the execution code :)
    private fun parse(inp: List<String>):List<Oper> {
        val code = mutableListOf<Oper>()
        for (ln in inp) {
            val left = ln.indexOf(" -> ")
            val prefix = ln.slice(0 until left)
            val target = ln.slice(left+4 until ln.length)
            when (prefix.count { it == ' ' }) {

                // the "value -> register" block appears in my input both with a number and register on the left
                // I give them opcode 0 in the value version, opcode 6 in the register version
                0 -> if (ln[0].isDigit()) code.add(Oper(0, target, "", "", prefix.toInt()))
                        else code.add(Oper(6, target, prefix, "", 0))

                // opcode 5 for the NOT operation
                1 -> code.add(Oper(5, target, ln.slice(4 until left), "", 0))
                2 -> {

                    // all the two parameter operations
                    val op = prefix.indexOf(' ')
                    when (prefix[op+1]) {

                        // ADD also occurs as "reg ADD reg -> reg" (opcode 1) and "val add reg -> reg" (opc 7)
                        'A' -> if (ln[0].isDigit()) {
                            code.add(Oper(7, target, "", prefix.slice(op+5 until left),
                                prefix.slice(0 until op).toInt()))
                            } else { code.add(Oper(1, target, prefix.slice(0 until op),
                                prefix.slice(op+5 until left), 0))
                            }

                        // all the other operations come only in one version:
                        // "reg OR reg -> reg" (2) and "reg R/LSHIFT val -> reg" (3/4)
                        'O' -> code.add(Oper(2, target, prefix.slice(0 until op),
                                                prefix.slice(op+4 until left), 0))
                        'L' -> code.add(Oper(3, target, prefix.slice(0 until op),
                                                "", prefix.slice(op+8 until left).toInt()))
                        'R' -> code.add(Oper(4, target, prefix.slice(0 until op),
                                                "", prefix.slice(op+8 until left).toInt()))
                    }
                }
            }
        }
        return code
    }

    // putting together the network (b is the special value for b for part 2)
    private fun exec(prog:List<Oper>, b: Int):Int {

        // the registers
        val regs = mutableMapOf("" to 0)

        // I actually consume the code lines I executed, thus a copy
        var code = prog.toMutableList()
        while (code.isNotEmpty())  {
            code[0].apply {

                // if source registers are not set yet, I move this operation to the end
                // note that if I don't need them, the empty string is there and that is mapped
                if (regs[this.src1] == null || regs[this.src2] == null) {
                    code.add(this)

                // I can resolve the registers
                } else {
                    when (this.opc) {
                        0 -> {
                            regs[this.tgt] = this.vl

                            // special casing for register 'b' for part 2
                            if (this.tgt == "b" && b > 0) regs["b"] = b
                        }
                        1 -> regs[this.tgt] = regs[this.src1]!! and regs[this.src2]!!
                        2 -> regs[this.tgt] = regs[this.src1]!! or regs[this.src2]!!
                        3 -> regs[this.tgt] = regs[this.src1]!! shl this.vl
                        4 -> regs[this.tgt] = regs[this.src1]!! shr this.vl
                        5 -> regs[this.tgt] = regs[this.src1]!!.inv()
                        6 -> regs[this.tgt] = regs[this.src1]!!
                        7 -> regs[this.tgt] = regs[this.src2]!! and this.vl
                    }
                }
            }
            code = code.drop(1).toMutableList()
        }
        return regs["a"]!!
    }

    override fun solve(file: String) {

        val code = parse(readTxtFile(file))
        val p1 = exec(code, 0)
        println("\nRegister 'a' shows signal $red$bold$p1$reset at the end")
        println("The signal on that line is $red$bold${exec(code, p1)}$reset if the above result is fed to 'b'")
    }
}