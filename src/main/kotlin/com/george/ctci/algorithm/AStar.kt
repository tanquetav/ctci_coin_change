package com.george.ctci.algorithm

import com.george.ctci.model.CtciState

/**
 * A* algorithm implementation
 * Try to search best the solution. Starting with initial state, successors call change one truck collecting one cargo
 * to successor states. This new states are put in openSet and a new state is selected using the Heuristic Function
 */
class AStar (val ctciState: CtciState) {
    /**
     * states already scanned
     */
    var closedSet = mutableListOf<CtciState>()
    /**
     * states to be scanned
     */
    var openSet = mutableListOf<CtciState>()
    /**
     * graph of pair of states representing where each state came from
     * Not used in this case, because the solution state already
     * have the information of which truck collected each cargo
     */
    var cameFrom = mutableMapOf<CtciState, CtciState>()

    /**
     * A* implementation
     */
    fun run() : CtciState?{
        // Put initial state in openset
        openSet.add(ctciState)
        var currentResult: CtciState? = null
        var numResults = 0;
        while (openSet.size>0) {
            // Select state with best heuristic
            val currentNode: CtciState = openSet
                    .sortedBy { it.h }
                    .first()
            openSet.remove(currentNode)
            closedSet.add(currentNode)
//            var ss2 = ""
//            currentNode.cargos.forEach {
//                ss2+=it.delivered
//            }

            // If this is the solution , all cargos collected
            if ( currentNode.isSolution() ) {
//                return currentNode
 // Used to check all solutions  in debug
                    //println("changed")
                currentResult = currentNode
                numResults++
                println(currentNode)
            }
            val successorList = currentNode.successors()
            successorList.forEach { neighbor ->
                // already closed states are discarted
                if (!closedSet.contains(neighbor)) {
                    // if not in openSet , put in
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor)
                        cameFrom[neighbor] = currentNode
                    }
                    else {
                        // if in openSet , check if heuristic is lower that previous, and fix cameFrom map
                        val otherEqualNeighborPosition = openSet.indexOf(neighbor)
                        val otherEqualNeighbor= openSet[otherEqualNeighborPosition ]
                        if (neighbor.h < otherEqualNeighbor.h) {
                            otherEqualNeighbor.f = neighbor.f
                            otherEqualNeighbor.g = neighbor.g
                            cameFrom[otherEqualNeighbor] = currentNode
                        }
                    }
                }
            }
        }
        println(numResults)
        return currentResult
    }
}