/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    /**
     * Anything labeled var instead of val is expected to be changed in the functions but DO NOT
     * alter their initial values declared here, this could cause the app to not function properly.
     */
    companion object {
        private const val LEMONADE_STATE = "LEMONADE_STATE"
        private const val LEMON_SIZE = "LEMON_SIZE"
        private const val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    }

    enum class LemonadeState {
        SELECT,
        SQUEEZE,
        DRINK,
        RESTART
    }


    // Default the state to select
    private var lemonadeState = LemonadeState.SELECT

    // Default lemonSize to -1
    private var lemonSize = -1

    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            lemonadeState =
                savedInstanceState.getString(LEMONADE_STATE)?.let { LemonadeState.valueOf(it) }
                    ?: LemonadeState.SELECT
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage?.setOnClickListener {
            clickLemonImage()
        }
        lemonImage?.setOnLongClickListener {
            showSnackbar()
        }
    }

    /**
     * This method saves the state of the app if it is put in the background.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState.name)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * Clicking will elicit a different response depending on the state.
     * This method determines the state and proceeds with the correct action.
     */
    private fun clickLemonImage() {
        when (lemonadeState) {
            LemonadeState.SELECT -> {
                lemonSize = lemonTree.pick()
                squeezeCount = 0
                lemonadeState = LemonadeState.SQUEEZE
            }

            LemonadeState.SQUEEZE -> {
                squeezeCount++
                lemonSize--
                if (lemonSize == 0) {
                    lemonadeState = LemonadeState.DRINK
                    lemonSize = -1
                }
            }

            LemonadeState.DRINK -> {
                lemonadeState = LemonadeState.RESTART
            }

            LemonadeState.RESTART -> {
                lemonadeState = LemonadeState.SELECT
            }
        }
        setViewElements()
    }

    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)
        var text: Int = 0
        var image: Int = 0
        when (lemonadeState) {
            LemonadeState.SELECT -> {
                text = R.string.lemon_select
                image = R.drawable.lemon_tree
            }

            LemonadeState.SQUEEZE -> {
                text = R.string.lemon_squeeze
                image = R.drawable.lemon_squeeze
            }

            LemonadeState.DRINK -> {
                text = R.string.lemon_drink
                image = R.drawable.lemon_drink
            }

            LemonadeState.RESTART -> {
                text = R.string.lemon_empty_glass
                image = R.drawable.lemon_restart
            }
        }
        textAction.setText(text)
        lemonImage?.setImageResource(image)
    }

    /**
     * Long clicking the lemon image will show how many times the lemon has been squeezed.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != LemonadeState.SQUEEZE) {
            return false
        }
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            getString(R.string.squeeze_count, squeezeCount),
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

/**
 * A Lemon tree class with a method to "pick" a lemon. The "size" of the lemon is randomized
 * and determines how many times a lemon needs to be squeezed before you get lemonade.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
