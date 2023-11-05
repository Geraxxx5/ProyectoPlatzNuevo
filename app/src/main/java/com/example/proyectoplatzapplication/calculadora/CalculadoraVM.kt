package com.example.myapplication.calculadora

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculadoraVM: ViewModel() {
    var state by mutableStateOf(CalculadoraS())
    private set

    fun onAction(action : CalculadoraA){
        when(action){
            is CalculadoraA.Number -> enterNumber(action.number)
            is CalculadoraA.Decimal -> enterDecimal()
            is CalculadoraA.Clear -> state =  CalculadoraS()
            is CalculadoraA.Operation -> enterOperation(action.operation)
            is CalculadoraA.Calculate-> performCalculation()
            is CalculadoraA.Delete ->performDeletion()
        }
    }

    private fun performDeletion() {
        when{
            state.num2.isNotBlank()-> state = state.copy(
                num2 =state.num2.dropLast(1)
            )
            state.operation != null -> state =state.copy(
                operation=null
            )
            state.num1.isNotBlank()-> state = state.copy(
                num1 =state.num1.dropLast(1)
            )
        }
    }

    private fun performCalculation() {
        val num1 = state.num1.toDoubleOrNull()
        val num2 = state.num2.toDoubleOrNull()
        if (num1 != null && num2 != null) {
            val result = when (state.operation) {
                is Operacionescal.Suma -> num1 + num2
                is Operacionescal.Resta -> num1 - num2
                is Operacionescal.Multiplicacion -> num1 * num2
                is Operacionescal.Division -> num1 / num2
                null -> return
            }
            state = state.copy(
                num1 = result.toString().take(15),
                num2 = "",
                operation = null
            )
        }
    }


    private fun enterOperation(operation: Operacionescal) {
        if(state.num1.isNotBlank()){
            state = state.copy(operation=operation)
        }
    }

    private fun enterDecimal() {
    if(state.operation== null && !state.num1.contains(".")
      && state.num1.isNotBlank()
        ){
            state = state.copy(
                num1 = state.num1 + "."
            )
        return
        }
        if(!state.num2.contains(".") && state.num2.isNotBlank()
        ) {
            state = state.copy(
                num2 = state.num2 + "."
            )
        }
    }

    private fun enterNumber(number: Int) {
        if(state.operation == null){
            if(state.num1.length >= MAX_NUM_LENGTH){
                return
            }
            state = state.copy(
                num1 =state.num1 + number
            )
            return
        }
        if(state.num2.length >= MAX_NUM_LENGTH){
            return
        }
        state = state.copy(
            num2 =state.num2 + number
        )
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}