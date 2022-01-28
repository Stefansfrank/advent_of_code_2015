package com.sf.aoc2015

import kotlin.math.max

class Day21 : Solver {

    data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)
    data class Shop(val weapons: List<Item>, val armour: List<Item>, val rings: List<Item>)

    private fun createShop():Shop {
        val wp = mutableListOf<Item>()
        wp.add(Item("Dagger", 8, 4, 0))
        wp.add(Item("Shortsword", 10, 5, 0))
        wp.add(Item("Warhammer", 25, 6, 0))
        wp.add(Item("Longsword", 40, 7, 0))
        wp.add(Item("Greataxe", 74, 8, 0))

        val arm = mutableListOf<Item>()
        arm.add(Item("Null", 0, 0, 0))
        arm.add(Item("Leather", 13, 0, 1))
        arm.add(Item("Chainmail", 31, 0, 2))
        arm.add(Item("Splintmail", 53, 0, 3))
        arm.add(Item("Bandedmail", 75, 0, 4))
        arm.add(Item("Platemail", 102, 0, 5))

        val rng = mutableListOf<Item>()
        rng.add(Item("Null", 0, 0, 0))
        rng.add(Item("Null", 0, 0, 0))
        rng.add(Item("Damage +1", 25, 1, 0))
        rng.add(Item("Damage +2", 50, 2, 0))
        rng.add(Item("Damage +3", 100, 3, 0))
        rng.add(Item("Defense +1", 20, 0, 1))
        rng.add(Item("Defense +2", 40, 0, 2))
        rng.add(Item("Defense +3", 80, 0, 3))

        return Shop(wp, arm, rng)
    }

    // the player with equipment and the attack computation
    data class Player(var hp: Int, val att: Int, val def: Int) {

        private var wp: Item = Item("Null",0,0,0)
        private var arm: Item = Item("Null",0,0,0)
        private var rng1: Item = Item("Null",0,0,0)
        private var rng2: Item = Item("Null",0,0,0)

        private var eqAtt = att
        private var eqDef = def
        private val origHP = hp

        private fun calcEq() {
            eqAtt = att + wp.damage + rng1.damage + rng2.damage
            eqDef = def + arm.armor + rng1.armor + rng2.armor
        }

        fun equip(weapon: Item, armor: Item, ring1: Item, ring2: Item) {
            wp = weapon; arm = armor; rng1 = ring1; rng2 = ring2
            calcEq()
        }

        fun getHit(enemy: Player):Boolean {
            hp -= max(enemy.eqAtt - this.eqDef,1)
            return (hp < 1)
        }

        fun resetHP() {
            hp = origHP
        }
    }

    private fun fight(me: Player, boss: Player):Boolean {
        var lost: Boolean
        while (true) {
            lost = boss.getHit(me)
            if (lost) return true
            lost = me.getHit(boss)
            if (lost) return false
        }
    }

    override fun solve(file: String) {

        // create all the given data
        val shop = createShop()
        val me   = Player(100, 0, 0)

        // my input for the boss
        val boss = Player(103, 9, 2)

        // loop through all combinations of equipment
        // note that I added free zero effect armour and two free zero effect rings to the shop
        // in order to simulate not equipping these without null checks in the code
        var minCost = 1000000
        var maxCost = 0
        for (w in shop.weapons) {
            for (a in shop.armour) {
                for ((i1, r1) in shop.rings.withIndex()) {
                    for (i2 in i1 + 1 until shop.rings.size) {

                        // equip the player
                        me.equip(w, a, r1, shop.rings[i2])
                        val cost = w.cost + a.cost + r1.cost + shop.rings[i2].cost

                        // fight
                        val win = fight(me, boss)

                        // maintain result relevant maximum / minimum
                        if (win && cost < minCost) minCost = cost
                        else if (!win && cost > maxCost) maxCost = cost

                        // reset the players for another round
                        me.resetHP(); boss.resetHP()
                    }
                }
            }
        }

        // results
        println("\nThe minimum expense needed in order to win against the boss is $red$bold$minCost$reset gold")
        println("The maximum gold the shop keeper can get me to spend while still loosing is $red$bold$maxCost$reset")
    }
}