package com.example.myapplication.calculadora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calculadora2(
    state: CalculadoraS,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculadoraA) -> Unit
    ) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Text(
                text = state.num1 + (state.operation?.symbol ?: "") + state.num2,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Light,
                fontSize = 80.sp,
                color = Color.Black,
                maxLines = 2

            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Calculadora1(symbol = "AC",
                    modifier = Modifier
                        .background( Color(0xFF9EDA9C))
                        .aspectRatio(2f)
                        .weight(2f),
                    onClick = {
                        onAction(CalculadoraA.Clear)
                    }
                )
                Calculadora1(symbol = "Del",
                    modifier = Modifier
                        .background( Color(0xFF9EDA9C))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Delete)
                    }
                )
                Calculadora1(symbol = "/",
                    modifier = Modifier
                        .background( Color(0xFF12950E))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Operation(Operacionescal.Division))
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Calculadora1(symbol = "7",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(7))
                    }
                )
                Calculadora1(symbol = "8",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(8))
                    }
                )
                Calculadora1(symbol = "9",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(9))
                    }
                )
                Calculadora1(symbol = "x",
                    modifier = Modifier
                        .background(Color(0xFF12950E))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Operation(Operacionescal.Multiplicacion))
                    }
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Calculadora1(symbol = "4",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(4))
                    }
                )
                Calculadora1(symbol = "5",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(5))
                    }
                )
                Calculadora1(symbol = "6",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(6))
                    }
                )
                Calculadora1(symbol = "-",
                    modifier = Modifier
                        .background(Color(0xFF12950E))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Operation(Operacionescal.Resta))
                    }
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Calculadora1(symbol = "1",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(1))
                    }
                )
                Calculadora1(symbol = "2",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(2))
                    }
                )
                Calculadora1(symbol = "3",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Number(3))
                    }
                )
                Calculadora1(symbol = "+",
                    modifier = Modifier
                        .background(Color(0xFF12950E))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Operation(Operacionescal.Suma))
                    }
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Calculadora1(symbol = "0",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(2f)
                        .weight(2f),
                    onClick = {
                        onAction(CalculadoraA.Number(0))
                    }
                )
                Calculadora1(symbol = ".",
                    modifier = Modifier
                        .background(Color.Green)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Decimal)
                    }
                )

                Calculadora1(symbol = "=",
                    modifier = Modifier
                        .background(Color(0xFF12950E))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculadoraA.Calculate)
                    }
                )

            }
        }
    }
}