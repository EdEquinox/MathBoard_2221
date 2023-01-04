package pt.isec.a21280317.tp_202223

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigSpgameBinding
import kotlin.random.Random

class ConfigSPGameActivity : AppCompatActivity(){

    lateinit var binding: ActivityConfigSpgameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigSpgameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val levelAct = 0
        val levelNum = 0
        val gridLayout = GridLayout(this)
        gridLayout.columnCount = 5
        gridLayout.rowCount = 5

        val cellSize = 140
        val cellMargin = 5


        for (i in 1..25){
            val cell =  TextView(this)
            val value = Random.nextInt(1,10 + levelNum)
            val actions = listOf("+","-","*","/")
            val actionIndex = Random.nextInt(actions.size - levelAct)
            val randAction = actions[actionIndex]
            cell.id = View.generateViewId()
            if (i == 7 || i == 9 || i == 17 || i == 19) {
                cell.text = ""
            }
            else if (i % 2 == 0) {
                cell.text = randAction
            }
            else {
                cell.text = "$value"
            }
            cell.setBackgroundColor(Color.LTGRAY)
            cell.typeface = Typeface.DEFAULT_BOLD
            cell.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)


            val layoutParams = GridLayout.LayoutParams()
            layoutParams.width = cellSize
            layoutParams.height = cellSize
            layoutParams.setMargins(cellMargin, cellMargin, cellMargin, cellMargin)
            layoutParams.setMarginEnd(cellMargin)
            layoutParams.setMarginStart(cellMargin)
            layoutParams.topMargin = cellMargin
            layoutParams.bottomMargin = cellMargin
            cell.layoutParams = layoutParams
            gridLayout.addView(cell)
        }

        val gridLayoutParams = FrameLayout.LayoutParams(5 * cellSize + 4 * cellSize, 5 * cellSize + 4 * cellSize)
//        gridLayoutParams.gravity = Gravity.CENTER
        gridLayout.layoutParams = gridLayoutParams

        binding.spgame.addView(gridLayout)

    }

}
