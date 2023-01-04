package pt.isec.a21280317.tp_202223

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigSpgameBinding
import kotlin.random.Random

class ConfigSPGameActivity : AppCompatActivity(){

    lateinit var binding: ActivityConfigSpgameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigSpgameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gridLayout = GridLayout(this)
        gridLayout.columnCount = 5
        gridLayout.rowCount = 5

        val params = GridLayout.LayoutParams()
        params.width = GridLayout.LayoutParams.WRAP_CONTENT
        params.height = GridLayout.LayoutParams.WRAP_CONTENT
        params.setGravity(Gravity.CENTER)
        gridLayout.layoutParams = params

//        gridLayout.setShowDividers(GridLayout.SHOW_DIVIDER_MIDDLE or GridLayout.SHOW_DIVIDER_BEGINNING or GridLayout.SHOW_DIVIDER_END)
//        gridLayout.dividerPadding = 4
//        gridLayout.dividerDrawable = ContextCompat.getDrawable(this, android.R.color.black)
//        gridLayout.stretchMode = GridLayout.STRETCH_COLUMN_WIDTH

        for (i in 1..25){
            val cell = TextView(this)
            val value = Random.nextInt(1,10)
            cell.id = View.generateViewId()
            cell.text = "$value"
            gridLayout.addView(cell)
        }

        binding.spgame.addView(gridLayout)

    }

}
