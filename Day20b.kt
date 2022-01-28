package com.sf.aoc2015

class Day20b : Solver { // alternative solution for Day 20 part 1

    // This an alternative solution for part 1 where I use a prime number table
    // to compute prime factorization and then get the divisors by the permutations
    // of the prime factors. It ends up to be wash with the brut force solution used in Day20.kt
    // since there is overhead in the now three steps of prime table creation, factorization and permutation .
    // On my Chromebook, brute force solves this in ~2.5 sec and this in ~3 sec

    // given that part 2 is much easier to add and optimize in brute force, I did not implement it here

    // The prime table creation, prime factorization and factor permutations code is in Int.kt

    override fun solve(file: String) {

        // Part 1
        val primes = primes(1_000)
        var house = 100000
        var numP  = 0
        while (numP < 36_000_000) numP = 10 * divisors(primeFac(++house, primes)).sum()
        println("\nThe fancy solution also finds $red$bold$house$reset as getting $numP packages")

    }

}