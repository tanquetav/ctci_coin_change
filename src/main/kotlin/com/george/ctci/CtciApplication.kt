package com.george.ctci

import com.george.ctci.algorithm.AStar
import com.george.ctci.model.CtciState


class CtciApplication {

}

fun main(args: Array<String>) {
        val ctiStates = CtciState.loadFromResource("data.txt")
        ctiStates.forEach { ctiState ->
            val aStar = AStar(ctiState)
            val newState = aStar.run()
            if (newState!=null) {
                println(newState.change)
            }
        }
}
