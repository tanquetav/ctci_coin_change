package com.george.ctci.model

import java.util.*

/**
 * Represents a system state, with the change, list of coins and the list of coins use . The with* methods clone the object
 * using a shallow copy strategy, creating a new list, changing only the selected element, reusing the
 * others (immutability)
 * The heuristic function is implemented in each state. H = F + G
 * F is the 
 * G is expectation 
 */
data class CtciState(
        val change:Int,
        val coins:List<Int>,
        val uses:List<Int>,
        var f:Double =0.0,
        var g:Double =0.0
){
    val h:Double get() =  f+g
    companion object {
        fun loadFromResource(resource: String): List<CtciState> {
            val resourceAsStream = this::class.java.classLoader.getResourceAsStream(resource)
            val scan = Scanner(resourceAsStream).useDelimiter(" |\n|\r")
            var ctciStates = mutableListOf<CtciState>()
            try {
                do {
                    val change = scan.nextInt()
                    val numVals = scan.nextInt()
                    val listCoins = mutableListOf<Int>()
                    val listUses = mutableListOf<Int>()
                    for (i in 1..numVals) {
                        listCoins.add(scan.nextInt())
                        listUses.add(0)
                    }

                    ctciStates.add(CtciState(change, listCoins, listUses))
                } while (scan.hasNext())
            }
            catch(e:Exception)
            {}
            return ctciStates
        }
    }
    /**
     * Clone state changing specific coin
     */
    fun withCoin(position: Int): CtciState {
        val  newUses = LinkedList(uses)
        newUses.set(position, newUses.get(position)+1)
        return CtciState(change,coins,newUses,f,g)
    }

    fun isValid():Boolean {
        var tot = 0;
        uses.forEachIndexed{idx ,v ->
            tot = tot + v*coins[idx]
        }
        return (tot<=change)
    }
    fun isSolution():Boolean {
        var tot = 0;
        uses.forEachIndexed{idx ,v ->
            tot = tot + v*coins[idx]
        }
        return (tot==change)
    }

    /**
     * used to debug
     */
    fun diff(newState: CtciState): String {
        return ""
    }

    /**
     * Increase F
     */
    fun plusF(plusF: Double): CtciState {
        f+=plusF
        return this
    }

    /**
     * Estimated G, 
     */
    fun calculateG(): CtciState {
        g=0.0
        return this
    }

    /**
     * Sucessor of this state.  Add a coin and check if it overflow change
     */
    fun successors() :List<CtciState> {
        val lstSucessors = mutableListOf<CtciState>()

        coins.forEachIndexed {  idx,v ->
            val xx = this.withCoin(idx)
            if (xx.isValid()) {
                lstSucessors.add(xx)
            }
        }

        return lstSucessors
    }

    /**
     * Equals methods, without f and g
     */
    override fun equals(other: Any?): Boolean {
        if (other is CtciState) {
            return other.uses ==uses
        }
        return false
    }

    /**
     * Equals methods, without f and g
     */
    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + coins.hashCode()
        result = 31 * result + uses.hashCode()
        return result
    }

    /**
     * Generate solution string
     */
    fun solution(): String {
        val sb = StringBuffer()
        var totalDistance= 0.0
        sb.append("{")
        uses.forEach { c ->
            sb.append(c)
            sb.append(",")
        }
        sb.append("}\n")
        return sb.toString()
    }



}
