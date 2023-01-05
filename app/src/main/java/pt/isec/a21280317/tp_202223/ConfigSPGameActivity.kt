package pt.isec.a21280317.tp_202223

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import android.widget.GridLayout.CENTER
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigSpgameBinding
import java.util.*
import kotlin.random.Random


class ConfigSPGameActivity : AppCompatActivity() {

    lateinit var binding: ActivityConfigSpgameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigSpgameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val levelAct = 4
        val levelNum = 0

        generateGrid(levelAct, levelNum)
        resultados()


    }

    private fun generateGrid(levelAct: Int, levelNum: Int) {
        for (i in 1..25) {
            val cellId = "cell$i"
            val textView =
                findViewById<TextView>(resources.getIdentifier(cellId, "id", packageName))
            val value = Random.nextInt(1, 10 + levelNum)
            val actions = listOf("+", "-", "*", "/")
            val actionIndex = Random.nextInt(actions.size)
            val randAction = actions[actionIndex]
            if (i == 7 || i == 9 || i == 17 || i == 19) {
                textView.text = ""
            } else if (i % 2 == 0) {
                textView.text = randAction
            } else {
                textView.text = "$value"
            }
            textView.textSize = 20F;
        }
    }

    private fun resultados() {
        val gridLayout = findViewById<GridLayout>(R.id.gameGrid)

        val colTexts = getColumnTexts(gridLayout, listOf(0, 2, 4))
        println(colTexts)

        val rowTexts = getRowTexts(gridLayout, listOf(0, 2, 4))
        println(rowTexts)

        val resRows = mutableListOf<Double>()
        val resCols = mutableListOf<Double>()


    }

    fun getColumnTexts(gridLayout: GridLayout, columnIndices: List<Int>): List<List<String>> {
        val texts = mutableListOf<List<String>>()
        for (column in columnIndices) {
            val columnTexts = mutableListOf<String>()
            for (row in 0 until gridLayout.rowCount) {
                val view = gridLayout.getChildAt((row * gridLayout.columnCount + column))
                if (view is TextView) {
                    columnTexts.add(view.text.toString())
                }
            }
            texts.add(columnTexts)
        }
        return texts
    }

    fun getRowTexts(gridLayout: GridLayout, rowIndices: List<Int>): List<List<String>> {
        val texts = mutableListOf<List<String>>()
        for (row in rowIndices) {
            val rowTexts = mutableListOf<String>()
            for (column in 0 until gridLayout.columnCount) {
                val view = gridLayout.getChildAt((row * gridLayout.columnCount + column))
                if (view is TextView) {
                    rowTexts.add(view.text.toString())
                }
            }
            texts.add(rowTexts)
        }
        return texts
    }


    fun evaluate(expression: String): Double {
        val stack = LinkedList<Double>()
        val tokens = expression.split(" ")
        for (token in tokens) {
            when {
                token == "+" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a + b)
                }
                token == "-" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a - b)
                }
                token == "*" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a * b)
                }
                token == "/" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a / b)
                }
                else -> stack.push(token.toDouble())
            }
        }
        return stack.pop()
    }

//    val spacedExpression = rowString.replace(Regex("(?<=[\\d])"), " ").replace(Regex("(?=[\\d])"), " ")
//    val result = evaluate(spacedExpression)

}
