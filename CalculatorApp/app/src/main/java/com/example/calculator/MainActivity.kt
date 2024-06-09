package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Stack

fun main(){
    val expression="2+(3*1)-9"
    println(infixToPostfix(expression))

}
fun evaluatePostfix(exp: String): Int {
    val stack = ArrayDeque<Int>()

    for (char in exp) {
        when (char) {
            in '0'..'9' -> stack.addFirst(char.toInt()-0)
            else -> {
                val val2 = stack.removeFirst()
                val val1 = stack.removeFirst()
                when (char) {
                    '+' -> stack.addFirst(val2 + val1)
                    '-' -> stack.addFirst(val2 - val1)
                    '*' -> stack.addFirst(val2 * val1)
                    '/' -> stack.addFirst(val2 / val1)
                    else -> throw IllegalArgumentException("Invalid operator: $char")
                }
            }

        }
        println(stack[0])
    }

    return stack.removeFirst()
}
fun infixToPostfix(expression: String): Int {
    val stack = ArrayDeque<Char>()
    val output = StringBuilder()

    for (char in expression) {
        when (char) {
            in '0'..'9' -> output.append(char)
            // Append operands
            '(' -> stack.addFirst(char)   // Push opening parentheses

            ')' -> {
                while (stack.isNotEmpty()) {
                    val firstElement = if (stack.isNotEmpty()) stack[0] else null
                    if (firstElement != null && firstElement != '(' && firstElement != '[' && firstElement != '{') {
                        output.append(stack.removeFirst())
                    } else {
                        if (stack.isEmpty() || firstElement != '(' && firstElement != '[' && firstElement != '{') {
                            throw IllegalArgumentException("Invalid Expression")
                        } else {
                            stack.removeFirst()  // Pop closing parentheses
                        }
                        break
                    }
                }
            }
            '+', '-', '*', '/' -> {
                while (stack.isNotEmpty() && precedence(char) <= precedence(stack[0])) {
                    output.append(stack.removeFirst())
                }
                stack.addFirst(char)  // Push operators
            }
            else -> throw IllegalArgumentException("Invalid character: $char")
        }
    }

    while (stack.isNotEmpty()) {
        output.append(stack.removeFirst())
    }
    println(output.toString())
    val result=evaluatePostfix(output.toString())
    return result
}

fun precedence(op: Char): Int {
    return when (op) {
        '+', '-' -> 1
        '*', '/' -> 2
        '(' -> 0
        ')' -> -1
        else -> throw IllegalArgumentException("Invalid operator: $op")
    }
}

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val screen = findViewById<TextView>(R.id.screen)
        val result = findViewById<TextView>(R.id.result)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)
        val btn0 = findViewById<Button>(R.id.btn0)
        val plus = findViewById<Button>(R.id.btnPlus)
        val minus = findViewById<Button>(R.id.btnMin)
        val product = findViewById<Button>(R.id.btnMul)
        val divide = findViewById<Button>(R.id.btnDiv)
        val dot = findViewById<Button>(R.id.btnDot)
        val openBracket = findViewById<Button>(R.id.openbracket)
        val closeBracket = findViewById<Button>(R.id.closeBracket)
        btn1.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn1) })
        btn2.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn2) })
        btn3.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn3) })
        btn4.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn4) })
        btn5.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn5) })
        btn6.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn6) })
        btn7.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn7) })
        btn8.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn8) })
        btn9.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn9) })
        btn0.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, btn0) })
//        for operators
        plus.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, plus) })
        minus.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, minus) })
        product.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, product) })
        divide.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, divide) })
        dot.setOnClickListener(View.OnClickListener { addchar_to_screen(screen, dot) })
        openBracket.setOnClickListener(View.OnClickListener {
            addchar_to_screen(
                screen,
                openBracket
            )
        })
        closeBracket.setOnClickListener(View.OnClickListener {
            addchar_to_screen(
                screen,
                closeBracket
            )
        })
//for AC
        val clear = findViewById<Button>(R.id.btnClr)
        clear.setOnClickListener(View.OnClickListener {
            screen.setText("")
            result.setText("")
        })
        //        for result
        val equals = findViewById<Button>(R.id.btnEquals)
        equals.setOnClickListener(View.OnClickListener {
            val expression = screen.text
            val answer = evaluateExpression(expression)
            result.setText(answer.toString())
        })
    }

    private fun addchar_to_screen(screen: TextView, btn: Button?) {
        screen.setText(screen.text.toString() + btn?.text.toString())
    }

    private fun evaluateExpression(expression: CharSequence?): Any {
        var answer: Double = 0.0
        val tokens = expression?.split("(?<=[-+*/])|(?=[-+*/])".toRegex())
        var operator: Any? =null
        if (tokens != null) {
            for (token in tokens) {
                when (token) {
                    "+", "-", "*", "/" -> operator = token
                    else -> {
                        val number = token.toDouble()
                        when (operator) {
                            null -> answer = number
                            '+' -> answer += number
                            '-' -> answer -= number
                            '*' -> answer *= number
                            '/' -> answer /= number
                        }
                    }
                }
            }

        }else{
            Toast.makeText(this, "Token is Empty",Toast.LENGTH_SHORT)
        }

        return answer
    }
}