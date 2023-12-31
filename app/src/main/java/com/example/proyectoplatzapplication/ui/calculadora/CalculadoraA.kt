package com.example.proyectoplatzapplication.ui.calculadora

import com.example.proyectoplatzapplication.ui.calculadora.Operacionescal

sealed class CalculadoraA{
    data class Number(val number: Int): CalculadoraA()
    object Clear: CalculadoraA()
    object Delete: CalculadoraA()
    object Decimal: CalculadoraA()
    object Calculate: CalculadoraA()
    data class Operation(val operation: Operacionescal): CalculadoraA()

}