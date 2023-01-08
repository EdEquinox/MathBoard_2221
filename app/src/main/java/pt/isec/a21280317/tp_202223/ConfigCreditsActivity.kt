package pt.isec.a21280317.tp_202223

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigCreditsBinding

class ConfigCreditsActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityConfigCreditsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityConfigCreditsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}