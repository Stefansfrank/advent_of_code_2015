package com.sf.aoc2015

class Day22 : Solver {

    class GameState(var wizHp: Int, var bossHp: Int, var mana: Int) {

        private val label = listOf("Magic Missile", "Drain", "Shield", "Poison", "Recharge")
        private val cost  = listOf(             53,      73,      113,      173,        229)
        var effects       = mutableListOf(0,0,0,0,0) // keeping track of lasting effects of spells
        var manaLoss = 0  // total mana spent (without the recharge effects)
        var win = 0       // 1 = wizard won, 2 = boss won
        var trace = ""    // a trace variable for debugging showing the sequence of spells in clear text

        // casts spell given in normal or hard mode
        // also applies effects, executes the boss' turn and detects win/loss conditions
        // this is the core of the fight simulation
        fun cast(spell: Int, hard: Boolean):Boolean {
            if (cost[spell] > mana) return false   // too expensive
            if (effects[spell] > 1)  return false  // effect already ongoing

            // wizard move
            if (hard) if (--wizHp < 1) { win = 2; return true }
            val arm = applyEffects()

            // apply the spells
            when (spell) {
                0 -> bossHp -= 4
                1 -> { bossHp -= 2; wizHp +=2 }
                2 -> effects[2] = 6
                3 -> effects[3] = 6
                4 -> effects[4] = 5
            }
            mana -= cost[spell]
            manaLoss += cost[spell]
            trace += label[spell]

            // boss move
            applyEffects()
            if (bossHp < 1) { win = 1; return true } // boss could have lost from attack or from effects
                                                     // thus I check only after application of effects on boss' turn

            wizHp -= 9 - arm  // !!! << 9 is my input for boss damage

            // wiz lost? (wiz also looses if no more spells can be cast)
            if (wizHp < 1 || mana < 53) win = 2 else trace += " -> "
            return true
        }

        // applies any effects that might be ongoing and reduces their timers
        private fun applyEffects():Int {
            var arm = 0
            for ((ex, e) in effects.withIndex()) {
                if (e == 0) continue else when (ex) {
                    2 -> arm = 7
                    3 -> bossHp -= 3
                    4 -> mana += 101
                }
                effects[ex] -= 1
            }
            return arm
        }

        // creates an identical copy of this state
        fun copy():GameState {
            val new = GameState(wizHp, bossHp, mana)
            new.manaLoss = manaLoss
            new.effects = mutableListOf(0, 0, effects[2], effects[3], effects[4])
            new.trace = trace
            return new
        }
    }

    // BFS path finding algorithm trying each possible next spell
    // Prunings:
    // - lost games are discarded
    // - games with more mana spent than in an already identified win are discarded
    // - won games are only kept if they are better than the current best
    // - game states are discarded once all spells are evaluated and created new states
    // Less than 800 ms for both parts together on my Chromebook
    private fun findBest(inState: GameState, hard: Boolean): GameState {

        var states = mutableListOf(inState)   // open states
        var best   = GameState(0,0,0).apply { this.manaLoss = Int.MAX_VALUE }

        while (states.size > 0) {

            // pruning happens by building a new list of states that are still evaluated
            val newStates = mutableListOf<GameState>()
            for (state in states) {

                // try all spells by creating copies of the current state
                for (spell in 0..4) {
                    val nState = state.copy()
                    if (nState.cast(spell, hard)) { // the cast function returns false if the cast was too expensive
                                                    // or already in effect so these are discarded
                        if (nState.manaLoss > best.manaLoss) continue // prune games that spent too much mana already
                        when (nState.win) {
                            0 -> newStates.add(nState)
                            1 -> if (nState.manaLoss < best.manaLoss) best = nState
                        }
                    }
                }
            }
            states = newStates
        }
        return best
    }

    override fun solve(file: String) {

        // my input is in the second parameter here (boss starting hp = 51) and up in the cast() function (marked)
        var best = findBest(GameState(50, 51, 500), false)
        println("\n${bold}The player wins spending $red${best.manaLoss}$reset$bold mana casting:$reset")
        println(best.trace)
        best = findBest(GameState(50, 51, 500), true)
        println("${bold}In hard mode, he wins spending $red${best.manaLoss}$reset$bold mana casting:$reset")
        println(best.trace)
    }
}