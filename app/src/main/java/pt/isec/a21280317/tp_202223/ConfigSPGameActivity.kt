package pt.isec.a21280317.tp_202223

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import android.widget.GridLayout.CENTER
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigSpgameBinding
import java.util.*
import kotlin.random.Random
import kotlin.math.pow


class ConfigSPGameActivity : AppCompatActivity() {

    lateinit var binding: ActivityConfigSpgameBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        var levelAct = 1
        var levelNum = 1
        var score = 0

        super.onCreate(savedInstanceState)
        binding = ActivityConfigSpgameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        generateGrid(levelAct, levelNum, score.toString())

        val buttonNum = findViewById<Button>(R.id.btnNextLvlNum)
        val buttonAct = findViewById<Button>(R.id.btnNextLvlAct)
        val buttonLvl = findViewById<Button>(R.id.btnNextLvl2)

        buttonNum.setOnClickListener{
            if (levelNum<4){
                levelNum += 1
                generateGrid(levelAct, levelNum,score.toString())
            }
            else{
                generateGrid(levelAct, levelNum,score.toString())
            }
            resultados()
        }

        buttonAct.setOnClickListener{
            if (levelAct<4){
                levelAct += 1
                generateGrid(levelAct, levelNum,score.toString())
            }
            else{
                generateGrid(levelAct, levelNum,score.toString())
            }
            resultados()
        }

        buttonLvl.setOnClickListener {
            score += 1
            if (score%5==0){
                val rand = Random.nextInt(0,1)
                if (rand == 0){
                    if (levelAct<4){
                        levelAct += 1
                        generateGrid(levelAct, levelNum,score.toString())
                    }
                }else{
                    if (levelNum<4){
                        levelNum += 1
                        generateGrid(levelAct, levelNum,score.toString())
                    }
                }
            }else{
                generateGrid(levelAct, levelNum,score.toString())
            }
            resultados()
        }


        resultados()


    }

    private fun generateGrid(levelAct: Int, levelNum: Int, score: String) {
        for (i in 1..25) {
            val limitNum = 10.0.pow(levelNum)
            val cellId = "cell$i"
            val textView =
                findViewById<TextView>(resources.getIdentifier(cellId, "id", packageName))
            val value = Random.nextInt(1,  limitNum.toInt())
            val actions = listOf("+", "-", "*", "/")
            val actionIndex = Random.nextInt(levelAct)
            val randAction = actions[actionIndex]
            if (i == 7 || i == 9 || i == 17 || i == 19) {
                textView.text = ""
            } else if (i % 2 == 0) {
                textView.text = randAction
            } else {
                textView.text = "$value"
            }
            textView.textSize = 20F - levelNum;
        }
        val textView = findViewById<TextView>(resources.getIdentifier("score", "id", packageName))
        textView.text = score
    }

    private fun resultados() {
        val gridLayout = findViewById<GridLayout>(R.id.gameGrid)

        val colTexts = getColumnExp(gridLayout)
        println(colTexts)

        val rowTexts = getRowExp(gridLayout)
        println(rowTexts)

        val resRows = mutableListOf<Double>()
        val resCols = mutableListOf<Double>()


    }

    fun getColumnExp(gridLayout: GridLayout): List<String> {

        val expression = mutableListOf<String>()
        for (column in 0 until  gridLayout.columnCount) {
            val columnRes = StringBuilder()
            for (row in 0 until gridLayout.rowCount) {
                val view = gridLayout.getChildAt((row * gridLayout.columnCount + column))
                if (view is TextView) {
                    columnRes.append(view.text)
                }
            }
            expression.add(columnRes.toString())
        }

        println(expression)

        expression.removeAt(1)
        expression.removeAt(2)

//        val result = evaluate("1+2*3")
//        println( result)

        //expression.add(evaluate(expression[0]).toString())
//        expression.add(evaluate(expression[1]).toString())
//        expression.add(evaluate(expression[2]).toString())

        println(expression)

        return expression
    }

    fun getRowExp(gridLayout: GridLayout): List<String> {
        val expression = mutableListOf<String>()
        for (row in 0 until gridLayout.rowCount) {
            val rowRes = StringBuilder()
            for (column in 0 until gridLayout.columnCount) {
                val view = gridLayout.getChildAt((row * gridLayout.columnCount + column))
                if (view is TextView) {
                    rowRes.append(view.text)
                }
            }
            expression.add(rowRes.toString())
        }
        expression.removeAt(1)
        expression.removeAt(2)

        return expression
    }


//    fun evaluate(expression: String): Double {
//        val tokens = mutableListOf<String>()
//        var token = ""
//        for (ch in expression) {
//            if (ch.isDigit() || ch == '.') {
//                // add the digit or decimal point to the current token
//                token += ch
//            } else {
//                // add the current token to the list of tokens
//                tokens.add(token)
//                token = ""
//
//                // add the operation to the list of tokens
//                tokens.add(ch.toString())
//            }
//        }
//        // add the last token to the list of tokens
//        tokens.add(token)
//
//        // evaluate the expression using a stack
//        val stack = mutableListOf<Double>()
//        for (t in tokens) {
//            if (t.isEmpty()) {
//                continue
//            }
//            when {
//                t[0].isDigit() -> stack.add(t.toDouble())
//                else -> {
//                    // perform the operation
//                    val right = stack.removeAt(stack.size - 1)
//                    val left = stack.removeAt(stack.size - 1)
//                    when (t[0]) {
//                        '+' -> stack.add(left + right)
//                        '-' -> stack.add(left - right)
//                        '*' -> stack.add(left * right)
//                        '/' -> stack.add(left / right)
//                    }
//                }
//            }
//        }
//
//        return stack.first()
//    }

//    val spacedExpression = rowString.replace(Regex("(?<=[\\d])"), " ").replace(Regex("(?=[\\d])"), " ")
//    val result = evaluate(spacedExpression)

}
