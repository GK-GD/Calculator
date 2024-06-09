package com.example.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {

    private val _equationText = MutableLiveData("")
    val equationText: LiveData<String> = _equationText

    private val _resultText = MutableLiveData("0")
    val resultText: LiveData<String> = _resultText

    fun onButtonClick(btn: String) {
        Log.i("Clicked Button", btn)

        _equationText.value?.let {
            when (btn) {
                "AC" -> {
                    _equationText.value = ""
                    _resultText.value = "0"
                    return
                }
                "C" -> {
                    if (it.isNotEmpty()) {
                        _equationText.value = it.substring(0, it.length - 1)
                    }
                    return
                }
                "=" -> {
                    _equationText.value = _resultText.value
                    return
                }
                else -> _equationText.value = it + btn
            }
            // Calculate result
            try {
                _resultText.value = calculateResult(_equationText.value.toString())
            } catch (e: Exception) {
                _resultText.value = "Error"
            }
        }
    }

    private fun calculateResult(equation: String): String {
        return try {
            val expression = ExpressionBuilder(equation).build()
            val result = expression.evaluate()
            if (result % 1.0 == 0.0) {
                result.toLong().toString() // Display as integer if there's no decimal part
            } else {
                result.toString()
            }
        } catch (e: Exception) {
            "Error"
        }
    }
}
