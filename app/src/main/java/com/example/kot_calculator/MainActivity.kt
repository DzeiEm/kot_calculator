package com.example.kot_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {


    var isNumber: Boolean = false
    var dot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onDigitTap(view: View) {
//         taip gaunam teksta is mygtuko. kas parasyta nat mygtuko tas ir atvaizduota
        digitDisplayOnTheScreen.append((view as Button).text)
        isNumber = true

    }

    fun onClear(view: View) {
//        paspaudus clear, prilyginam viska tuscia stringui
        digitDisplayOnTheScreen.text = ""
        isNumber = false
        dot = false
    }

    fun onDecimalPoint(view: View) {
        if (isNumber && !dot) {
            digitDisplayOnTheScreen.append(".")
            isNumber = false
            dot = true
        }
    }

    //    patikrinam ar screen'e dega kaip paskutinis skaicius skaicius ar operatorius
    fun onEqual(view: View) {
        if (isNumber) {
            var value = digitDisplayOnTheScreen.text.toString()
            var prefix = "-"
            try {
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1);
                }
                if (value.contains("-")) {
                    val splitValue = value.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    digitDisplayOnTheScreen.text =
                        removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                } else if (value.contains("*")) {
                    val splitValue = value.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    digitDisplayOnTheScreen.text =
                        removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                } else if (value.contains("/")) {
                    val splitValue = value.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    digitDisplayOnTheScreen.text =
                        removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                } else if (value.contains("+")) {
                    val splitValue = value.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    digitDisplayOnTheScreen.text =
                        removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onOperator(view: View) {
        if (isNumber && !isOperatorAdded(digitDisplayOnTheScreen.text.toString())) {
            digitDisplayOnTheScreen.append((view as Button).text)
            isNumber = false
            dot = false
        }
    }

    fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }
}

