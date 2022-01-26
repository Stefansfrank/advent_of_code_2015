package com.sf.aoc2015

import java.math.BigInteger
import java.security.MessageDigest

class Day4 : Solver {

    private fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }

    // brute force solution using the build in MD5 computation, nothing special about this
    // expect run time to be in the tens of seconds (15 sec on my 2019 MB Pro i7)
    override fun solve(file:String) {
        val secret = readTxtFile(file)[0]

        var cnt = 0
        while (true)
            if (md5Hash(secret + (++cnt).toString()).substring(0 until 5) == "00000") {
                println("\nThe lowest number postfixed to the secret delivering an MD5 starting with 00000 is " +
                        "$red$bold$cnt$reset")
                break
            }

        cnt -= 1  // don't need to do the first computations a second time as 6 zeros would have shown up
                  // just trying the part 1 result once more in case it already has 6 leading zeros.
        while (true)
            if (md5Hash(secret + (++cnt).toString()).substring(0 until 6) == "000000") {
                println("The lowest number postfixed to the secret delivering an MD5 starting with 000000 is " +
                        "$red$bold$cnt$reset")
                return
            }
    }
}