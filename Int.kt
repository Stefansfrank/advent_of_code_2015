package com.sf.aoc2015

// returns all permutations of a list of Int
fun perm(lst: List<Int>):List<List<Int>> {
    if (lst.size == 1) return listOf(listOf(lst[0]))
    val perms = mutableListOf<List<Int>>()
    for (i in lst) {
        val mlst = perm(lst.filter { it != i })
        for (ml in mlst) {
            perms.add(listOf(i) + ml)
        }
    }
    return perms
}

