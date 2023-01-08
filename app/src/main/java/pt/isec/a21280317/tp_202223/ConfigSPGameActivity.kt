package pt.isec.a21280317.tp_202223

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigSpgameBinding
import kotlin.random.Random
import kotlin.math.pow


class ConfigSPGameActivity : AppCompatActivity() {

    private var levelAct = 1
    private var levelNum = 1
    private var score = 0
    private lateinit var binding: ActivityConfigSpgameBinding
    private var cellId: Int = 0
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding = ActivityConfigSpgameBinding.inflate(layoutInflater)
            setContentView(R.layout.activity_config_spgame_landscape)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            generateGrid(levelAct, levelNum, score.toString())

            startTimer()
            setOnClickListeners()

        } else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding = ActivityConfigSpgameBinding.inflate(layoutInflater)
            setContentView(binding.root)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            generateGrid(levelAct, levelNum, score.toString())

            startTimer()
            setOnClickListeners()

        }


    }

    override fun onStop() {
        timer.cancel()
        super.onStop()
    }

    private fun startTimer() {
        timer = createTimer()
        timer.start()
    }

    private fun setOnClickListeners() {
        cellId = results().key
        println(cellId)
        var clickedCell :Int
        for (i in 0 until binding.gameGrid.childCount) {
            val textView = binding.gameGrid.getChildAt(i) as TextView
            textView.setOnClickListener {
                clickedCell = i+1
                println("Cell clicked: $clickedCell")
                if (clickedCell == cellId){
                    updateScore()
                    val res = results()
                    cellId = res.key
                    println(res)
                }
                else{
                    resetGame()
                }
            }
        }

    }

    private fun updateScore() {

        score += 1
        timer.cancel()
        timer.start()
        if (score % 5 == 0) {
            Snackbar.make(binding.root, "Next Level!", Snackbar.LENGTH_LONG)
                .setDuration(1000)
                .show()
            val rand = Random.nextInt(0, 2)
            if (rand == 0) {
                if (levelAct < 4) {
                    levelAct += 1
                    generateGrid(levelAct, levelNum, score.toString())
                }
            } else {
                if (levelNum < 4) {
                    levelNum += 1
                    generateGrid(levelAct, levelNum, score.toString())
                }
            }
        } else {
            generateGrid(levelAct, levelNum, score.toString())
        }
    }

    private fun resetGame() {
        score = 0
        timer.cancel()
        timer.onFinish()
        showLoseNotification()
    }


    private fun createTimer() : CountDownTimer{
        val timerView = binding.timer

        val timer = object : CountDownTimer(16000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerView.text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                timerView.text = "0"
                showLoseNotification()
            }
        }
        return timer
    }

    private fun showLoseNotification() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("You lost the game")
            .setPositiveButton("New Game") { _, _ ->
                startActivity(intent)
            }
            .setNegativeButton("Main Menu") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        val dialog = builder.create()
        dialog.show()
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
            textView.textSize = 20F - levelNum
            textView.setTextColor(Color.BLACK)
        }
        val textView = findViewById<TextView>(resources.getIdentifier("score", "id", packageName))
        textView.text = score

    }

    private fun eval(expression: String): Double {
        return expression.split("+", "-", "*", "/")
            .map { it.trim() }
            .map { it.toDouble() }
            .reduce { acc, element ->
                when {
                    expression.contains("+") -> acc + element
                    expression.contains("-") -> acc - element
                    expression.contains("*") -> acc * element
                    expression.contains("/") -> acc / element
                    else -> acc
                }
            }
    }

    private fun results() :  Map.Entry<Int, Double> {
        val gridLayout = binding.gameGrid

        val colRes = getColumnRes(gridLayout)
        val rowRes = getRowRes(gridLayout)

        return if (rowRes.value > colRes.value){
            rowRes
        } else{
            colRes
        }
    }

    private fun getColumnRes(gridLayout: GridLayout): Map.Entry<Int, Double> {

        val expression = mutableListOf<String>()
        for (column in 0 until gridLayout.columnCount) {
            val columnRes = StringBuilder()
            for (row in 0 until gridLayout.rowCount) {
                val view = gridLayout.getChildAt((row * gridLayout.columnCount + column))
                if (view is TextView) {
                    columnRes.append(view.text)
                }
            }
            expression.add(columnRes.toString())
        }
        expression.removeAt(1)
        expression.removeAt(2)

        val mapResult =
            mapOf(1 to eval(expression[0]), 3 to eval(expression[1]), 5 to eval(expression[2]))

        return mapResult.maxBy { it.value }
    }

    private fun getRowRes(gridLayout: GridLayout): Map.Entry<Int, Double> {
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

        val mapResult =
            mapOf(1 to eval(expression[0]), 11 to eval(expression[1]), 21 to eval(expression[2]))

        return mapResult.maxBy { it.value }

    }

}
