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
 * In this Activity, a user can roll a die and the result is displayed on the screen.
 */
class MainActivity : AppCompatActivity() {
    private val die = Die(6)
    private lateinit var diceImage: ImageView

    /**
     * The onCreate() function is a lifecycle function of an Android Activity.
     * It is called when the Activity is first created.
     * In this function, we set the UI for the Activity and initialize the roll button.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the ImageView in the layout
        diceImage = findViewById(R.id.imageView)

        // Reference to the "Roll" button in the layout
        val rollButton: Button = findViewById(R.id.button)

        // Set a click listener on the Roll button. When clicked, a toast message is shown and the
        // rollDice() function is called.
        rollButton.setOnClickListener {
            Toast.makeText(this, "Die Rolled!", Toast.LENGTH_SHORT).show()
            rollDice()
        }

        // Do a die roll when the app starts
        rollDice()
    }

    /**
     * Roll the die and update the screen with the result.
     */
    private fun rollDice() {
        // Roll the die
        val diceRoll = die.roll()

        // Update the ImageView with the correct drawable resource ID based on the die roll
        diceImage.setImageResource(
            resources.getIdentifier("dice_$diceRoll", "drawable", this.packageName)
        )

        // Update the content description
        diceImage.contentDescription = diceRoll.toString()
    }
}

/**
 * The Die class represents a die which can be rolled to generate a random number.
 * @property numSides The number of sides on the die.
 */
class Die(private val numSides: Int) {

    /**
     * The roll() function generates a random number between 1 and the number of sides on the die.
     * @return A random number between 1 and the number of sides on the die.
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}