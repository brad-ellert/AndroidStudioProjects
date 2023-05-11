package com.example.diceroller

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun generates_number() {
        val die = Die(6)
        val rollResult = die.roll()
        assertTrue("The value of rollResult was not between 1 and 6", rollResult in 1..6)
    }
}