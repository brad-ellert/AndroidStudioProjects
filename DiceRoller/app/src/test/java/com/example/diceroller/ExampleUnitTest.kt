package com.example.diceroller

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun generates_number() {
        val numSides = 6
        assertTrue("The value of rollResult was not between 1 and 6",
            Die(numSides).roll() in 1..6)
    }
}