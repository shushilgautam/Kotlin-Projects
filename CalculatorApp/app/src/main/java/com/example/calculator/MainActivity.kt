package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


fun evaluatePostfix(exp: String): Any {
    val stack = ArrayDeque<Double>()

    for (char in exp) {
        when (char) {
            in '0'..'9' -> stack.addFirst(char.digitToInt().toDouble())
            else -> {
                val val2 = stack.first()
                stack.removeFirst()

                val val1 = stack.first()
                stack.removeFirst()
                when (char) {
                    '+' -> stack.addFirst(val1 + val2)
                    '-' -> stack.addFirst(val1 - val2)
                    '*' -> stack.addFirst(val1 * val2)
                    '/' -> stack.addFirst(val1 / val2)
                    else -> throw IllegalArgumentException("Invalid operator: $char")
                }
            }

        }
        println(stack.first())
    }

    return stack.first()
}
private fun infixToPostfix(expression: String): Any {
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

private fun precedence(op: Char): Int {
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
        val del = findViewById<Button>(R.id.btnDot)
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
        del.setOnClickListener(View.OnClickListener {
            var str=screen.text.toString()
            if(str.isNotEmpty()){
                str=str.substring(0,str.length-1)
                screen.setText(str)
            }

        })
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
            val answer = infixToPostfix(expression.toString())
            result.setText("=  "+answer.toString())
        })
    }

    private fun addchar_to_screen(screen: TextView, btn: Button?) {
        screen.setText(screen.text.toString() + btn?.text.toString())
    }

}