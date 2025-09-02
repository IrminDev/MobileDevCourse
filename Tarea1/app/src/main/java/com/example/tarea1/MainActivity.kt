package com.example.tarea1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarea1.ui.theme.Tarea1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tarea1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        CalculatorScreen()
                    }
                }
            }
        }
    }
}

// 3. CalculatorScreen: Main calculator screen
@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var displayText by remember { mutableStateOf("0") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom // Buttons at the bottom, display on top
    ) {
        CalculatorDisplay(
            text = displayText,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Display takes available vertical space above buttons
        )
        Spacer(modifier = Modifier.height(16.dp))
        ButtonGrid(
            onButtonClick = { buttonValue ->
                // Basic logic (very simplified)
                if (displayText == "0" && buttonValue != "." && !isOperator(buttonValue)) {
                    displayText = buttonValue
                } else if (isOperator(buttonValue)) {
                    if (isOperator(displayText.last().toString())) {
                        displayText = displayText.dropLast(1) + buttonValue
                    } else {
                        displayText += buttonValue
                    }
                }
                else if (buttonValue == ".") {
                    if (!displayText.contains(".")) {
                        displayText += buttonValue
                    }
                }
                else if (buttonValue == "=") {
                    try {
                        displayText = calculateResult(displayText)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        displayText = "Error"
                    }
                } else {
                    displayText += buttonValue
                }
                if (buttonValue == "C") {
                    displayText = "0"
                }
                // Add more complex logic for AC, =, calculations etc. here
            }
        )
    }
}


// Helper function to calculate the result
fun calculateResult(expression: String): String {
    val numbers = expression.split("+", "-", "×", "÷")
    var result = numbers[0].toDouble()
    for (i in 1 until numbers.size) {
        val operator = expression[expression.indexOf(numbers[i]) - 1]
        val nextNumber = numbers[i].toDouble()
        when (operator) {
            '+' -> result += nextNumber
            '-' -> result -= nextNumber
            '×' -> result *= nextNumber
            '÷' -> result /= nextNumber
        }
    }

    return result.toString()
}

// Helper function to check if a string is an operator
fun isOperator(value: String): Boolean {
    return value == "+" || value == "-" || value == "×" || value == "÷"
}

// 2. CalculatorDisplay: Displays the expression or result
@Composable
fun CalculatorDisplay(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(vertical = 24.dp, horizontal = 8.dp),
        contentAlignment = Alignment.BottomEnd // Text aligned to bottom right
    ) {
        Text(
            text = text,
            fontSize = 48.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.End,
            maxLines = 2 // Allow for some wrapping if needed
        )
    }
}

// 1. CalculatorButton: A single calculator button
@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f) // Make buttons square
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Text(text = text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

// 4. ButtonGrid: Arranges calculator buttons
@Composable
fun ButtonGrid(onButtonClick: (String) -> Unit, modifier: Modifier = Modifier) {
    val buttonRows = listOf(
        listOf("AC", "C", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "=") // Last row has a wider button
    )

    Column(modifier = modifier) {
        buttonRows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { buttonText ->
                    val weight = if (buttonText == "0") 2f else 1f // "0" button is wider
                    val buttonBackgroundColor = when (buttonText) {
                        "AC", "C", "%" -> MaterialTheme.colorScheme.secondaryContainer
                        "÷", "×", "-", "+", "=" -> MaterialTheme.colorScheme.tertiaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant // Numbers and dot
                    }
                    val buttonContentColor = when (buttonText) {
                        "AC", "C", "%" -> MaterialTheme.colorScheme.onSecondaryContainer
                        "÷", "×", "-", "+", "=" -> MaterialTheme.colorScheme.onTertiaryContainer
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }

                    CalculatorButton(
                        text = buttonText,
                        onClick = { onButtonClick(buttonText) },
                        modifier = Modifier.weight(weight),
                        backgroundColor = buttonBackgroundColor,
                        contentColor = buttonContentColor
                    )
                }
                // If the row has fewer than 4 buttons (like the last row), add spacers to maintain alignment
                if (row.size < 4 && row.contains("=")) { // Specifically for the last row with '='
                    val missingButtons = 4 - row.size
                    repeat(missingButtons) {
                        Spacer(Modifier
                            .weight(1f)
                            .padding(4.dp))
                    }
                }
            }
        }
    }
}

/*
    Previews
*/

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun CalculatorScreenPreview() {
    Tarea1Theme {
        CalculatorScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorDisplayPreview() {
    Tarea1Theme {
        CalculatorDisplay(text = "12345+67890")
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorButtonPreview() {
    Tarea1Theme {
        CalculatorButton(text = "7", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun OperatorButtonPreview() {
    Tarea1Theme {
        CalculatorButton(
            text = "+",
            onClick = {},
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    }
}