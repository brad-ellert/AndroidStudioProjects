package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

        // Do a dice roll when the app starts
        rollDice()
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides and roll the dice
        val dice = Dice(6)
        val diceRoll = dice.roll()

        // Find the ImageView in the layout
        val diceImage: ImageView = findViewById(R.id.imageView)

        // Determine which drawable resource ID to use based on the dice roll
        val drawableResource = when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        // Update the ImageView with the correct drawable resource ID
        diceImage.setImageResource(drawableResource)

        // Update the content description
        diceImage.contentDescription = diceRoll.toString()
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