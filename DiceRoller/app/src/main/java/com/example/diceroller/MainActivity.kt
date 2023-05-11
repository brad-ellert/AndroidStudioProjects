package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

/**
 * MainActivity is the primary UI (User Interface) for our dice roller application.
 * This Activity extends AppCompatActivity, making it compatible with older Android devices.
 * In this Activity, a user can roll a dice and the result is displayed on the screen.
 */
class MainActivity : AppCompatActivity() {

    /**
     * The onCreate() function is a lifecycle function of an Android Activity.
     * It is called when the Activity is first created.
     * In this function, we set the UI for the Activity and initialize the roll button.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Reference to the "Roll" button in the layout
        val rollButton: Button = findViewById(R.id.button)

        // Set a click listener on the Roll button. When clicked, a toast message is shown and the rollDice() function is called.
        rollButton.setOnClickListener {
            Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT).show()
            rollDice()
        }
    }

    /**
     * The rollDice() function is responsible for rolling a dice and updating the UI with the result.
     * It creates a new Dice object with 6 sides and rolls it. The result of the roll is then displayed on a TextView.
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides and roll it
        val dice = Dice(6)
        val diceRoll = dice.roll()

        // Find the TextView in the layout, then change its text to the result of the dice roll
        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.text = diceRoll.toString()
    }
}

/**
 * The Dice class represents a dice which can be rolled to generate a random number.
 * @property numSides The number of sides on the dice.
 */
class Dice(private val numSides: Int) {

    /**
     * The roll() function generates a random number between 1 and the number of sides on the dice.
     * @return A random number between 1 and the number of sides on the dice.
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}